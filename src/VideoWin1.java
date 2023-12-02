import com.sun.javafx.application.PlatformImpl;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

public class VideoWin1 extends JFrame implements Runnable
{
    public File file = new File("src/whale.mp4");
    public String url = file.toURI().toString();
    public Media media = new Media(url);
    public MediaPlayer player;
    public MediaView view;

    public JFXPanel jfxPanel = new JFXPanel();
    public StackPane root = new StackPane();
    public Scene scene;

    public VideoWin1()
    {
        super("折纸-鲸");

        //图标设置
        Image gameIcon_Img = (new ImageIcon
                (Objects.requireNonNull(this.getClass().getResource("gameIcon.png")))).getImage();
        this.setIconImage(gameIcon_Img);

        player = new MediaPlayer(media);
        player.setMute(true);
        view = new MediaView(player);
        view.setPreserveRatio(true);
        root.getChildren().add(view);
        player.setOnEndOfMedia(this);
        scene = new Scene(root, 800, 600);

        PlatformImpl.runLater(() -> {jfxPanel.setScene(scene);}); //多线程
        super.add(jfxPanel);
    }

    public void playVideo()
    {
        player.stop();
        player.play();
    }

    @Override
    public void run()
    {
        player.stop();
        playVideo();
    }
}
