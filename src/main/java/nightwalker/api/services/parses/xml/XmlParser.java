package nightwalker.api.services.parses.xml;

import org.jsoup.helper.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * XMLへのパース機能を提供します。*
 * Created by takashi on 2014/12/25.
 */
public final class XmlParser {
    /**
     * XMLファイルからパースします。*
     * @param filePath XMLファイルパス
     * @return XML
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public static final Element parseFromFile(final String filePath) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(filePath);
        return document.getDocumentElement();
    }

    /**
     * XML文字列からパースします。*
     * @param xml XML文字列
     * @return XML
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public static final Element parseFromString(final String xml) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource source = new InputSource(new StringReader(xml));
        Document document = builder.parse(source);
        return document.getDocumentElement();
    }

    /**
     * 子エレメントの改行エレメントを削除します。*
     * @param el エレメント
     * @return 子エレメントの改行エレメントを除いたエレメント
     */
    public static final Element removeNewLineChildNodes(final Element el) {
        Element clonedEl = (Element)(el.cloneNode(true));
        List<Node> removeNodes = new ArrayList<>();
        for(int i = 0; i < clonedEl.getChildNodes().getLength(); i++) {
            Node node = clonedEl.getChildNodes().item(i);
            if(node.getNodeName().equals("#text") && StringUtil.isBlank(node.getTextContent())){
                removeNodes.add(node);
            }
        }
        
        removeNodes.forEach(removeNode -> clonedEl.removeChild(removeNode));
        
        return clonedEl;
    }
}
