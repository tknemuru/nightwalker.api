package nightwalker.api.services.extracts;

import java.net.URISyntaxException;
import java.util.stream.Stream;

/**
 * リソースから条件に合致する情報を抽出する機能を提供します。
 * Created by takashi on 2014/12/14.
 */
public abstract class ResourceExtractor {
    /**
     * リソースから条件に合致する情報を抽出します。
     * @param resource リソース
     * @return 条件に合致する情報
     */
    public abstract ExtractedList extract(String resource);

    /**
     * リソースから条件に合致する情報を抽出します。
     * @param resource リソース
     * @return 条件に合致する情報
     */
    public final ExtractedList extract(ExtractedList resource) {
        Stream<String> s = resource.stream()
                .filter(r -> !this._extract(r).isEmpty())
                .map(r -> this._extract(r));
        return new ExtractedList(s);
    }

    /**
     * リソースから条件に合致する情報を抽出します。
     * @param resource リソース
     * @return 条件に合致する情報
     */
    protected abstract String _extract(String resource);
}
