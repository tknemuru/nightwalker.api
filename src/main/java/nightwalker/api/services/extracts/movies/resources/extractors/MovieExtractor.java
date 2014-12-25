package nightwalker.api.services.extracts.movies.resources.extractors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by takashi on 2014/12/25.
 */
public final class MovieExtractor {
    public Element extract() throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse("./src/main/java/nightwalker/api/services/extracts/movies/resources/j-xvideos-resource.xml");

        Element root = document.getDocumentElement();
        return root;
    }

}
