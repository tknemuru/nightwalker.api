package nightwalker.api.services.helper;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * URLに関する処理を提供します。
 * Created by takashi on 2014/12/16.
 */
public final class UrlHelper {
    /**
     * URLがファイルパスかディレクトリパスかを判断し、ファイルパスの場合はtrueを返します。
     * @param url URL
     * @return ファイルパスかどうか
     * @throws URISyntaxException
     */
    public  static final boolean isFilePath(final String url) throws URISyntaxException {
        // クエリパラメータを取り除いてから判定する
        String lastPath = url.split("\\?")[0];

        lastPath = lastPath.split("/")[lastPath.split("/").length - 1];
        return lastPath.contains(".") && !(lastPath.equals(new URI(url).getHost()));
    }
}
