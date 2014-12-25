package nightwalker.api.services.extracts.html;

import org.junit.Test;

import static org.junit.Assert.*;

public class CssSelectorAttrValueExtractorTests {

    /**
     * 単一行の指定したCSSセクレタから、アトリビュート値が抽出できることをテストします。
     * @throws Exception
     */
    @Test
    public void 単一行の指定したCSSセクレタからアトリビュート値が抽出できる() throws Exception {
        CssSelectorAttrValueExtractor extractor = new CssSelectorAttrValueExtractor("img", "src");
        String resource = "<img class=\"thumbnail-image\" src=\"/assets/ninja.png\">";
        ExtractedList expected = new ExtractedList("/assets/ninja.png");
        ExtractedList actual = extractor.extract(resource);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    /**
     * 複数行の指定したCSSセクレタから、アトリビュート値が抽出できることをテストします。
     * @throws Exception
     */
    @Test
    public void 複数行の指定したCSSセクレタからアトリビュート値が抽出できる() throws Exception {
        CssSelectorAttrValueExtractor extractor = new CssSelectorAttrValueExtractor("img", "src");
        StringBuilder resources = new StringBuilder();
        resources.append("<img class=\"thumbnail-image\" src=\"/assets/ninja.png\">");
        resources.append("<img class=\"thumbnail-image\" src=\"./assets/ninja.png\">");
        resources.append("<img class=\"thumbnail-image\" src=\"assets/ninja.png\">");
        resources.append("<img class=\"thumbnail-image\" src=\"//assets/ninja.png\">");
        resources.append("<img class=\"thumbnail-image\" src=\"http://assets/ninja.png\">");
        resources.append("<img class=\"thumbnail-image\" src=\"https://assets/ninja.png\">");

        ExtractedList expected = new ExtractedList("/assets/ninja.png");
        expected.add("./assets/ninja.png");
        expected.add("assets/ninja.png");
        expected.add("//assets/ninja.png");
        expected.add("http://assets/ninja.png");
        expected.add("https://assets/ninja.png");

        ExtractedList actual = extractor.extract(resources.toString());
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    /**
     * 指定していないタグのアトリビュート値は、抽出対象外になることをテストします。
     * @throws Exception
     */
    @Test
    public void 指定していないタグのアトリビュート値は抽出対象外になる() throws Exception {
        CssSelectorAttrValueExtractor extractor = new CssSelectorAttrValueExtractor("img", "src");
        StringBuilder resources = new StringBuilder();
        resources.append("<iframe class=\"thumbnail-image\" src=\"/iframes/assets/ninja.html\"></iframe>");
        resources.append("<img class=\"thumbnail-image\" src=\"/images/assets/ninja.png\">");

        ExtractedList expected = new ExtractedList("/images/assets/ninja.png");
        ExtractedList actual = extractor.extract(resources.toString());

        // 抽出された値は1件
        assertEquals(1, actual.size());

        // 抽出された結果が期待通り
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    /**
     * 指定したCSSセクレタが0件でも例外が発生せず、正常に終了することをテストします。
     * @throws Exception
     */
    @Test
    public void 指定したCSSセクレタが0件でも正常に終了する() throws Exception {
        CssSelectorAttrValueExtractor extractor = new CssSelectorAttrValueExtractor("img", "src");
        StringBuilder resources = new StringBuilder();
        resources.append("<iframe class=\"thumbnail-image\" src=\"/iframes/assets/ninja.html\"></iframe>");

        ExtractedList expected = new ExtractedList();
        ExtractedList actual = extractor.extract(resources.toString());

        // 抽出された値は0件
        assertEquals(0, actual.size());

        // 抽出された結果が期待通り
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    /**
     * 指定したアトリビュートキーが0件でも例外が発生せず、正常に終了することをテストします。
     * @throws Exception
     */
    @Test
    public void 指定したアトリビュートキーが１件もないときでも正常に終了する() throws Exception {
        CssSelectorAttrValueExtractor extractor = new CssSelectorAttrValueExtractor("img", "src");
        StringBuilder resources = new StringBuilder();
        resources.append("<img class=\"thumbnail-image\">");

        ExtractedList expected = new ExtractedList();
        ExtractedList actual = extractor.extract(resources.toString());

        // 抽出された値は0件
        assertEquals(0, actual.size());

        // 抽出された結果が期待通り
        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}