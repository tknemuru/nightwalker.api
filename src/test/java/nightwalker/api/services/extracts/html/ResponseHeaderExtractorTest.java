package nightwalker.api.services.extracts.html;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class ResponseHeaderExtractorTest {

    /**
     * HTTP レスポンスヘッダの値による抽出が正常に行われることをテストします。
     * @throws Exception
     */
    @Test
    public void HTTPレスポンスヘッダの値による抽出が正常に行われる() throws Exception {
        Predicate<String> greaterThanCompareValue = (headerValue) -> Integer.parseInt(headerValue) >= 3000;
        ResponseHeaderExtractor extractor = new ResponseHeaderExtractor("Content-Length", greaterThanCompareValue);
        ExtractedList list = new ExtractedList("http://www.28lab.com/Content/Image/FB-f-Logo__blue_29.png");
        list.add("http://www.28lab.com/Content/Image/tvasahi.jpg");

        List<String> expected = new ArrayList<>();
        expected.add("http://www.28lab.com/Content/Image/tvasahi.jpg");

        ExtractedList actual = extractor.extract(list);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}