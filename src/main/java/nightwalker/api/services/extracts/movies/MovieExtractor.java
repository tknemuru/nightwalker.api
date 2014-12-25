package nightwalker.api.services.extracts.movies;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import nightwalker.api.models.resources.Movie;
import nightwalker.api.models.resources.MovieType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * 動画の抽出機能を提供します。*
 * Created by takashi on 2014/12/25.
 */
public final class MovieExtractor {
    /**
     * 抽出計画*
     */
    private Element Plan;

    /**
     * コンストラクタ*
     * @param plan 抽出計画
     */
    public MovieExtractor(Element plan) {
        this.Plan = plan;
    }
    
    /**
     * 動画の抽出を行います。*
     * @return 抽出された動画
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public Movie extract() throws SAXException, IOException, ParserConfigurationException {
        // 動画リソースの生成
        Movie movie = new Movie();
        
        // 動画タイプの取得
        movie.setMovieType(MovieType.valueOf(this.Plan.getElementsByTagName("type").item(0).getTextContent()));

        return movie;
    }
}
