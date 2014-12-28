package nightwalker.api.models.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 動画*
 * Created by takashi on 2014/12/25.
 */
public class Movie {
    /**
     * 動画タイプ*
     */
    @Getter
    @Setter
    private MovieType MovieType;

    /**
     * 画像情報*
     */
    @Getter
    @Setter
    private List<String> Image;

    /**
     * 動画情報* 
     */
    @Getter
    @Setter
    private List<String> Video;

    /**
     * 概要情報*
     */
    @Getter
    @Setter
    private List<String> Desc;

    /**
     * 時間情報*
     */
    @Getter
    @Setter
    private List<String > Time;

    /**
     * タグ情報*
     */
    @Getter
    @Setter
    private List<String> Tags;

    /**
     * 次に読み込むページ情報*
     */
    @Getter
    @Setter
    private List<String> NextPage;
}
