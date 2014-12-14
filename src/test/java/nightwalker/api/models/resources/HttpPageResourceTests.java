package nightwalker.api.models.resources;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpPageResourceTests {

    /**
     * HTMLが正常に読み込めることをテストします。
     * TODO:環境に左右されないテストにしたい…
     *      このクラスだけはしようがない気もするけど、どうだろう。
     * @throws Exception
     */
    @Test
    public void testGetContent() throws Exception {
        String url = "http://nihachilab.herokuapp.com/";
        HttpPageResource resource = new HttpPageResource(url);
        String actual = resource.getContent();
        assertTrue(actual.contains("<body>"));
    }
}