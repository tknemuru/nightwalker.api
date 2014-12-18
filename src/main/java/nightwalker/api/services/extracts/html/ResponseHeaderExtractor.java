package nightwalker.api.services.extracts.html;

import nightwalker.api.services.extracts.ExtractedList;
import nightwalker.api.services.extracts.ResourceExtractor;

import java.net.URL;
import java.net.URLConnection;
import java.util.function.Predicate;

/**
 * HTTP レスポンスヘッダの値による抽出機能を提供します。
 * Created by takashi on 2014/12/17.
 */
public final class ResponseHeaderExtractor extends ResourceExtractor {
    /**
     * 抽出判定に使用するヘッダ値のキー
     */
    private String HeaderKey;

    /**
     * 抽出判定に使用する関数
     * ヘッダの値を引数にとり、抽出条件に合致するかどうかを判定する関数です。
     * trueが返されたリソースのみが抽出されます。
     */
    private Predicate<String> ExtractFunc;

    /**
     * コンストラクタ
     * @param headerKey 抽出判定に使用するヘッダ値のキー
     * @param extractFunc 抽出判定に使用する関数
     */
    public ResponseHeaderExtractor(String headerKey, Predicate<String> extractFunc) {
        this.HeaderKey = headerKey;
        this.ExtractFunc = extractFunc;
    }

    /**
     * HTTP レスポンスヘッダの値によるリソースの抽出を行います。
     * @param resource リソース
     * @return 抽出されたリソース
     */
    @Override
    public ExtractedList extract(String resource) {
        // TODO: 後でちゃんと実装
        return new ExtractedList();
    }

    /**
     * HTTP レスポンスヘッダの値によるリソースの抽出を行います。
     * @param resource リソース
     * @return 抽出されたリソース
     */
    @Override
    protected String _extract(final String resource) {
        // TODO:対応が面倒なので、ひとまず例外は握りつぶしておく。
        String headerValue = null;
        try {
            URL url = new URL(resource);
            URLConnection connection = url.openConnection();
            headerValue = connection.getHeaderField(this.HeaderKey);
            boolean result = this.ExtractFunc.test(headerValue);
            System.out.println("test result -> " + result + " header -> " + this.HeaderKey
                    + " value -> " + headerValue + " resource -> " + resource);
            return this.ExtractFunc.test(headerValue) ? resource : "";
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
}
