package nightwalker.api.services.extracts.plans;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 抽出計画XMLエレメントの妥当性判定機能を提供します。*
 * Created by takashi on 2014/12/25.
 */
public final class PlanValidator {
    /**
     * 子ノードの必須検証を行います。*
     * @param planElement 抽出計画の要素
     * @param requiredChildNodesNames 必須の子ノード
     * @return 必須の子ノードを全て保持しているかどうか
     */
    public static final boolean requiredChildNodesValidation(Element planElement, String... requiredChildNodesNames) {
        List<String> childNodesNameList = new ArrayList<>();
        for(int i = 0; i < planElement.getChildNodes().getLength(); i++) {
            childNodesNameList.add(planElement.getChildNodes().item(i).getNodeName());
        }
        List<String> requiredNodeNameList = Arrays.asList(requiredChildNodesNames);
        return requiredNodeNameList.stream()
                .allMatch(req -> childNodesNameList.contains(req));
    }

    /**
     * 子ノードの必須検証を行います。*
     * 妥当でない場合は例外を投げます。*
     * @param planElement 抽出計画の要素
     * @param requiredChildNodesNames 必須の子ノード
     */
    public static final void requiredChildNodesValidationThrowsException(Element planElement, String... requiredChildNodesNames) {
        if(!PlanValidator.requiredChildNodesValidation(planElement, requiredChildNodesNames)){
            throw new IllegalArgumentException("必須の子ノードが存在しません。 -> " + planElement.getChildNodes());
        }
    }

    /**
     * 子ノードのノード名検証を行います。*
     * @param planElement 抽出計画の要素
     * @param validNames 妥当な子ノード名
     * @return 子ノード名が全て妥当かどうか
     */
    public static final boolean childNodesNameValidation(Element planElement, String... validNames) {
        List<String> childNodesNameList = new ArrayList<>();
        for(int i = 0; i < planElement.getChildNodes().getLength(); i++) {
            childNodesNameList.add(planElement.getChildNodes().item(i).getNodeName());
        }
        List<String> validNameList = Arrays.asList(validNames);
        return childNodesNameList.stream()
                .allMatch(name -> validNameList.contains(name));
    }

    /**
     * 子ノードのノード名検証を行います。*
     * 妥当でない場合は例外を投げます。*
     * @param planElement 抽出計画の要素
     * @param validNames 妥当な子ノード名
     */
    public static final void childNodesNameValidationThrowsException(Element planElement, String... validNames) {
        if(!PlanValidator.childNodesNameValidation(planElement, validNames)){
            throw new IllegalArgumentException("子ノードのノード名が不正です。 -> " + planElement.getChildNodes());
        }
    }
    
    /**
     * Node名の妥当性を検証します。*
     * @param planElement 抽出計画の要素
     * @param validNames 妥当なNode名
     * @return Node名が妥当かどうか
     */
    public static final boolean nodeNameValidation(Element planElement, String... validNames) {
        return Arrays.asList(validNames).contains(planElement.getNodeName());
    }

    /**
     * Node名の妥当性を検証します。*
     * 妥当でない場合は例外を投げます。*
     * @param planElement 抽出計画の要素
     * @param validNames 妥当なNode名
     */
    public static final void nodeNameValidationThrowsException(Element planElement, String... validNames) {
        if(!PlanValidator.nodeNameValidation(planElement, validNames)){
            throw new IllegalArgumentException("Node名が不正です。 -> " + planElement.getNodeName());
        }
    }

    /**
     * アトリビュートキーの妥当性を検証します。*
     * @param planElement 抽出計画の要素
     * @param validKeys 妥当なアトリビュートキー
     * @return アトリビュートキーが妥当かどうか
     */
    public static final boolean attrKeyValidation(Element planElement, String... validKeys) {
        List<String> validKeyList = Arrays.asList(validKeys);
        for(int i = 0; i < planElement.getAttributes().getLength(); i++) {
            if(!validKeyList.contains(planElement.getAttributes().item(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * アトリビュートキーの妥当性を検証します。*
     * 妥当でない場合は例外を投げます。*
     * @param planElement 抽出計画の要素
     * @param validKeys 妥当なアトリビュートキー
     */
    public static final void attrKeyValidationThrowsException(Element planElement, String... validKeys) {
        if(!PlanValidator.attrKeyValidation(planElement, validKeys)){
            throw new IllegalArgumentException("アトリビュートキーが不正です。 -> " + planElement.getAttributes());
        }
    }

    /**
     * アトリビュートキーの必須検証を行います。*
     * @param planElement 抽出計画の要素
     * @param requiredAttrKeys 必須のアトリビュートキー
     * @return 必須のアトリビュートキーを全て保持しているかどうか
     */
    public static final boolean requiredAttrKeysValidation(Element planElement, String... requiredAttrKeys) {
        List<String> attrKeysList = new ArrayList<>();
        for(int i = 0; i < planElement.getAttributes().getLength(); i++) {
            attrKeysList.add(planElement.getAttributes().item(i).getNodeName());
        }
        List<String> requiredNodeNameList = Arrays.asList(requiredAttrKeys);
        return requiredNodeNameList.stream()
                .allMatch(req -> attrKeysList.contains(req));
    }

    /**
     * アトリビュートキーの必須検証を行います。*
     * 妥当でない場合は例外を投げます。*
     * @param planElement 抽出計画の要素
     * @param requiredAttrKeys 必須のアトリビュートキー
     */
    public static final void requiredAttrKeysValidationThrowsException(Element planElement, String... requiredAttrKeys) {
        if(!PlanValidator.requiredAttrKeysValidation(planElement, requiredAttrKeys)){
            throw new IllegalArgumentException("必須のアトリビュートキーが存在しません。 -> " + planElement.getAttributes());
        }
    }
}
