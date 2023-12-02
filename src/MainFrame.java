import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.*;
import javax.swing.JFrame;

public class MainFrame extends JFrame implements ActionListener
{
    //实例化各所需类
    CardLayout card = new CardLayout();
    JPanel pane = new JPanel();

    //界面对象
    HomePage hp = new HomePage();
    SaveLoadPage sl = new SaveLoadPage();
    AdditionPage about = new AdditionPage();
    Game game = new Game();

    SubWin1 subWin1 = new SubWin1();
    SubWin2 subWin2 = new SubWin2();
    SubWin3 subWin3 = new SubWin3();
    VideoWin1 vdWin1 = new VideoWin1();
    VideoWin2 vdWin2 = new VideoWin2();

    public MainFrame()
    {
        super("鲸");

        //将LookAndFeel设置成Windows样式
        try { UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); }
        catch (Exception ex) { ex.printStackTrace(); }

        //图标设置
        Image gameIcon_Img = (new ImageIcon
                (Objects.requireNonNull(this.getClass().getResource("gameIcon.png")))).getImage();
        this.setIconImage(gameIcon_Img);
        this.setSize(72, 72);
        this.setVisible(true);

        //设置卡片布局并添加画布
        pane.setLayout(card);
        pane.add(hp,"HomePage");
        pane.add(sl,"SaveLoadPage");
        pane.add(game,"GamePage");
        pane.add(about,"AdditionPage");

        //按钮监听

        //游戏界面上的按钮
        addMouseListener(game);
        addKeyListener(game); //键盘监听
        game.nextPButton_Bt.addActionListener(this);
        game.prePButton_Bt.addActionListener(this);
        game.saveButton_Bt.addActionListener(this);
        game.loadButton_Bt.addActionListener(this);
        game.returnButton_Bt.addActionListener(this);
        game.aboutButton_Bt.addActionListener(this);

        //存储读取界面上的按钮
        sl.nextPButton_Bt.addActionListener(this);
        sl.prePButton_Bt.addActionListener(this);
        sl.returnButton_Bt.addActionListener(this);
        for(int i=0; i<6; i++) sl.recordButton_Bts[i].addActionListener(this);

        //主界面上的按钮
        hp.startButton_Bt.addActionListener(this);
        hp.loadButton_Bt.addActionListener(this);
        hp.quitButton_Bt.addActionListener(this);

        //提示窗口上的按钮
        subWin1.yesButton_Bt.addActionListener(this);
        subWin1.noButton_Bt.addActionListener(this);
        subWin2.yesButton_Bt.addActionListener(this);
        subWin2.noButton_Bt.addActionListener(this);
        subWin3.yesButton_Bt.addActionListener(this);
        subWin3.noButton_Bt.addActionListener(this);

        //彩蛋窗口上的按钮
        about.returnButton_Bt.addActionListener(this);
        about.paperWhaleButton_Bt.addActionListener(this);
        about.paperDolphinButton_Bt.addActionListener(this);

        //播放主题背景音乐
        game.OceanPacific.loop();

