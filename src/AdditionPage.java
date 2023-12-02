import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdditionPage extends JPanel
{
    //导入窗口所用的所有图片
    Image background_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("background.png"));
    Image logo_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("ppbfLogo.png"));
    Image paperWhaleButton_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("paperWhaleButton.png"));
    Image paperDolphinButton_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("paperDolphinButton.png"));
    Image returnButton_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("returnButton.png"));
    Image returnButtonA_Img = Toolkit.getDefaultToolkit().getImage(SaveLoadPage.class.getResource("returnButtonA.png"));

    ImageIcon background_Icon = new ImageIcon(background_Img);
    ImageIcon logo_Icon = new ImageIcon(logo_Img);
    ImageIcon paperWhaleButton_Icon = new ImageIcon(paperWhaleButton_Img);
    ImageIcon paperDolphinButton_Icon = new ImageIcon(paperDolphinButton_Img);
    ImageIcon returnButton_Icon = new ImageIcon(returnButton_Img);
    ImageIcon returnButtonA_Icon = new ImageIcon(returnButtonA_Img);

    //窗口中的标签
    JLabel background_Lb = new JLabel(background_Icon);
    JLabel logo_Lb = new JLabel(logo_Icon);

    //窗口中的按钮
    public JButton paperWhaleButton_Bt = new JButton(paperWhaleButton_Icon);
    public JButton paperDolphinButton_Bt = new JButton(paperDolphinButton_Icon);
    public JButton returnButton_Bt = new JButton(returnButton_Icon);

    public AudioClip ButtonTouch //音效1（按钮碰触）
            = Applet.newAudioClip(this.getClass().getResource("ButtonTouch.wav"));

    public AdditionPage() //窗口构造函数
    {
        //构造窗口
        this.setLayout(null);

        //控件布局
        background_Lb.setBounds(-1, 0, 1170, 765);
        logo_Lb.setBounds(485,25,200,200);
        paperWhaleButton_Bt.setBounds(170, 250, 320, 240);
        paperDolphinButton_Bt.setBounds(680, 250, 320, 240);
        returnButton_Bt.setBounds(1050,685,80,46);

        returnButton_Bt.setOpaque(false); //去除按钮的白色背景填充
        returnButton_Bt.setContentAreaFilled(false); //点击时不会重现黑背景
        returnButton_Bt.setBorderPainted(false); //去除按钮边框
        returnButton_Bt.addMouseListener(new MouseAdapter(){ //鼠标放到按钮上的改变
            public void mouseEntered(MouseEvent e) {returnButton_Bt.setIcon(returnButtonA_Icon); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {returnButton_Bt.setIcon(returnButton_Icon);}
        });

        //在窗口中添加控件
        this.add(paperWhaleButton_Bt);
        this.add(paperDolphinButton_Bt);
        this.add(returnButton_Bt);
        this.add(logo_Lb);
        this.add(background_Lb); //背景图标最后添加以保证位于最底下
    }
}
