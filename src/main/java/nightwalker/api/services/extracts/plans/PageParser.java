package nightwalker.api.services.extracts.plans;

import nightwalker.api.models.resources.PageResource;
import nightwalker.api.services.formats.AbsolutePathFormatter;
import org.w3c.dom.Element;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * ページ要素の解析機能を提供します。*
 * Created by takashi on 2014/12/25.
 */
public class PageParser {
    /**
     * Elementsを取得します。*
     * @param planElement 抽出計画の要素
     * @return Elements
     */
    public static final PageResource parse(final Element planElement) throws IOException {
        // 抽出計画の妥当性チェック
        CommonValidation(planElement);

        // url、htmlを許可
        PlanValidator.attrKeyValidationThrowsException(planElement, "url", "html");

        // urlが必須
        PlanValidator.requiredAttrKeysValidationThrowsException(planElement, "url");

        // ページを生成
        return new PageResource(planElement.getAttribute("url"), planElement.getAttribute("html"));
    }

    /**
     * Elementsを取得します。*
     * @param planElement 抽出計画の要素
     * @param container コンテナ
     * @return Elements
     * @throws IOException
     */
    public static final PageResource parse(final Element planElement, final org.jsoup.nodes.Element container, final String pageUrl) throws IOException, URISyntaxException {
        // 抽出計画の妥当性チェック
        CommonValidation(planElement);

        // selector、attrが必須。enabledJavascriptがオプション
        PlanValidator.attrKeyValidationThrowsException(planElement, "selector", "attr", "enabledJavascript");
        PlanValidator.requiredAttrKeysValidationThrowsException(planElement, "selector", "attr");

        // ページのURLを取得する
        String selector = planElement.getAttribute("selector");
        String attr = planElement.getAttribute("attr");
        
        // URLを取得する対象の要素数は必ず1
        if(container.select(selector).size() != 1) {
            throw new IllegalArgumentException("URLを取得する対象の要素数を1にしてください。");
        }
        
        String url = container.select(selector).attr(attr);
        
        // URLを正規化する
        url = new AbsolutePathFormatter(pageUrl).format(url);
        
        // ページを生成
        return new PageResource(url);
    }

    /**
     * ページ共通の妥当性チェックを行います。*
     * @param planElement 抽出計画が妥当かどうか
     */
    private static void CommonValidation(final Element planElement) {
        // pageノードのみ対応
        PlanValidator.nodeNameValidationThrowsException(planElement, "page");

        // 子ノードはelements、values、pageを許可
        PlanValidator.childNodesNameValidationThrowsException(planElement, "elements", "values", "page");
    }
}
