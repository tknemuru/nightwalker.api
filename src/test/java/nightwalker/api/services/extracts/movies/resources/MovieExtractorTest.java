package nightwalker.api.services.extracts.movies.resources;

import junit.framework.TestCase;
import nightwalker.api.services.extracts.movies.resources.extractors.MovieExtractor;
import org.w3c.dom.Element;

public class MovieExtractorTest extends TestCase {

    public void testExtract() throws Exception {
        MovieExtractor extractor = new MovieExtractor();
        Element el = extractor.extract();
        assertTrue(el != null);
    }
}