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
     * ディレクトリパスの場合も例外が発生せず正常に処理が行われることをテストします。
     * @throws Exception
     */
    @Test
    public void ディレクトリパスの場合も例外が発生せず正常に処理が行われる() throws Exception {
        FilenameExtensionExtractor extractor = new FilenameExtensionExtractor("jpg", "png");
        ExtractedList list = new ExtractedList("http://example/images/");

        ExtractedList expected = new ExtractedList();

        ExtractedList actual = extractor.extract(list);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    /**
     * 抽出対象の拡張子が未指定の場合はディレクトリパスが抽出されることをテストします。
     * @throws Exception
     */
    @Test
    public void 抽出対象の拡張子が未指定の場合はディレクトリパスが抽出される() throws Exception {
        FilenameExtensionExtractor extractor = new FilenameExtensionExtractor();
        ExtractedList list = new ExtractedList("http://example.com/images/");
        list.add("http://example.com/test.jpg");

        ExtractedList expected = new ExtractedList("http://example.com/images/");

        ExtractedList actual = extractor.extract(list);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    /**
     * 抽出対象の拡張子が指定されていても全て空文字の場合はディレクトリパスが抽出されることをテストします。
     * @throws Exception
     */
    @Test
    public void 抽出対象の拡張子が指定されていても全て空文字の場合はディレクトリパスが抽出される() throws Exception {
        FilenameExtensionExtractor extractor = new FilenameExtensionExtractor("", "", "");
        ExtractedList list = new ExtractedList("http://example.com/images/");
        list.add("http://example.com/test.jpg");

        ExtractedList expected = new ExtractedList("http://example.com/images/");

        ExtractedList actual = extractor.extract(list);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    /**
     * 抽出対象の拡張子に空文字が含まれていても正常に抽出されることをテストします。
     * @throws Exception
     */
    @Test
    public void 抽出対象の拡張子に空文字が含まれていても正常に抽出される() throws Exception {
        FilenameExtensionExtractor extractor = new FilenameExtensionExtractor("", "jpg", "");
        ExtractedList list = new ExtractedList("http://example.com/images/");
        list.add("http://example.com/test.jpg");

        ExtractedList expected = new ExtractedList("http://example.com/test.jpg");

        ExtractedList actual = extractor.extract(list);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    /**
     * 末尾にスラッシュが付いていなくてもディレクトリパスが正常に抽出されることをテストします。
     * @throws Exception
     */
    @Test
    public void 末尾にスラッシュが付いていなくてもディレクトリパスが正常に抽出される() throws Exception {
        FilenameExtensionExtractor extractor = new FilenameExtensionExtractor();
        ExtractedList list = new ExtractedList("http://example.com");
        list.add("http://example.com/test.jpg");

        ExtractedList expected = new ExtractedList("http://example.com");

        ExtractedList actual = extractor.extract(list);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    /**
     * クエリパラメータがついていてもファイルパスが正常に抽出されることをテストします。
     * @throws Exception
     */
    @Test
    public void クエリパラメータがついていてもファイルパスが正常に抽出される() throws Exception {
        FilenameExtensionExtractor extractor = new FilenameExtensionExtractor("jpg");
        ExtractedList list = new ExtractedList("http://example.com/test.jpg?key=value&key2=value2");

        ExtractedList expected = new ExtractedList("http://example.com/test.jpg?key=value&key2=value2");

        ExtractedList actual = extractor.extract(list);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    /**
     * 末尾のスラッシュ無しでクエリパラメータがついていても正常に抽出されることをテストします。
     * @throws Exception
     */
    @Test
    public void 末尾のスラッシュ無しでクエリパラメータがついていても正常に抽出される() throws Exception {
        FilenameExtensionExtractor extractor = new FilenameExtensionExtractor();
        ExtractedList list = new ExtractedList("http://example.com?key=value&key2=value2");

        ExtractedList expected = new ExtractedList("http://example.com?key=value&key2=value2");

        ExtractedList actual = extractor.extract(list);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}