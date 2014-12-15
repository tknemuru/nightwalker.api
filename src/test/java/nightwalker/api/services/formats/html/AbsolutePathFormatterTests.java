package nightwalker.api.services.formats.html;

import org.junit.Test;

import static org.junit.Assert.*;

public class AbsolutePathFormatterTests {

    /**
     * 相対パスが絶対パスに整形されることをテストします。
     * @throws Exception
     */
    @Test
    public void 相対パスが絶対パスに整形される() throws Exception {
        AbsolutePathFormatter formatter = new AbsolutePathFormatter("http://example.com/index.html");
        String expected = "http://example.com/test.jpg";
        String actual = formatter.format("./test.jpg");

        assertEquals(expected, actual);
    }

    /**
     * 末尾がスラッシュでなくても絶対パスに整形されることをテストします。
     * @throws Exception
     */
    @Test
    public void 末尾がスラッシュでなくても絶対パスに整形される() throws Exception {
        AbsolutePathFormatter formatter = new AbsolutePathFormatter("http://example.com");
        String expected = "http://example.com/test.jpg";
        String actual = formatter.format("./test.jpg");

        assertEquals(expected, actual);
    }

    /**
     * .(ドット)無しの相対パスが絶対パスに整形されることをテストします。
     * @throws Exception
     */
    @Test
    public void ドット無しの相対パスが絶対パスに整形される() throws Exception {
        AbsolutePathFormatter formatter = new AbsolutePathFormatter("http://example.com/");
        String expected = "http://example.com/test.jpg";
        String actual = formatter.format("/test.jpg");

        assertEquals(expected, actual);
    }

    /**
     * ファイルパスのみの場合も絶対パスに整形されることをテストします。
     * @throws Exception
     */
    @Test
    public void ファイルパスのみの場合も絶対パスに整形される() throws Exception {
        AbsolutePathFormatter formatter = new AbsolutePathFormatter("http://example.com/");
        String expected = "http://example.com/test.jpg";
        String actual = formatter.format("test.jpg");

        assertEquals(expected, actual);
    }

    /**
     * スキーム省略の場合も絶対パスに整形されることをテストします。
     * @throws Exception
     */
    @Test
    public void スキーム省略の場合も絶対パスに整形される() throws Exception {
        AbsolutePathFormatter formatter = new AbsolutePathFormatter("http://example.com/");
        String expected = "http://example.com/test.jpg";
        String actual = formatter.format("//example.com/test.jpg");

        assertEquals(expected, actual);
    }

    /**
     * 絶対パスの場合は変わらないことをテストします。
     * @throws Exception
     */
    @Test
    public void 絶対パスの場合は変わらない() throws Exception {
        AbsolutePathFormatter formatter = new AbsolutePathFormatter("http://example.com/");
        String expected = "http://example.com/test.jpg";
        String actual = formatter.format("http://example.com/test.jpg");
        assertEquals(expected, actual);

        expected = "http://example.com/sub/test.jpg";
        actual = formatter.format("http://example.com/sub/test.jpg");
        assertEquals(expected, actual);
    }

    /**
     * 絶対パスの場合はオリジンが異なっていても変わらないことをテストします。
     * @throws Exception
     */
    @Test
    public void 絶対パスの場合はオリジンが異なっていても変わらない() throws Exception {
        AbsolutePathFormatter formatter = new AbsolutePathFormatter("http://example.com/");
        String expected = "https://example.com/test.jpg";
        String actual = formatter.format("https://example.com/test.jpg");
        assertEquals(expected, actual);

        expected = "http://foo.com/sub/test.jpg";
        actual = formatter.format("http://foo.com/sub/test.jpg");
        assertEquals(expected, actual);
    }
}