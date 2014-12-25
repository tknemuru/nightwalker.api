package nightwalker.api.services.extracts.plans;

import nightwalker.api.models.resources.PageResource;
import org.w3c.dom.Element;

import java.io.IOException;

/**
 * ページを提供します。*
 * Created by takashi on 2014/12/25.
 */
public class PageProvider {
    /**
     * Elementsを取得します。*
     * @param planElement 抽出計画の要素
     * @return Elements
     */
    public static final PageResource get(Element planElement) throws IOException {
        // pageノードのみ対応
        XmlElementValidator.nodeNameValidationThrowsException(planElement, "page");

        // url、htmlを許可
        XmlElementValidator.attrKeyValidationThrowsException(planElement, "url", "html");

        // urlが必須
        XmlElementValidator.requiredAttrKeysValidationThrowsException(planElement, "url");

        // 子ノードはelementsのみ許可
        XmlElementValidator.requiredChildNodesValidationThrowsException(planElement, "elements");
        XmlElementValidator.childNodesNameValidationThrowsException(planElement, "elements");

        // ページを生成
        return new PageResource(planElement.getAttribute("url"), planElement.getAttribute("html"));
    }
}
