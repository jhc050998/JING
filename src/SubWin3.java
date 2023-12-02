import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class SubWin3 extends JFrame
{
    //导入窗口所用的图片
    Image subWinTag3_Img = Toolkit.getDefaultToolkit().getImage(
            SubWin1.class.getResource("subWinTag3.png"));
    ImageIcon subWinTag3_Icon = new ImageIcon(subWinTag3_Img);
    JLabel subWinTag3_Lb;

    public ImageIcon yesButton_Img
            = new ImageIcon(Objects.requireNonNull(SubWin1.class.getResource("yesButton.png")));
    public ImageIcon yesButtonA_Img
            = new ImageIcon(Objects.requireNonNull(SubWin1.class.getResource("yesButtonA.png")));
    public JButton yesButton_Bt;

    public ImageIcon noButton_Img
            = new ImageIcon(Objects.requireNonNull(SubWin1.class.getResource("noButton.png")));
    public ImageIcon noButtonA_Img
            = new ImageIcon(Objects.requireNonNull(SubWin1.class.getResource("noButtonA.png")));
    public JButton noButton_Bt;

    public AudioClip ButtonTouch //音效1（按钮碰触）
            = Applet.newAudioClip(this.getClass().getResource("ButtonTouch.wav"));

    public SubWin3()
    {
        super("提示");

        //将LookAndFeel设置成Windows样式
        try { UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); }
        catch (Exception ex) { ex.printStackTrace(); }

        //图标设置
        Image gameIcon_Img = (new ImageIcon
                (Objects.requireNonNull(this.getClass().getResource("gameIcon.png")))).getImage();
        this.setIconImage(gameIcon_Img);

        //构造窗口
        this.setLayout(null);

        //标签
        this.subWinTag3_Lb = new JLabel(this.subWinTag3_Icon);
        this.subWinTag3_Lb.setBounds(50,10,250,100);

        //按钮
        yesButton_Bt = new JButton(yesButton_Img);
        yesButton_Bt.setBounds(75,120,80,46);
        yesButton_Bt.setOpaque(false);
        yesButton_Bt.setContentAreaFilled(false);
        yesButton_Bt.setBorderPainted(false);
        yesButton_Bt.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {yesButton_Bt.setIcon(yesButtonA_Img); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {yesButton_Bt.setIcon(yesButton_Img);}
        });

        noButton_Bt = new JButton(noButton_Img);
        noButton_Bt.setBounds(195,120,80,46);
        noButton_Bt.setOpaque(false);
        noButton_Bt.setContentAreaFilled(false);
        noButton_Bt.setBorderPainted(false);
        noButton_Bt.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e) {noButton_Bt.setIcon(noButtonA_Img); ButtonTouch.play();}
            public void mouseExited(MouseEvent e) {noButton_Bt.setIcon(noButton_Img);}
        });

        add(subWinTag3_Lb);
        add(yesButton_Bt);
        add(noButton_Bt);
    }
}
