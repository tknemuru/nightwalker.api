package nightwalker.api.services.extracts.plans;

import nightwalker.api.models.resources.PageResource;
import nightwalker.api.services.parses.xml.XmlParser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ValuesParserTest {
    /**
     * 抽出対象のページ*
     */
    private PageResource Page;

    /**
     * 抽出対象のコンテナ*
     */
    private org.jsoup.nodes.Element Container;

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
        html.append("            <li class=\"tags\">tag1</li>");
        html.append("            <li class=\"tags\">tag2</li>");
        html.append("            <li class=\"tags\">tag3</li>");
        html.append("          </ul>");
        html.append("          <h1>");
        html.append("            <a href=\"http://www.example.com/test.html\">desc test 1</a>");
        html.append("            <a class=\"formattest\" href=\"test.html\">desc test 1</a>");
        html.append("            <img class=\"formattest2\" src=\"test.jpg\">desc test 1</img>");
        html.append("          </h1>");
        html.append("        </div>");
        html.append("        <div class=\"block entry\" id=\"entry4631\" style=\"cursor: pointer; background-color: rgb(225, 222, 201);\">");
        html.append("          <ul class=\"info\" id=\"info_1\">");
        html.append("            <li class=\"category\"><!--Category : --><a href=\"blog-category-2.html\">X-Videos</a></li>");
        html.append("          </ul>");
        html.append("          <h1>");
        html.append("            <a href=\"http://www.example.com/test.html\">desc test 2</a>");
        html.append("          </h1>");
        html.append("        </div>");
        html.append("        <div class=\"block entry\" id=\"entry4631\" style=\"cursor: pointer; background-color: rgb(225, 222, 201);\">");
        html.append("          <ul class=\"info\" id=\"info_1\">");
        html.append("            <li class=\"category\"><!--Category : --><a href=\"blog-category-2.html\">FC2動画</a></li>");
        html.append("          </ul>");
        html.append("          <h1>");
        html.append("            <a href=\"http://www.example.com/test.html\">desc test 3</a>");
        html.append("          </h1>");
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
        planStr.append("</elements>");
        Element plan = XmlParser.parseFromString(planStr.toString());
        
        // 抽出対象のコンテナの作成
        this.Container = ElementsParser.parse(plan, this.Page).first();
    }
    
    @Test
    public void 指定した要素のtextがvaluesとして取得できる() throws Exception {
        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<values selector=\"h1 a\" attr=\"text\" />");
        Element plan = XmlParser.parseFromString(planStr.toString());
        
        List<String> values = ValuesParser.parse(plan, this.Container, this.Page.getUrl().toString());
        
        assertEquals("desc test 1", values.get(0));
    }

    @Test
    public void 指定した要素のattrがvaluesとして取得できる() throws Exception {
        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<values selector=\"h1 a\" attr=\"href\" />");
        Element plan = XmlParser.parseFromString(planStr.toString());

        List<String> values = ValuesParser.parse(plan, this.Container, this.Page.getUrl().toString());

        assertEquals("http://www.example.com/test.html", values.get(0));
    }
    
    @Test
    public void 指定した要素が複数であってもvaluesが取得できる() throws Exception {
        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<values selector=\".info .tags\" attr=\"text\" />");
        Element plan = XmlParser.parseFromString(planStr.toString());

        List<String> expected = new ArrayList<>();
        expected.add("tag1");
        expected.add("tag2");
        expected.add("tag3");
        
        List<String> values = ValuesParser.parse(plan, this.Container, this.Page.getUrl().toString());
        
        assertArrayEquals(expected.toArray(), values.toArray());
    }

    @Test
    public void ページからvaluesが取得できる() throws Exception {
        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<values selector=\"a\" attr=\"href\" />");
        Element plan = XmlParser.parseFromString(planStr.toString());

        List<String> values = ValuesParser.parse(plan, this.Page.getDocument(), this.Page.getUrl().toString());

        assertEquals(7, values.size());
    }

    @Test
    public void hrefとsrcの値はURLの正規化が行われる() throws Exception {
        // 抽出計画の作成
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<values selector=\"h1 .formattest\" attr=\"href\" />");
        Element plan = XmlParser.parseFromString(planStr.toString());

        List<String> values = ValuesParser.parse(plan, this.Container, this.Page.getUrl().toString());

        assertEquals("http://example.com/test.html", values.get(0));

        // 抽出計画の作成
        planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<values selector=\"h1 .formattest2\" attr=\"src\" />");
        plan = XmlParser.parseFromString(planStr.toString());

        values = ValuesParser.parse(plan, this.Container, this.Page.getUrl().toString());

        assertEquals("http://example.com/test.jpg", values.get(0));
    }
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void values以外の要素は禁止() throws Exception {
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<elements selector=\".entry\" attr=\"text\" />");
        Element plan = XmlParser.parseFromString(planStr.toString());


        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Node名が不正です。 -> " + plan.getNodeName());
        ValuesParser.parse(plan, this.Container, this.Page.getUrl().toString());
    }

    @Test
    public void 子要素を持たない() throws Exception {
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<values selector=\".entry\" attr=\"text\">");
        planStr.append("  <values selector=\"a\" attr=\"text\" />");
        planStr.append("</values>");
        Element plan = XmlParser.parseFromString(planStr.toString());


        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("子要素を保持してはいけません。");
        ValuesParser.parse(plan, this.Container, this.Page.getUrl().toString());
    }

    @Test
    public void selectorが必須() throws Exception {
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<values attr=\"text\" />");
        Element plan = XmlParser.parseFromString(planStr.toString());


        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("必須のアトリビュートキーが存在しません。 -> " + plan.getAttributes());
        ValuesParser.parse(plan, this.Container, this.Page.getUrl().toString());
    }

    @Test
    public void attrが必須() throws Exception {
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<values selector=\"a\" />");
        Element plan = XmlParser.parseFromString(planStr.toString());


        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("必須のアトリビュートキーが存在しません。 -> " + plan.getAttributes());
        ValuesParser.parse(plan, this.Container, this.Page.getUrl().toString());
    }
}