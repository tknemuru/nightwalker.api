package nightwalker.api.models.resources;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageResourceTests {

    /**
     * HTMLが正常に読み込めることをテストします。
     * TODO:環境に左右されないテストにしたい…
     *      このクラスだけはしようがない気もするけど、どうだろう。
     * @throws Exception
     */
    @Test
    public void HTMLが正常に読み込める() throws Exception {
        String url = "http://example.com/";
        PageResource resource = new PageResource(url);
        String actual = resource.getHtml();
        assertTrue(actual.contains("<body>"));
    }
}