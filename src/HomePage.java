import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePage extends JPanel
{
    //导入窗口所用的所有图片
    Image homePage_Img = Toolkit.getDefaultToolkit().getImage(HomePage.class.getResource("cover.png"));
    Image title_Img = Toolkit.getDefaultToolkit().getImage(HomePage.class.getResource("title.png"));
    Image startButton_Img = Toolkit.getDefaultToolkit().getImage(HomePage.class.getResource("startButton.png"));
    Image loadButton_Img = Toolkit.getDefaultToolkit().getImage(HomePage.class.getResource("loadButtonM.png"));
    Image quitButton_Img = Toolkit.getDefaultToolkit().getImage(HomePage.class.getResource("quitButton.png"));
    Image startButtonA_Img = Toolkit.getDefaultToolkit().getImage(HomePage.class.getResource("startButtonA.png"));
    Image loadButtonA_Img = Toolkit.getDefaultToolkit().getImage(HomePage.class.getResource("loadButtonMA.png"));
    Image quitButtonA_Img = Toolkit.getDefaultToolkit().getImage(HomePage.class.getResource("quitButtonA.png"));

    ImageIcon homePage_Icon = new ImageIcon(homePage_Img);
    ImageIcon title_Icon = new ImageIcon(title_Img);
    ImageIcon startButton_Icon = new ImageIcon(startButton_Img);
    ImageIcon loadButton_Icon = new ImageIcon(loadButton_Img);
    ImageIcon quitButton_Icon = new ImageIcon(quitButton_Img);
    ImageIcon startButtonA_Icon = new ImageIcon(startButtonA_Img);
    ImageIcon loadButtonA_Icon = new ImageIcon(loadButtonA_Img);
    ImageIcon quitButtonA_Icon = new ImageIcon(quitButtonA_Img);

    //窗口中的标签
    JLabel homePage_Lb = new JLabel(homePage_Icon);
    JLabel title_Lb = new JLabel(title_Icon);

    //窗口中的按钮
    public JButton startButton_Bt = new JButton(startButton_Icon);
    public JButton loadButton_Bt = new JButton(loadButton_Icon);
    public JButton quitButton_Bt = new JButton(quitButton_Icon);

    public AudioClip ButtonTouch //音效1（按钮碰触）
            = Applet.newAudioClip(this.getClass().getResource("ButtonTouch.wav"));

    public HomePage() //窗口构造函数
    {
        //构造窗口
        this.setLayout(null);

        //控件布局
        homePage_Lb.setBounds(-1, 0, 765, 765);
        title_Lb.setBounds(762, 0, 405, 765);

        startButton_Bt.setBounds(768+134, 440, 124, 147);
        loadButton_Bt.setBounds(768+15, 600, 124, 147);
        quitButton_Bt.setBounds(768+268-15, 600, 124, 147);

        startButton_Bt.setOpaque(false); //去除按钮的白色背景填充
        loadButton_Bt.setOpaque(false);
        quitButton_Bt.setOpaque(false);

        startButton_Bt.setContentAreaFilled(false); //点击时不会重现黑背景
        loadButton_Bt.setContentAreaFilled(false);
        quitButton_Bt.setContentAreaFilled(false);

        startButton_Bt.setBorderPainted(false); //去除按钮边框
        loadButton_Bt.setBorderPainted(false);
        quitButton_Bt.setBorderPainted(false);

        //鼠标放到按钮上的改变
        startButton_Bt.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {startButton_Bt.setIcon(startButtonA_Icon); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {startButton_Bt.setIcon(startButton_Icon);}
        });
        loadButton_Bt.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {loadButton_Bt.setIcon(loadButtonA_Icon); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {loadButton_Bt.setIcon(loadButton_Icon);}
        });
        quitButton_Bt.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {quitButton_Bt.setIcon(quitButtonA_Icon); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {quitButton_Bt.setIcon(quitButton_Icon);}
        });

        //在窗口中添加控件
        this.add(startButton_Bt);
        this.add(loadButton_Bt);
        this.add(quitButton_Bt);

        this.add(title_Lb); //背景图标最后添加以保证位于最底下
        this.add(homePage_Lb);
    }
}
