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
        Predicate<String> greaterThanCompareValue = (headerValue) -> Integer.parseInt(headerValue) >= 7000;
        ResponseHeaderExtractor extractor = new ResponseHeaderExtractor("Content-Length", greaterThanCompareValue);
        ExtractedList list = new ExtractedList("https://28lab.com/28lab/wp-content/uploads/2018/06/logo.png");
        list.add("https://28lab.com/28lab/wp-content/themes/alamak-child/img/dash-cam-dark.png");

        List<String> expected = new ArrayList<>();
        expected.add("https://28lab.com/28lab/wp-content/themes/alamak-child/img/dash-cam-dark.png");

        ExtractedList actual = extractor.extract(list);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}