        //设置窗口属性
        add(pane);
        setSize(1170,765+28); //窗口大小
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)  //处理各按钮事件
    {
        //主界面按钮
        if(e.getSource() == hp.startButton_Bt) //开始按钮
        {
            game.ButtonClick.play();
            setSize(1180,1004); //窗口大小
            game.Reset(); game.isRunning = true;
            card.show(pane, "GamePage");
            requestFocus();
            game.OceanPacific.stop();
            game.Splash.play();
        }

        if(e.getSource() == hp.loadButton_Bt) //载入按钮(主菜单上)
        {
            game.ButtonClick.play();
            sl.isLoad = true; sl.ChangeTitle();
            sl.isFromMain = true;
            setSize(1170,765+28); //窗口大小
            card.show(pane, "SaveLoadPage");
            requestFocus();
        }

        if(e.getSource() == hp.quitButton_Bt) //退出按钮
        {
            game.ButtonClick.play();
            System.exit(0);
        }

        //存储读取界面按钮
        if(e.getSource() == sl.nextPButton_Bt) //下一页按钮
        {
            game.ButtonClick.play();
            if(sl.page < sl.pageNum) sl.page+=1;
            sl.nextPButton_Bt.setVisible(sl.page < sl.pageNum); //若有下一页，则显示下一页按钮
            sl.prePButton_Bt.setVisible(sl.page > 0);
            sl.ButtonLayout(sl.page);
        }

        if(e.getSource() == sl.prePButton_Bt) //上一页按钮
        {
            game.ButtonClick.play();
            if(sl.page > 0) sl.page-=1;
            sl.nextPButton_Bt.setVisible(sl.page < sl.pageNum); //若有下一页，则显示下一页按钮
            sl.prePButton_Bt.setVisible(sl.page > 0);
            sl.ButtonLayout(sl.page);
        }

        if(e.getSource() == sl.returnButton_Bt) //返回按钮
        {
            game.ButtonClick.play();
            game.isRunning = true;
            if(sl.isFromMain) //从主界面读取
            { setSize(1170, 765+28); card.show(pane, "HomePage");}
            else //进行中存取
            { setSize(1180,1004); card.show(pane, "GamePage");}
            requestFocus();
            sl.page = 0;
            sl.nextPButton_Bt.setVisible(sl.page < sl.pageNum);
            sl.prePButton_Bt.setVisible(sl.page > 0);
            sl.ButtonLayout(sl.page);

            subWin1.dispose();
            subWin2.dispose();
            subWin3.dispose();
        }

        for(int i=0; i<6; i++)
        {
            if(e.getSource() == sl.recordButton_Bts[i]) //存档按钮
            {
                game.ButtonClick.play();
                if(!sl.isLoad) //存档动作的处理
                {
                    if(sl.recordIsEmpty[i]) //存入空档
                    {
                        sl.SaveE(i, game);
                        game.Splash.play();
                    }
                    else //覆盖原有存档
                    {
                        sl.SL_loc = i;
                        subWin1.setSize(350, 300);
                        subWin1.setLocationRelativeTo(pane); //null-屏幕中央，pane-窗口中央
                        subWin1.setResizable(false);
                        subWin1.setVisible(true);
                        requestFocus();
                    }
                }
                else //读档动作的处理
                {
                    if(!sl.recordIsEmpty[i])
                    {
                        if(sl.isFromMain)
                        {
                            sl.SL_loc = i;
                            int game_page = sl.Load(sl.SL_loc);
                            int current_page = game.page; //记录读取前所在页面
                            if(game_page >= 0) //读到内容
                            {
                                setSize(1180,1004);
                                game.Reset(); game.isRunning = true;
                                game.page = game_page;
                                game.currentPage = current_page;
                                game.lang.pageNum -= game.page;
                                game.lang.ChangePage();
                                card.show(pane, "GamePage");
                                requestFocus();
                                game.MusicStop();

                                sl.page = 0;
                                sl.nextPButton_Bt.setVisible(sl.page < sl.pageNum);
                                sl.prePButton_Bt.setVisible(sl.page > 0);
                                sl.ButtonLayout(sl.page);
                            }
                            game.Splash.play();
                        }
                        else
                        {
                            sl.SL_loc = i;
                            subWin2.setSize(350, 300);
                            subWin2.setLocationRelativeTo(pane); //null-屏幕中央，pane-窗口中央
                            subWin2.setResizable(false);
                            subWin2.setVisible(true);
                            requestFocus();
                        }
                    }
                }
            }
        }

        if(e.getSource() == subWin1.yesButton_Bt) //覆盖原存档提示窗口“确认按钮”
        {
            game.ButtonClick.play();
            subWin1.dispose(); //子窗口关闭
            sl.SaveNE(sl.SL_loc, game);
            game.Splash.play();
        }

        if(e.getSource() == subWin1.noButton_Bt) //覆盖原存档提示窗口“取消按钮”
        {
            game.ButtonClick.play();
            subWin1.dispose(); //子窗口关闭
        }

        if(e.getSource() == subWin2.yesButton_Bt) //读档提示窗口“确认按钮”
        {
            game.ButtonClick.play();
            subWin2.dispose(); //子窗口关闭
            int game_page = sl.Load(sl.SL_loc);
            int current_page = game.page; //记录读取前所在页面
            if(game_page >= 0) //读到内容
            {
                setSize(1180,1004);
                game.Reset(); game.isRunning = true;
                game.page = game_page;
                game.currentPage = current_page;
                game.lang.pageNum -= game.page;
                game.lang.ChangePage();
                card.show(pane, "GamePage");
                requestFocus();
                game.MusicStop();

                sl.page = 0;
                sl.nextPButton_Bt.setVisible(sl.page < sl.pageNum);
                sl.prePButton_Bt.setVisible(sl.page > 0);
                sl.ButtonLayout(sl.page);
            }
            game.Splash.play();
        }

        if(e.getSource() == subWin2.noButton_Bt) //读档提示窗口“取消按钮”
        {
            game.ButtonClick.play();
            subWin2.dispose(); //子窗口关闭
        }

        if(e.getSource() == subWin3.yesButton_Bt) //返回主界面提示窗口“确认按钮”
        {
            game.ButtonClick.play();
            game.Reset(); game.isRunning = false;
            setSize(1170, 765+28);
            card.show(pane, "HomePage");
            requestFocus();
            game.MusicStop();
            game.OceanPacific.loop();
        }

        if(e.getSource() == subWin3.noButton_Bt) //返回主界面提示窗口“取消按钮”
        {
            game.ButtonClick.play();
            subWin3.dispose(); //子窗口关闭
        }

        //游戏界面按钮
        if(e.getSource() == game.nextPButton_Bt) //下一页按钮
        {
            game.ButtonClick.play();
            if(game.lang.isTyping) game.lang.ResetTyper();
            else if(game.page<game.pageNum)
            {
                game.lang.pageNum -= 1; game.lang.ResetTyper(); game.page += 1;
                game.lang.ChangePage();
                if(game.page==game.pageNum) game.TheEnd.play();
                game.ResetDrawer(game.page-1);
            }
            requestFocus();
        }

        if(e.getSource() == game.prePButton_Bt) //上一页按钮
        {
            game.ButtonClick.play();
            if(game.lang.isTyping) game.lang.ResetTyper();
            else if(game.page>0)
            {
                game.lang.pageNum += 1; game.lang.ResetTyper(); game.page -= 1;
                game.lang.ChangePage();
                game.ResetDrawer(game.page+1);
            }
            requestFocus();
        }

        if(e.getSource() == game.saveButton_Bt) //存储按钮
        {
            game.ButtonClick.play();
            game.isRunning = false;
            sl.isLoad = false; sl.ChangeTitle();
            sl.isFromMain = false;
            setSize(1170,765+28); //窗口大小
            card.show(pane, "SaveLoadPage");
            requestFocus();
        }

        if(e.getSource() == game.loadButton_Bt) //读取按钮
        {
            game.ButtonClick.play();
            game.isRunning = false;
            sl.isLoad = true; sl.ChangeTitle();
            sl.isFromMain = false;
            setSize(1170,765+28); //窗口大小
            card.show(pane, "SaveLoadPage");
            requestFocus();
        }

        if(e.getSource() == game.returnButton_Bt) //返回主界面按钮
        {
            game.ButtonClick.play();
            game.isRunning = false;
            subWin3.setSize(350, 300);
            subWin3.setLocationRelativeTo(pane); //null-屏幕中央，pane-窗口中央
            subWin3.setResizable(false);
            subWin3.setVisible(true);
            requestFocus();
        }

        if(e.getSource() == game.aboutButton_Bt) //结束后的彩蛋按钮
        {
            game.ButtonClick.play();
            //game.Reset(); game.isRunning = false;
            setSize(1170,765+28); //窗口大小
            card.show(pane, "AdditionPage");
            requestFocus();
        }

        if(e.getSource() == about.returnButton_Bt) //彩蛋窗口上的返回按钮
        {
            game.ButtonClick.play();
            vdWin1.setVisible(false);
            vdWin2.setVisible(false);
            game.Reset(); game.isRunning = false;
            setSize(1170, 765+28);
            card.show(pane, "HomePage");
            requestFocus();
            game.MusicStop();
            game.OceanPacific.loop();
        }

        if(e.getSource() == about.paperWhaleButton_Bt)
        {
            game.ButtonClick.play();
            vdWin2.setVisible(false);
            vdWin1.setSize(800, 600);
            vdWin1.setLocationRelativeTo(pane); //null-屏幕中央，pane-窗口中央
            vdWin1.setResizable(false);
            vdWin1.setVisible(true);
            vdWin1.playVideo();
        }

        if(e.getSource() == about.paperDolphinButton_Bt)
        {
            game.ButtonClick.play();
            vdWin1.setVisible(false);
            vdWin2.setSize(800, 600);
            vdWin2.setLocationRelativeTo(pane); //null-屏幕中央，pane-窗口中央
            vdWin2.setResizable(false);
            vdWin2.setVisible(true);
            vdWin2.playVideo();
        }
    }

    public static void main(String[] args)
    {
        MainFrame mf = new MainFrame();
        while(true) {mf.game.run();}
    }
}
