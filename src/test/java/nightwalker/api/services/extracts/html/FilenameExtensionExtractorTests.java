package nightwalker.api.services.extracts.html;

import nightwalker.api.services.extracts.ExtractedList;
import org.junit.Test;

import static org.junit.Assert.*;

public class FilenameExtensionExtractorTests {

    /**
     * 指定した拡張子のファイルパスのみが抽出されることをテストします。
     * @throws Exception
     */
    @Test
    public void 指定した拡張子のファイルパスのみが抽出される() throws Exception {
        FilenameExtensionExtractor extractor = new FilenameExtensionExtractor("jpg", "png");
        ExtractedList list = new ExtractedList("http://example.com/images/test.jpg");
        list.add("http://example.com/images/test.gif");
        list.add("http://example.com/test.html");
        list.add("http://example.com/images/test.png");

        ExtractedList expected = new ExtractedList("http://example.com/images/test.jpg");
        expected.add("http://example.com/images/test.png");

        ExtractedList actual = extractor.extract(list);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    /**
     * ディレクトリパスの場合も正常に処理が行われることをテストします。
     * @throws Exception
     */
    @Test
    public void ディレクトリパスの場合も正常に処理が行われる() throws Exception {
        FilenameExtensionExtractor extractor = new FilenameExtensionExtractor("jpg", "png");
        ExtractedList list = new ExtractedList("http://example/images/");

        ExtractedList expected = new ExtractedList();

        ExtractedList actual = extractor.extract(list);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}