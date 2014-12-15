package nightwalker.api.services.regularexpressions;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RegExPatternMatcherTests {

    /**
     * 正規表現にマッチした全ての文字列が取得できることをテストします。
     * @throws Exception
     */
    @Test
    public void 正規表現にマッチした全ての文字列が取得できる() throws Exception {
        // 例文は下記から引用
        // http://www.javadrive.jp/regex/ini/index6.html
        String target = "Tomato is 100yen, Lemon is 80yen.";
        RegExPatternMatcher matcher = new RegExPatternMatcher("[0-9]+yen");
        List<String> groups = matcher.findAll(target);

        // "100yen"と"80yen"の二つがマッチしている
        assertEquals(2, groups.size());
        assertEquals("100yen", groups.get(0));
        assertEquals("80yen", groups.get(1));
    }
}