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

public class PageParserTest {

    /**
     * ページ*
     */
    private PageResource Page;
    
    @Before
    public void setUp() throws IOException, ParserConfigurationException, SAXException
    {
        // HTMLの作成
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
        html.append("          <a class=\"testpage\" href=\"http://www.28lab.com/\">");
        html.append("          <a class=\"testpage2\" href=\"http://www.28lab.com/\">");
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
    }
    
    @Test
    public void 抽出計画のURLからページを生成できる() throws Exception {
        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<page url=\"http://www.28lab.com/\">");
        planStr.append("    <elements selector=\".info\" />");
        planStr.append("</page>");
        Element plan = XmlParser.parseFromString(planStr.toString());
        
        PageResource page = PageParser.parse(plan);
        assertTrue(page.getHtml().contains("28Lab"));
    }
    
    @Test
    public void 渡されたURLとHTMLからページが生成できる() throws Exception {
        // HTMLの作成
        String pageStr = "&lt;html&gt;" +
                "  &lt;head&gt;&lt;/head&gt;" +
                "  &lt;body&gt;" +
                "    &lt;p&gt;test page&lt;/p&gt;" +
                "    &lt;h1&gt;test value&lt;/h1&gt;" +
                "  &lt;/body&gt;" +
                "&lt;/html&gt;";

        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<page url=\"http://www.example.com/\" html=\"" + pageStr + "\">");
        planStr.append("  <values selector=\"h1\" attr=\"text\" />");
        planStr.append("</page>");
        Element plan = XmlParser.parseFromString(planStr.toString());

        PageResource page = PageParser.parse(plan);
        assertTrue(page.getHtml().contains("test value"));
    }
    
    @Test
    public void セレクタのアトリビュート値からページが生成できる() throws Exception {
        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<page selector=\".testpage\" attr=\"href\">");
        planStr.append("  <values selector=\"h1\" attr=\"text\" />");
        planStr.append("</page>");
        Element plan = XmlParser.parseFromString(planStr.toString());

        org.jsoup.nodes.Element container = this.Page.getDocument().select(".entry").first();
        
        PageResource innerPage = PageParser.parse(plan, container, "http://www.28lab.com");
        assertTrue(innerPage.getHtml().contains("28Lab"));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
     public void page以外の要素は禁止() throws Exception {
        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<elements url=\"http://www.28lab.com/\">");
        planStr.append("    <elements selector=\".info\" />");
        planStr.append("</elements>");
        Element plan = XmlParser.parseFromString(planStr.toString());

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Node名が不正です。 -> " + plan.getNodeName());
        PageParser.parse(plan);
    }

    @Test
    public void page以外の要素は禁止セレクタ使用版() throws Exception {
        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<elements selector=\".testpage\" attr=\"href\">");
        planStr.append("  <values selector=\"h1\" attr=\"text\" />");
        planStr.append("</elements>");
        Element plan = XmlParser.parseFromString(planStr.toString());

        org.jsoup.nodes.Element container = this.Page.getDocument().select(".entry").first();

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Node名が不正です。 -> " + plan.getNodeName());
        PageParser.parse(plan, container, "http://www.28lab.com");
    }

    @Test
    public void URLとHTML以外は使用不可() throws Exception {
        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<page url=\"http://www.28lab.com/\" attr=\"href\">");
        planStr.append("    <elements selector=\".info\" />");
        planStr.append("</page>");
        Element plan = XmlParser.parseFromString(planStr.toString());

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("アトリビュートキーが不正です。 -> " + plan.getAttributes());
        PageParser.parse(plan);
    }

    @Test
    public void URLが必須() throws Exception {
        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<page>");
        planStr.append("    <elements selector=\".info\" />");
        planStr.append("</page>");
        Element plan = XmlParser.parseFromString(planStr.toString());

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("必須のアトリビュートキーが存在しません。 -> " + plan.getAttributes());
        PageParser.parse(plan);
    }

    @Test
    public void SelectorOrAttr以外は使用不可() throws Exception {
        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<page selector=\".textBody .imgwrap a\" attr=\"href\" url=\"http://www.28lab.com\">");
        planStr.append("    <values selector=\".info\" />");
        planStr.append("</page>");
        Element plan = XmlParser.parseFromString(planStr.toString());

        org.jsoup.nodes.Element container = this.Page.getDocument().select(".entry").first();
        
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("アトリビュートキーが不正です。 -> " + plan.getAttributes());
        PageParser.parse(plan, container, "http://www.28lab.com");
    }

    @Test
    public void URLを取得する対象の要素数は必ず1() throws Exception {
        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<page selector=\"a\" attr=\"href\" >");
        planStr.append("    <values selector=\".info\" />");
        planStr.append("</page>");
        Element plan = XmlParser.parseFromString(planStr.toString());

        org.jsoup.nodes.Element container = this.Page.getDocument().select(".entry").first();

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("URLを取得する対象の要素数を1にしてください。");
        PageParser.parse(plan, container, "http://www.28lab.com");
    }
}