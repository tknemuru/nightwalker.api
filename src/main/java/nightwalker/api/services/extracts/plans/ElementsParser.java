package nightwalker.api.services.extracts.plans;

import nightwalker.api.models.resources.PageResource;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;

import java.io.IOException;

/**
 * Elementsを提供します。*
 * Created by takashi on 2014/12/25.
 */
public final class ElementsParser {
    /**
     * Elementsを取得します。*
     * @param planElement 抽出計画の要素
     * @param page ページ
     * @return Elements
     */
    public static Elements get(Element planElement, PageResource page) throws IOException {
        // elementsノードのみ対応
        PlanValidator.nodeNameValidationThrowsException(planElement, "elements");

        // selectorが必須
        PlanValidator.requiredAttrKeysValidationThrowsException(planElement, "selector");

        // 子ノードはfilterをオプションで許可
        PlanValidator.childNodesNameValidationThrowsException(planElement, "filter");

        // セレクタを取得
        String selector = planElement.getAttribute("selector");

        // htmlからドキュメントを生成
        org.jsoup.nodes.Document doc = Jsoup.parse(page.getHtml());

        // 要素を取得
        Elements elements = doc.select(selector);

        // filterが指定されている場合はfilterした要素を返す
        if(PlanValidator.requiredChildNodesValidation(planElement, "filter")){
            return FilterParser.filter((Element) planElement.getElementsByTagName("filter").item(0), elements);
        }
        else {
            return elements;
        }
    }
}
