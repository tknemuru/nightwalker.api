package nightwalker.api.services.regularexpressions;

/**
 * 正規表現のパターンを提供します。
 * Created by takashi on 2014/12/15.
 */
public final class RegExPattern {
    /**
     * パスからファイル名を抜き出す正規表現
     */
    public static final String HttpFilePath = "(?!.*/).+";
}
