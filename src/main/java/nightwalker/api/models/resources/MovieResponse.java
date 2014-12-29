package nightwalker.api.models.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 動画レスポンス* 
 * Created by takashi on 2014/12/29.
 */
public class MovieResponse {
    /**
     * 動画リスト* 
     */
    @Getter
    @Setter
    private List<Movie> Movies;

    /**
     * 次に読み込むページのリスト*
     */
    @Getter
    @Setter
    private List<String> NextPages;

    /**
     * コンストラクタ*
     * @param movies 動画リスト
     * @param nextPages 次に読み込むページのリスト
     */
    public MovieResponse(List<Movie> movies, List<String> nextPages) {
        this.setMovies(movies);
        this.setNextPages(nextPages);
    }
}
