package nightwalker.api.models.resources.html;

import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * 画像ファイル
 * Created by takashi on 2014/12/16.
 */
public class Image {
    /**
     * URL
     */
    @Getter
    private String Url;

    /**
     * 幅
     */
    @Getter
    private int Width;

    /**
     * 高さ
     */
    @Getter
    private int Height;

    /**
     * コンストラクタ
     * @param Url 画像URL
     * @throws IOException
     */
    public Image(String Url) throws IOException {
        BufferedImage image = ImageIO.read(new URL(Url));
        this.Url = Url;
        this.Width = image.getWidth();
        this.Height = image.getHeight();
    }
}
