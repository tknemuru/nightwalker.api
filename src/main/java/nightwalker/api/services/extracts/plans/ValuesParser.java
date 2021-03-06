package nightwalker.api.services.extracts.plans;

import nightwalker.api.services.formats.AbsolutePathFormatter;
import nightwalker.api.services.parses.xml.XmlParser;
import org.w3c.dom.Element;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * value要素の解析機能を提供します。*
 * Created by takashi on 2014/12/26.
 */
public final class ValuesParser {
    /**
     * valueを解析します。*
     * @param planElement 抽出計画
     * @param container 抽出対象のHTMLコンテナ
     * @return value
     */
    public static final List<String> parse(final Element planElement, final org.jsoup.nodes.Element container, final String pageUrl) throws IOException, URISyntaxException{
        // valuesを許可
        PlanValidator.nodeNameValidationThrowsException(planElement, "values");

        // 子要素を持たない
        Element planElementRemovedNewLineChild = XmlParser.removeNewLineChildNodes(planElement);
        if(planElementRemovedNewLineChild.getChildNodes().getLength() != 0) {
            throw new IllegalArgumentException("子要素を保持してはいけません。");
        }

        // selector、attrが必須
        PlanValidator.requiredAttrKeysValidationThrowsException(planElement, "selector", "attr");

        // 要素を取得
        String selector = planElement.getAttribute("selector");
        String attr = planElement.getAttribute("attr");
        List<String> values = new ArrayList<>();
        if(attr.equals("text")) {
            container.select(selector).forEach(el ->
                    values.add(el.text()));
        }
        else {
            AbsolutePathFormatter formatter = new AbsolutePathFormatter(pageUrl);
            container.select(selector).forEach(el ->
            {
                String value = el.attr(attr);
                if(isNeedUrlFormat(attr)) {
                    value = formatter.format(value);
                }
                values.add(value);
            });
        }
        
        return values;
    }

    /**
     * アトリビュートキーからURLフォーマットが必要かどうかを判定します。*
     * @param attr アトリビュートキー
     * @return URLフォーマットが必要かどうか
     */
    private static boolean isNeedUrlFormat(String attr) {
        return (attr.equals("href") || attr.equals("src"));
    }
}
