package nightwalker.api.services.extracts.html;

import nightwalker.api.services.extracts.ExtractedList;
import nightwalker.api.services.extracts.ResourceExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.stream.Stream;

/**
 * 指定したHTMLタグのアトリビュート値を抽出する機能を提供します。
 * Created by takashi on 2014/12/15.
 */
public final class TagAttrValueExtractor extends ResourceExtractor {
    /**
     * 抽出対象のタグ名
     */
    private String TagName;

    /**
     * 抽出対象のアトリビュートキー
     */
    private String AttrKey;

    /**
     * コンストラクタ
     * @param tagName 抽出対象のタグ名
     * @param attrKey 抽出対象のアトリビュートキー
     */
    public TagAttrValueExtractor(String tagName, String attrKey) {
        this.TagName = tagName;
        this.AttrKey = attrKey;
    }

    /**
     * HTMLから指定したタグのアトリビュート値を抽出します。
     * @param resource リソース
     * @return 指定したタグのアトリビュート値のリスト
     */
    @Override
    public ExtractedList extract(String resource) {
        // htmlからドキュメントを生成
        Document doc = Jsoup.parse(resource);

        // 指定したタグのアトリビュート値を取得
        // 値が空の場合は取得対象外
        Stream<String> values = doc.select(this.TagName).stream()
                .filter(tag -> !tag.attr(this.AttrKey).isEmpty())
                .map(tag -> tag.attr(this.AttrKey));

        return new ExtractedList(values);
    }

    /**
     * リソースから条件に合致する情報を抽出します。
     * @param resource リソース
     * @return 条件に合致する情報
     */
    @Override
    protected String _extract(String resource) {
        // TODO:後でちゃんと実装
        return "hoge";
    }
}
