package nightwalker.api.services.extracts.movies;

import nightwalker.api.services.parses.xml.XmlParseProvider;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * 動画抽出機能を提供します。*
 * Created by takashi on 2014/12/25.
 */
public final class MovieExtractorProvider {
    /**
     * j-xvideos向け動画抽出機能を取得します。*
     * @return j-xvideos向け動画抽出機能
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public static final MovieExtractor getJapaneseXvideosExtractor() throws SAXException, IOException, ParserConfigurationException {
        Element plan = XmlParseProvider.parseFromFile("./src/main/java/nightwalker/api/services/extracts/plans/xml/j-xvideos-resource.xml");
        return new MovieExtractor(plan);
    }
}
