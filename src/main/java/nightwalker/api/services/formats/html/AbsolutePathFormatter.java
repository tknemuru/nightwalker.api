package nightwalker.api.services.formats.html;

import nightwalker.api.services.formats.IFormattable;
import nightwalker.api.services.helper.UrlHelper;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * URLを絶対パスに整形する機能を提供します。
 * Created by takashi on 2014/12/15.
 */
public final class AbsolutePathFormatter implements IFormattable {
    /**
     * 基底URI
     */
    private URI BaseUri;

    /**
     * コンストラクタ
     * @param baseUrl 基底URL
     */
    public AbsolutePathFormatter(String baseUrl) throws URISyntaxException {
        this.BaseUri = new URI(this.preFormat(baseUrl));
    }

    /**
     * URLを絶対パスに整形します。
     * @param org 元の文字列
     * @return 絶対パスのURL
     */
    public String format(String org) {
        return this.BaseUri.resolve(org).toString();
    }

    /**
     * URLの事前整形を行います。
     * @param org 元のURL
     * @return 整形したURL
     */
    private String preFormat(String org) throws URISyntaxException {
        String formattedUrl = org;

        // URLがファイルかディレクトリかを判定する
        boolean isFilePath = UrlHelper.isFilePath(formattedUrl);

        // ファイルなら整形不要
        if(isFilePath){
            return formattedUrl;
        }

        // ディレクトリの場合は末尾が/(スラッシュ)かどうかを判定
        boolean isSlashEnd = (org.charAt(org.length() - 1) == '/');

        // 末尾が/(スラッシュ)でない場合は付与
        if(!isSlashEnd) {
            formattedUrl += "/";
        }

        return formattedUrl;
    }
}
