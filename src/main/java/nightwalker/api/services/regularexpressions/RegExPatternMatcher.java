package nightwalker.api.services.regularexpressions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正規表現のパターンマッチに関する一連の機能を集約して提供します。
 * Created by takashi on 2014/12/14.
 */
public final class RegExPatternMatcher {
    /**
     * 正規表現のパターン
     */
    private Pattern Pattern;

    /**
     * コンストラクタ
     * @param pattern 正規表現の文字列
     */
    public RegExPatternMatcher(String regex) {
        this.Pattern = Pattern.compile(regex);
    }

    /**
     * 正規表現にマッチした全ての文字列をリスト形式で返します。
     * @param target 検証対象の文字列
     * @return 正規表現にマッチした全ての文字列
     */
    public List<String> findAll(String target) {
        Matcher matcher = this.Pattern.matcher(target);
        List<String> matchedGroups = new ArrayList<>();
        while (matcher.find()) {
            matchedGroups.add(matcher.group());
        }
        return matchedGroups;
    }
}
