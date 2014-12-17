package nightwalker.api.services.helper;

import nightwalker.api.helper.UrlHelper;
import org.junit.Test;

import static org.junit.Assert.*;

public class UrlHelperTest {

    /**
     * ファイルパスであると正常に判定できることをテストします。
     * @throws Exception
     */
    @Test
    public void ファイルパスであると正常に判定できる() throws Exception {
        String url = "http://example.com/index.html";
        boolean actual = UrlHelper.isFilePath(url);
        assertEquals(true, actual);
    }

    /**
     * 複数階層であってもファイルパスであると正常に判定できることをテストします。
     * @throws Exception
     */
    @Test
    public void 複数階層であってもファイルパスであると正常に判定できる() throws Exception {
        String url = "http://example.com/foo/bar/fuga/index.html";
        boolean actual = UrlHelper.isFilePath(url);
        assertEquals(true, actual);
    }

    /**
     * クエリパラメータ付きであってもファイルパスであると正常に判定できることをテストします。
     * @throws Exception
     */
    @Test
    public void クエリパラメータ付きであってもファイルパスであると正常に判定できる() throws Exception {
        String url = "http://example.com/index.html?key=value&key2=value2";
        boolean actual = UrlHelper.isFilePath(url);
        assertEquals(true, actual);
    }

    /**
     * ディレクトリパスであると正常に判定できることをテストします。
     * @throws Exception
     */
    @Test
    public void ディレクトリパスであると正常に判定できる() throws Exception {
        String url = "http://example.com/";
        boolean actual = UrlHelper.isFilePath(url);
        assertEquals(false, actual);
    }

    /**
     * 末尾がスラッシュでなくてもディレクトリパスであると正常に判定できることをテストします。
     * @throws Exception
     */
    @Test
    public void 末尾がスラッシュでなくてもディレクトリパスであると正常に判定できる() throws Exception {
        String url = "http://example.com";
        boolean actual = UrlHelper.isFilePath(url);
        assertEquals(false, actual);
    }

    /**
     * 複数階層であってもディレクトリパスであると正常に判定できることをテストします。
     * @throws Exception
     */
    @Test
    public void 複数階層であってもディレクトリパスであると正常に判定できる() throws Exception {
        String url = "http://example.com/foo/bar/fuga/";
        boolean actual = UrlHelper.isFilePath(url);
        assertEquals(false, actual);
    }

    /**
     * クエリパラメータ付きであってもディレクトリパスであると正常に判定できることをテストします。
     * @throws Exception
     */
    @Test
    public void クエリパラメータ付きであってもディレクトリパスであると正常に判定できる() throws Exception {
        String url = "http://example.com/?key=value&key2=value2";
        boolean actual = UrlHelper.isFilePath(url);
        assertEquals(false, actual);
    }

    /**
     * 末尾がスラッシュでなくてクエリパラメータ付きであってもディレクトリパスであると正常に判定できることをテストします。
     * @throws Exception
     */
    @Test
    public void 末尾がスラッシュでなくてクエリパラメータ付きであってもディレクトリパスであると正常に判定できる() throws Exception {
        String url = "http://example.com?key=value&key2=value2";
        boolean actual = UrlHelper.isFilePath(url);
        assertEquals(false, actual);
    }

    /**
     * 相対パスでもファイルパスを正常に判定できることをテストします。
     * @throws Exception
     */
    @Test
    public void 相対パスでもファイルパスを正常に判定できる() throws Exception {
        String url = "../image.jpg";
        boolean actual = UrlHelper.isFilePath(url);
        assertEquals(true, actual);

        url = "./image.jpg";
        actual = UrlHelper.isFilePath(url);
        assertEquals(true, actual);

        url = "/image.jpg";
        actual = UrlHelper.isFilePath(url);
        assertEquals(true, actual);

        url = "image.jpg";
        actual = UrlHelper.isFilePath(url);
        assertEquals(true, actual);
    }

    /**
     * 相対パスでもディレクトリパスを正常に判定できることをテストします。
     * @throws Exception
     */
    @Test
    public void 相対パスでもディレクトリパスを正常に判定できる() throws Exception {
        String url = "../images/";
        boolean actual = UrlHelper.isFilePath(url);
        assertEquals(false, actual);

        url = "./images/";
        actual = UrlHelper.isFilePath(url);
        assertEquals(false, actual);

        url = "/images/";
        actual = UrlHelper.isFilePath(url);
        assertEquals(false, actual);

        url = "images/";
        actual = UrlHelper.isFilePath(url);
        assertEquals(false, actual);
    }
}