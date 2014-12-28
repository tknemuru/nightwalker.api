package nightwalker.api.services.extracts.plans;

import org.jsoup.select.Elements;
import org.w3c.dom.Element;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 抽出計画のフィルタ要素を解析し、HTML要素のフィルタ機能を提供します。*
 * Created by takashi on 2014/12/25.
 */
public final class FilterParser {
    /**
     * フィルタを実行します。*
     * @param planElement 抽出計画の要素
     * @param htmlElements HTML要素
     * @return フィルタされたHTML要素
     */
    public static final Elements filter(final Element planElement, final Elements htmlElements) {
        // filterノードのみ許可
        PlanValidator.nodeNameValidationThrowsException(planElement, "filter");

        // attrが必須、equals/notequalsのいずれかが必須
        PlanValidator.requiredAttrKeysValidationThrowsException(planElement, "attr");
        boolean hasEquals = PlanValidator.requiredAttrKeysValidation(planElement, "equals");
        boolean hasNotEquals = PlanValidator.requiredAttrKeysValidation(planElement, "notequals");
        if((!hasEquals) && (!hasNotEquals)) {
            throw new IllegalArgumentException("equals/notequals のいずれかが必須です。 -> " + planElement.getAttributes());
        }

        // selectorがオプション
        PlanValidator.nodeNameValidation(planElement, "attr", "equals", "notequals", "selector");
        
        // アトリビュート値を取得
        String selector = planElement.getAttribute("selector");
        String attr = planElement.getAttribute("attr");
        String equals = planElement.getAttribute("equals");
        String notEquals = planElement.getAttribute("notequals");

        // フィルターを実行
        List<org.jsoup.nodes.Element> filteredHtmlElements;
        if(hasEquals){
            filteredHtmlElements = htmlElements.stream()
                    .filter(el -> ((!attr.equals("text")) && el.select(selector).get(0).attr(attr).equals(equals))
                            || (attr.equals("text") && el.select(selector).get(0).text().equals(equals)))
                    .collect(Collectors.toList());   
        }
        else {
            filteredHtmlElements = htmlElements.stream()
                    .filter(el -> (attr.equals("text") && (!el.select(selector).get(0).attr(attr).equals(notEquals)))
                            || (attr.equals("text") && (!el.select(selector).get(0).text().equals(notEquals))))
                    .collect(Collectors.toList());
        }

        return new Elements(filteredHtmlElements);
    }
}
