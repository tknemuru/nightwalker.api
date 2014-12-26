package nightwalker.api.services.parses.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

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
    public static final Element parseFromFile(String filePath) throws SAXException, IOException, ParserConfigurationException {
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
    public static final Element parseFromString(String xml) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource source = new InputSource(new StringReader(xml));
        Document document = builder.parse(source);
        return document.getDocumentElement();
    }
}
