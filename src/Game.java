import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;

import java.awt.*;
import java.awt.event.*;
import java.awt.AlphaComposite;
import java.util.Objects;

public class Game extends JPanel implements Runnable, MouseListener, KeyListener
{
    public boolean isRunning;
    public int page;
    public int pageNum;
    public Image[] imgStories = new Image[65];
    public Image imgFrame;
    public boolean[] isBgmPlaying = new boolean[5];

    public StoryTeller lang = new StoryTeller();

    public boolean isDrawing;
    public float composite;
    public int drawDelay;
    public int currentPage;

    //按钮
    public ImageIcon nextPButton_Img
            = new ImageIcon(Objects.requireNonNull(Game.class.getResource("nextPButton.png")));
    public ImageIcon nextPButtonA_Img
            = new ImageIcon(Objects.requireNonNull(Game.class.getResource("nextPButtonA.png")));
    public JButton nextPButton_Bt;

    public ImageIcon prePButton_Img
            = new ImageIcon(Objects.requireNonNull(Game.class.getResource("prePButton.png")));
    public ImageIcon prePButtonA_Img
            = new ImageIcon(Objects.requireNonNull(Game.class.getResource("prePButtonA.png")));
    public JButton prePButton_Bt;

    public ImageIcon saveButton_Img
            = new ImageIcon(Objects.requireNonNull(Game.class.getResource("saveButton.png")));
    public ImageIcon saveButtonA_Img
            = new ImageIcon(Objects.requireNonNull(Game.class.getResource("saveButtonA.png")));
    public JButton saveButton_Bt;

    public ImageIcon loadButton_Img
            = new ImageIcon(Objects.requireNonNull(Game.class.getResource("loadButton.png")));
    public ImageIcon loadButtonA_Img
            = new ImageIcon(Objects.requireNonNull(Game.class.getResource("loadButtonA.png")));
    public JButton loadButton_Bt;

    public ImageIcon returnButton_Img
            = new ImageIcon(Objects.requireNonNull(Game.class.getResource("returnButtonM.png")));
    public ImageIcon returnButtonA_Img
            = new ImageIcon(Objects.requireNonNull(Game.class.getResource("returnButtonMA.png")));
    public JButton returnButton_Bt;

    public ImageIcon aboutButton_Img
            = new ImageIcon(Objects.requireNonNull(Game.class.getResource("aboutButton.png")));
    public ImageIcon aboutButtonA_Img
            = new ImageIcon(Objects.requireNonNull(Game.class.getResource("aboutButtonA.png")));
    public JButton aboutButton_Bt;

    //导入背景音乐
    public AudioClip OceanPacific //音乐1（平静之洋）
            = Applet.newAudioClip(this.getClass().getResource("OceanPacific.wav"));
    public AudioClip FishPond //音乐2（鱼塘）
            = Applet.newAudioClip(this.getClass().getResource("FishPond.wav"));
    public AudioClip Peace //音乐3（平静浅海）
            = Applet.newAudioClip(this.getClass().getResource("Peace.wav"));
    public AudioClip Mystic //音乐4（神秘水域）
            = Applet.newAudioClip(this.getClass().getResource("Mystic.wav"));
    public AudioClip Danger //音乐5（危机四伏）
            = Applet.newAudioClip(this.getClass().getResource("Danger.wav"));

    //导入音效
    public AudioClip ButtonTouch //音效1（按钮碰触）
            = Applet.newAudioClip(this.getClass().getResource("ButtonTouch.wav"));
    public AudioClip ButtonClick //音效2（按钮点击）
            = Applet.newAudioClip(this.getClass().getResource("ButtonClick.wav"));
    public AudioClip Splash //音效3（水花声）
            = Applet.newAudioClip(this.getClass().getResource("Splash.wav"));
    public AudioClip TheEnd //音效4（尾声）
            = Applet.newAudioClip(this.getClass().getResource("TheEnd.wav"));

