package nightwalker.api.services.extracts.plans;

import nightwalker.api.models.resources.PageResource;
import nightwalker.api.services.parses.xml.XmlParser;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.*;

public class ElementsParserTest {
    /**
     * 抽出対象のページ*
     */
    private PageResource Page;

    /**
     * 抽出計画* 
     */
    private Element Plan;
    
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
        html.append("        <div class=\"block entry\" id=\"entry4631\" style=\"cursor: pointer; background-color: rgb(225, 222, 201);\">");
        html.append("          <ul class=\"info\" id=\"info_1\">");
        html.append("            <li class=\"category\"><!--Category : --><a href=\"blog-category-2.html\">X-Videos</a></li>");
        html.append("          </ul>");
        html.append("        </div>");
        html.append("        <div class=\"block entry\" id=\"entry4631\" style=\"cursor: pointer; background-color: rgb(225, 222, 201);\">");
        html.append("          <ul class=\"info\" id=\"info_1\">");
        html.append("            <li class=\"category\"><!--Category : --><a href=\"blog-category-2.html\">FC2動画</a></li>");
        html.append("          </ul>");
        html.append("        </div>");
        html.append("      </article>");
        html.append("    </div>");
        html.append("  </body>");
        html.append("</html>");
        this.Page = new PageResource("http://example.com/", html.toString());

        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<elements selector=\".entry\">");
        planStr.append("    <filter selector=\".info .category a\" attr=\"text\" equals=\"FC2動画\" />");
        planStr.append("</elements>");
        this.Plan = XmlParser.parseFromString(planStr.toString());
    }

    @Test
    public void filter有りで指定通りのHTML要素が取得できる() throws Exception {
        Elements containers = ElementsParser.get(this.Plan, this.Page);
        assertEquals(containers.size(), 2);

        containers.forEach(el ->
                assertEquals(el.attr("class"), "block entry"));
    }
    
    @Test
    public void filter無しで指定通りのHTML要素が取得できる() throws Exception {
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<elements selector=\".entry\" />");
        Element plan = XmlParser.parseFromString(planStr.toString());
        
        Elements containers = ElementsParser.get(plan, this.Page);
        assertEquals(containers.size(), 3);
        
        containers.forEach(el ->
            assertEquals(el.attr("class"), "block entry"));
    }
    
    @Test
    public void selectorで子階層まで指定してHTML要素が取得できる() throws Exception {
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<elements selector=\".entry .info li\" />");
        Element plan = XmlParser.parseFromString(planStr.toString());

        Elements containers = ElementsParser.get(plan, this.Page);
        assertEquals(containers.size(), 3);

        containers.forEach(el ->
                assertEquals(el.attr("class"), "category"));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void elements以外の要素は禁止() throws Exception {
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<value selector=\".entry\" />");
        Element plan = XmlParser.parseFromString(planStr.toString());


        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Node名が不正です。 -> " + plan.getNodeName());
        ElementsParser.get(plan, this.Page);
    }

    @Test
    public void selectorが必須() throws Exception {
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<elements />");
        Element plan = XmlParser.parseFromString(planStr.toString());


        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("必須のアトリビュートキーが存在しません。 -> " + plan.getAttributes());
        ElementsParser.get(plan, this.Page);
    }
}