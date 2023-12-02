import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Objects;

public class SaveLoadPage extends JPanel
{
    //导入窗口所用的所有图片
    Image background_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("background.png"));
    Image saveTitle_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("saveTitle.png"));
    Image loadTitle_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("loadTitle.png"));
    Image nextPButton_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("nextPButton.png"));
    Image nextPButtonA_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("nextPButtonA.png"));
    Image prePButton_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("prePButton.png"));
    Image prePButtonA_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("prePButtonA.png"));
    Image returnButton_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("returnButton.png"));
    Image returnButtonA_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("returnButtonA.png"));
    Image record_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("thumbnail/0.png"));

    ImageIcon background_Icon = new ImageIcon(background_Img);
    ImageIcon saveTitle_Icon = new ImageIcon(saveTitle_Img);
    ImageIcon loadTitle_Icon = new ImageIcon(loadTitle_Img);
    ImageIcon nextPButton_Icon = new ImageIcon(nextPButton_Img);
    ImageIcon nextPButtonA_Icon = new ImageIcon(nextPButtonA_Img);
    ImageIcon prePButton_Icon = new ImageIcon(prePButton_Img);
    ImageIcon prePButtonA_Icon = new ImageIcon(prePButtonA_Img);
    ImageIcon returnButton_Icon = new ImageIcon(returnButton_Img);
    ImageIcon returnButtonA_Icon = new ImageIcon(returnButtonA_Img);
    ImageIcon record_Icon = new ImageIcon(record_Img);
    ImageIcon[] thumbnail_Icons = new ImageIcon[64];

    //窗口中的标签
    JLabel background_Lb = new JLabel(background_Icon);
    JLabel title_Lb = new JLabel(loadTitle_Icon);

    //窗口中的按钮
    public JButton nextPButton_Bt = new JButton(nextPButton_Icon);
    public JButton prePButton_Bt = new JButton(prePButton_Icon);
    public JButton returnButton_Bt = new JButton(returnButton_Icon);
    public JButton[] recordButton_Bts = new JButton[6];
    public boolean[] recordIsEmpty = new boolean[6];


    //控制量
    public boolean isLoad = true;
    public boolean isFromMain = true;
    public int SL_loc = 0;

    public int page = 0;
    public int pageNum = 5;
    File file = new File("src/record.txt");

    public AudioClip ButtonTouch //音效1（按钮碰触）
            = Applet.newAudioClip(this.getClass().getResource("ButtonTouch.wav"));

    public SaveLoadPage() //窗口构造函数
    {
        for(int i=1; i<=63; i++) //准备好所有的缩略图
        {
            String picName = "thumbnail/" + i + ".png";
            Image tmp_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource(picName));
            thumbnail_Icons[i] = new ImageIcon(tmp_Img);
        }
        for(int i=0; i<6; i++) { recordButton_Bts[i] = new JButton(record_Icon); recordIsEmpty[i] = true;} //初始化
        ButtonLayout(0);

        //构造窗口
        this.setLayout(null);

        //控件布局
        background_Lb.setBounds(-1, 0, 1170, 765);
        title_Lb.setBounds(505, 30, 160, 80);

        nextPButton_Bt.setBounds(135,685,80,46);
        prePButton_Bt.setBounds(35,685,80,46);
        returnButton_Bt.setBounds(1050,685,80,46);

        recordButton_Bts[0].setBounds(100, 150, 234, 153);
        recordButton_Bts[1].setBounds(100+234+134, 150, 234, 153);
        recordButton_Bts[2].setBounds(100+234*2+134*2, 150, 234, 153);
        recordButton_Bts[3].setBounds(100, 150+153+100, 234, 153);
        recordButton_Bts[4].setBounds(100+234+134, 150+153+100, 234, 153);
        recordButton_Bts[5].setBounds(100+234*2+134*2, 150+153+100, 234, 153);

        nextPButton_Bt.setOpaque(false); //去除按钮的白色背景填充
        prePButton_Bt.setOpaque(false);
        returnButton_Bt.setOpaque(false);

        nextPButton_Bt.setContentAreaFilled(false); //点击时不会重现黑背景
        prePButton_Bt.setContentAreaFilled(false);
        returnButton_Bt.setContentAreaFilled(false);

        nextPButton_Bt.setBorderPainted(false); //去除按钮边框
        prePButton_Bt.setBorderPainted(false);
        returnButton_Bt.setBorderPainted(false);

        //鼠标放到按钮上的改变
        nextPButton_Bt.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {nextPButton_Bt.setIcon(nextPButtonA_Icon); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {nextPButton_Bt.setIcon(nextPButton_Icon);}
        });
        prePButton_Bt.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {prePButton_Bt.setIcon(prePButtonA_Icon); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {prePButton_Bt.setIcon(prePButton_Icon);}
        });
        returnButton_Bt.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {returnButton_Bt.setIcon(returnButtonA_Icon); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {returnButton_Bt.setIcon(returnButton_Icon);}
        });

        //在窗口中添加控件
        this.add(nextPButton_Bt);
        this.add(prePButton_Bt); prePButton_Bt.setVisible(false); //起始在第1页，没有更之前的页
        this.add(returnButton_Bt);
        for(int i=0; i<6; i++) this.add(recordButton_Bts[i]);

        this.add(title_Lb);
        this.add(background_Lb); //背景图标最后添加以保证位于最底下
    }

    public String ReadFile() //读出存档文件中现有内容
    {
        String fileString = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) //打开文件，使用阅读器
        {
            String tempString;
            while ((tempString = reader.readLine()) != null) fileString = tempString;
        }
        catch (IOException e) //对输入输出的容错处理
        { e.printStackTrace();}
        return fileString;
    }

    public void ButtonLayout(int page) //当前页上的按钮图片
    {
        for(int i=0; i<6; i++) //开始或换页时先初始化为全空，然后填对应内容
        { recordButton_Bts[i].setIcon(record_Icon); recordIsEmpty[i] = true;}

        String record = ReadFile();
        String[] items = record.split(";");
        for (String item : items) //逐个检查存档记录
        {
            if(!Objects.equals(item, ""))
            {
                if(item.charAt(0)=='0'+page) //找到与当前页符合的
                {
                    int loc = item.charAt(2)-'0';
                    int game_page = Integer.parseInt(item.substring(4));
                    recordButton_Bts[loc].setIcon(thumbnail_Icons[game_page]);
                    recordIsEmpty[loc] = false;
                }
            }
        }
    }

    public void SaveE(int loc, Game game) //空档存储，当前所在位置信息作为一条记录加进存档文件中
    {
        String rawRecord = ReadFile(); //读出存档内容
        StringBuilder newRecord = new StringBuilder();

        String newItem = page + "," + loc + "-" + game.page + ";"; //单条记录，格式：存档页码,存档位置-进行到的页（记录主体）;
        newRecord.append(rawRecord); newRecord.append(newItem);

        recordButton_Bts[loc].setIcon(thumbnail_Icons[game.page]);
        recordIsEmpty[loc] = false;
        try
        {
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.append(newRecord.toString());
            ps.close();
        }
        catch (FileNotFoundException e) //对无法找到要写的文件的容错处理
        {
            e.printStackTrace();
        }
    }

    public void SaveNE(int loc, Game game) //当前所在位置信息作为一条记录加进存档文件中
    {
        String rawRecord = ReadFile(); //读出存档内容
        StringBuilder newRecord = new StringBuilder();

        //覆盖已有存档的操作
        String[] items = rawRecord.split(";");
        for (String item : items)
        {
            if(item.charAt(0)=='0'+page && item.charAt(2)=='0'+loc) //需修改的记录
            { item = page + "," + loc + "-" + game.page;}
            item = item + ";";
            newRecord.append(item);
        }

        recordButton_Bts[loc].setIcon(thumbnail_Icons[game.page]);
        recordIsEmpty[loc] = false;
        try
        {
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.append(newRecord.toString());
            ps.close();
        }
        catch (FileNotFoundException e) //对无法找到要写的文件的容错处理
        {
            e.printStackTrace();
        }
    }

    public int Load(int loc) //结合当前信息分析存档文件中读出的内容
    {
        String record = ReadFile();
        String[] items = record.split(";");
        int game_page = -1;
        for (String item : items)
        {
            if(!Objects.equals(item, ""))
            {
                if(item.charAt(0)=='0'+page && item.charAt(2)=='0'+loc) //找到所要的记录
                { game_page = Integer.parseInt(item.substring(4));}
            }
        }
        return game_page;
    }

    public void ChangeTitle()
    {
        if(!isLoad) title_Lb.setIcon(saveTitle_Icon);
        else title_Lb.setIcon(loadTitle_Icon);
    }
}
