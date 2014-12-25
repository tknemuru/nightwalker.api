package nightwalker.api.services.extracts.movies;

import nightwalker.api.models.resources.MovieType;
import nightwalker.api.models.resources.PageResource;
import nightwalker.api.services.parses.xml.XmlParseProvider;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.*;

public class MovieExtractorTest {
    /**
     * 要素生成機能*
     */
    private MovieExtractor Extractor;

    /**
     * 抽出された動画*
     */
    private nightwalker.api.models.resources.Movie Movie;
 
    @Before
    public void setUp() throws IOException, ParserConfigurationException, SAXException
    {
        // 抽出対象のHTMLの作成
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("  <head></head>");
        html.append("  <body>");
        html.append("    <div class=\"layout\" style=\"position: absolute; left: 750px; top: 3071px;\">");
        html.append("      <article>");
        html.append("        <div class=\"block entry\" id=\"entry4631\" style=\"cursor: pointer; background-color: rgb(225, 222, 201);\">");
        html.append("          <ul class=\"info\" id=\"info_1\">");
        html.append("            <li class=\"category\"><!--Category : --><a href=\"blog-category-2.html\">FC2動画</a></li>");
        html.append("          </ul>");
        html.append("        </div>");
        html.append("      </article>");
        html.append("    </div>");
        html.append("  </body>");
        html.append("</html>");
        PageResource page = new PageResource("http://example.com/", html.toString());
        
        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<element selector=\".entry\">");
        planStr.append("    <filter selector=\".info .category a\" attr=\"text\" equals=\"FC2動画\" />");
        planStr.append("</element>");
        Element plan = XmlParseProvider.parseFromString(planStr.toString());
        
        this.Extractor = new MovieExtractor(plan);
//        this.Movie = this.Extractor.extract();
    }
   
    @Test
    public void 動画タイプが取得できる() throws Exception {
//        assertEquals(this.Movie.getMovieType(), MovieType.XVideos);
    }
}