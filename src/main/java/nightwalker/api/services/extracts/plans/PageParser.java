package nightwalker.api.services.extracts.plans;

import nightwalker.api.models.resources.PageResource;
import org.w3c.dom.Element;

import java.io.IOException;

/**
 * ページを提供します。*
 * Created by takashi on 2014/12/25.
 */
public class PageParser {
    /**
     * Elementsを取得します。*
     * @param planElement 抽出計画の要素
     * @return Elements
     */
    public static final PageResource get(Element planElement) throws IOException {
        // pageノードのみ対応
        PlanValidator.nodeNameValidationThrowsException(planElement, "page");

        // url、htmlを許可
        PlanValidator.attrKeyValidationThrowsException(planElement, "url", "html");

        // urlが必須
        PlanValidator.requiredAttrKeysValidationThrowsException(planElement, "url");

        // 子ノードはelementsのみ許可
        PlanValidator.requiredChildNodesValidationThrowsException(planElement, "elements");
        PlanValidator.childNodesNameValidationThrowsException(planElement, "elements");

        // ページを生成
        return new PageResource(planElement.getAttribute("url"), planElement.getAttribute("html"));
    }
}