    public Game()
    {
        this.setLayout(null);
        Reset();

        //按钮
        nextPButton_Bt = new JButton(nextPButton_Img);
        nextPButton_Bt.setBounds(135,785,80,46);
        nextPButton_Bt.setOpaque(false);
        nextPButton_Bt.setContentAreaFilled(false);
        nextPButton_Bt.setBorderPainted(false);
        nextPButton_Bt.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {nextPButton_Bt.setIcon(nextPButtonA_Img); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {nextPButton_Bt.setIcon(nextPButton_Img);}
        });

        prePButton_Bt = new JButton(prePButton_Img);
        prePButton_Bt.setBounds(35,785,80,46);
        prePButton_Bt.setOpaque(false);
        prePButton_Bt.setContentAreaFilled(false);
        prePButton_Bt.setBorderPainted(false);
        prePButton_Bt.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {prePButton_Bt.setIcon(prePButtonA_Img); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {prePButton_Bt.setIcon(prePButton_Img);}
        });

        saveButton_Bt = new JButton(saveButton_Img);
        saveButton_Bt.setBounds(35+17,785+60,46,80);
        saveButton_Bt.setOpaque(false);
        saveButton_Bt.setContentAreaFilled(false);
        saveButton_Bt.setBorderPainted(false);
        saveButton_Bt.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {saveButton_Bt.setIcon(saveButtonA_Img); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {saveButton_Bt.setIcon(saveButton_Img);}
        });

        loadButton_Bt = new JButton(loadButton_Img);
        loadButton_Bt.setBounds(135+17,785+60,46,80);
        loadButton_Bt.setOpaque(false);
        loadButton_Bt.setContentAreaFilled(false);
        loadButton_Bt.setBorderPainted(false);
        loadButton_Bt.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {loadButton_Bt.setIcon(loadButtonA_Img); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {loadButton_Bt.setIcon(loadButton_Img);}
        });

        returnButton_Bt = new JButton(returnButton_Img);
        returnButton_Bt.setBounds(950,785+50,80,46);
        returnButton_Bt.setOpaque(false);
        returnButton_Bt.setContentAreaFilled(false);
        returnButton_Bt.setBorderPainted(false);
        returnButton_Bt.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {returnButton_Bt.setIcon(returnButtonA_Img); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {returnButton_Bt.setIcon(returnButton_Img);}
        });

        aboutButton_Bt = new JButton(aboutButton_Img);
        aboutButton_Bt.setBounds(950+100,785+50,80,46);
        aboutButton_Bt.setOpaque(false);
        aboutButton_Bt.setContentAreaFilled(false);
        aboutButton_Bt.setBorderPainted(false);
        aboutButton_Bt.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {aboutButton_Bt.setIcon(aboutButtonA_Img); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {aboutButton_Bt.setIcon(aboutButton_Img);}
        });

        add(nextPButton_Bt); //上一页按钮
        add(prePButton_Bt); //下一页按钮
        add(saveButton_Bt); //存档按钮
        add(loadButton_Bt); //读档按钮
        add(returnButton_Bt); //返回主菜单按钮
        add(aboutButton_Bt); //彩蛋按钮
    }

    public void Reset()
    {
        isRunning = false;
        page = 0;
        pageNum = 64;
        lang.Reset();
        imgFrame = Toolkit.getDefaultToolkit().getImage(Game.class.getResource("frame.png"));
        for(int i=0; i<=pageNum; i++)
        {
            String picName = "stories/" + i + ".png";
            imgStories[i] = Toolkit.getDefaultToolkit().getImage(Game.class.getResource(picName));
        }
        for(int i=0; i<5; i++) isBgmPlaying[i] = false;

        isDrawing = true;
        composite = 1.0f;
        drawDelay = 5;
        currentPage = -1;
    }

    public void MusicStop()
    {
        OceanPacific.stop(); isBgmPlaying[0] = false;
        FishPond.stop(); isBgmPlaying[1] = false;
        Peace.stop(); isBgmPlaying[2] = false;
        Mystic.stop(); isBgmPlaying[3] = false;
        Danger.stop(); isBgmPlaying[4] = false;
    }

    public void ResetDrawer(int cp)
    {
        isDrawing = !isDrawing; composite = 1.0f; currentPage = cp;
    }

    public void paint(Graphics gp) //统一绘制
    {
        super.paint(gp);

        Graphics2D g2 = (Graphics2D) gp;
        if(isDrawing) //正在绘制
        {
            drawDelay--;
            //绘制当前页，逐渐复现
            if (currentPage >= 0)
            {
                g2.setComposite(AlphaComposite.SrcOver.derive(composite)); //绘制前一页，逐渐隐去
                g2.drawImage(imgStories[currentPage], 0, 2, 1170, 765, this);
            }
            g2.setComposite(AlphaComposite.SrcOver.derive(1.0f-composite)); // 设置透明度
            g2.drawImage(imgStories[page],0,2, 1170,765,this); // 绘制图像

            if(drawDelay<=0) {drawDelay = 5; composite -= 0.1f;}
            if(composite <= 0.0f) {isDrawing = false; composite = 1.0f;}
        }
        else
        {
            g2.setComposite(AlphaComposite.SrcOver.derive(1.0f));
            g2.drawImage(imgStories[page],0,2, 1170,765,this); //直接画图
            lang.PaintLangColumn(g2,this); //写文字
        }
        g2.setComposite(AlphaComposite.SrcOver.derive(1.0f));
        g2.drawImage(imgFrame,0,0,this); //画边框
    }

    public void run() //游戏的运行
    {
        repaint(); //重绘制

        prePButton_Bt.setVisible(page >= 1);
        nextPButton_Bt.setVisible(page < pageNum);
        saveButton_Bt.setVisible(page >= 1 && page < pageNum);
        loadButton_Bt.setVisible(page >= 1 && page < pageNum);
        aboutButton_Bt.setVisible(page == pageNum);

        // 音乐播放控制
        if(page<1){ if(isBgmPlaying[1]) {FishPond.stop(); isBgmPlaying[1] = false;}} //停掉后面
        if((page>=1 && page<9) && !isBgmPlaying[1]) //1鱼塘，小刀出生
        {
            if(isBgmPlaying[2]) {Peace.stop(); isBgmPlaying[2] = false;} //停掉后面
            FishPond.loop(); isBgmPlaying[1] = true;
        }
        if((page>=9 && page<12) && !isBgmPlaying[2]) //2平静浅海，小刀单独行动
        {
            if(isBgmPlaying[1]) {FishPond.stop(); isBgmPlaying[1] = false;} //停掉前面
            if(isBgmPlaying[4]) {Danger.stop(); isBgmPlaying[4] = false;} //停掉后面
            Peace.loop(); isBgmPlaying[2] = true;
        }
        if((page>=12 && page<14) && !isBgmPlaying[4]) //3危机四伏，龙卷风
        {
            if(isBgmPlaying[2]) {Peace.stop(); isBgmPlaying[2] = false;} //停掉前面
            if(isBgmPlaying[3]) {Mystic.stop(); isBgmPlaying[3] = false;} //停掉后面
            Danger.loop(); isBgmPlaying[4] = true;
        }
        if((page>=14 && page<20) && !isBgmPlaying[3]) //4神秘水域，初遇小虎
        {
            if(isBgmPlaying[4]) {Danger.stop(); isBgmPlaying[4] = false;} //停掉前面及后面
            Mystic.loop(); isBgmPlaying[3] = true;
        }
        if((page>=20 && page<26) && !isBgmPlaying[4]) //5危机四伏，小虎追击
        {
            if(isBgmPlaying[3]) {Mystic.stop(); isBgmPlaying[3] = false;} //停掉前面
            if(isBgmPlaying[2]) {Peace.stop(); isBgmPlaying[2] = false;} //停掉后面
            Danger.loop(); isBgmPlaying[4] = true;
        }
        if((page>=26 && page<30) && !isBgmPlaying[3]) //6神秘水域，小香捉墨鱼
        {
            if(isBgmPlaying[4]) {Danger.stop(); isBgmPlaying[4] = false;} //停掉前面
            if(isBgmPlaying[0]) {OceanPacific.stop(); isBgmPlaying[0] = false;} //停掉后面
            Mystic.loop(); isBgmPlaying[3] = true;
        }
        if((page>=30 && page<36) && !isBgmPlaying[0]) //7平静之洋，鲸的故事
        {
            if(isBgmPlaying[3]) {Mystic.stop(); isBgmPlaying[3] = false;} //停掉前面
            if(isBgmPlaying[4]) {Danger.stop(); isBgmPlaying[4] = false;} //停掉后面
            OceanPacific.loop(); isBgmPlaying[0] = true;
        }
        if((page>=36 && page<42) && !isBgmPlaying[4]) //8危机四伏，捕鲸船
        {
            if(isBgmPlaying[0]) {OceanPacific.stop(); isBgmPlaying[0] = false;} //停掉前面
            if(isBgmPlaying[2]) {Peace.stop(); isBgmPlaying[2] = false;} //停掉后面
            Danger.loop(); isBgmPlaying[4] = true;
        }
        if((page>=42 && page<48) && !isBgmPlaying[2]) //9平静浅海，海豚作业区
        {
            if(isBgmPlaying[4]) {Danger.stop(); isBgmPlaying[4] = false;} //停掉前面
            if(isBgmPlaying[3]) {Mystic.stop(); isBgmPlaying[3] = false;} //停掉后面
            Peace.loop(); isBgmPlaying[2] = true;
        }
        if((page>=48 && page<55) && !isBgmPlaying[3]) //10神秘水域，海人行动
        {
            if(isBgmPlaying[2]) {Peace.stop(); isBgmPlaying[2] = false;} //停掉前面及后面
            Mystic.loop(); isBgmPlaying[3] = true;
        }
        if((page>=55 && page<57) && !isBgmPlaying[2]) //11平静浅海，重新启程
        {
            if(isBgmPlaying[3]) {Mystic.stop(); isBgmPlaying[3] = false;} //停掉后面
            if(isBgmPlaying[1]) {FishPond.stop(); isBgmPlaying[1] = false;} //停掉后面
            Peace.loop(); isBgmPlaying[2] = true;
        }
        if((page>=57 && page<64) && !isBgmPlaying[1]) //11鱼塘，结局
        {
            if(isBgmPlaying[2]) {Peace.stop(); isBgmPlaying[2] = false;} //停掉前面
            FishPond.loop(); isBgmPlaying[1] = true;
        }
        if(page>=pageNum)
        {
            FishPond.stop(); isBgmPlaying[1] = false; //停掉前面
        }
    }

    public void mousePressed(MouseEvent e)
    {
        if(isRunning)
        {
            if(e.getButton() == MouseEvent.BUTTON1) //左键点击事件
            {
                if(lang.isTyping) lang.ResetTyper();
                else if(page<pageNum)
                {
                    lang.pageNum -= 1; lang.ResetTyper(); page += 1;
                    lang.ChangePage();
                    if(page==pageNum) TheEnd.play();
                    ResetDrawer(page-1);
                }
            }
        }
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e)
    {
        if(isRunning)
        {
            if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_SPACE)  //右方向键或空格
            {
                if(lang.isTyping) lang.ResetTyper();
                else if(page<pageNum)
                {
                    lang.pageNum -= 1; lang.ResetTyper(); page += 1;
                    lang.ChangePage();
                    if(page==pageNum) TheEnd.play();
                    ResetDrawer(page-1);
                }
            }

            if(e.getKeyCode()==KeyEvent.VK_LEFT )  //左方向键
            {
                if(lang.isTyping) lang.ResetTyper();
                else if(page>0)
                {
                    lang.pageNum += 1; lang.ResetTyper(); page -= 1;
                    lang.ChangePage();
                    ResetDrawer(page+1);
                }
            }
        }
    }
    public void keyReleased(KeyEvent e) {}
}
