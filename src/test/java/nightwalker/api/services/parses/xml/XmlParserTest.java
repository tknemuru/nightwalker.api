package nightwalker.api.services.parses.xml;

import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static org.junit.Assert.*;

public class XmlParserTest {

    @Test
    public void 改行エレメントを除いた子エレメントが取得できる() throws Exception {
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<elements selector=\".entry\">");
        planStr.append("    <filter selector=\".info .category a\" attr=\"text\" equals=\"FC2動画\" />");
        planStr.append("</elements>");
        Element el = XmlParser.parseFromString(planStr.toString());

        Node childEl = XmlParser.removeNewLineChildNodes(el).getChildNodes().item(0);
        assertEquals(childEl.getNodeName(), "filter");
        
        Node textEl = el.getChildNodes().item(0);
        assertEquals(textEl.getNodeName(), "#text");
    }

    @Test
    public void 通常のテキストは除かれない() throws Exception {
        StringBuilder planStr = new StringBuilder();
        planStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        planStr.append("<elements selector=\".entry\">hogehoge</elements>");
        Element el = XmlParser.parseFromString(planStr.toString());

        Node childEl = XmlParser.removeNewLineChildNodes(el).getChildNodes().item(0);
        assertEquals(childEl.getTextContent(), "hogehoge");

        Node textEl = el.getChildNodes().item(0);
        assertEquals(textEl.getTextContent(), "hogehoge");
    }
}