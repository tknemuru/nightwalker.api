package nightwalker.api.services.extractors;

import java.util.List;
import java.util.stream.Collectors;

/**
 * リソースから条件に合致する情報を抽出する機能を提供します。
 * Created by takashi on 2014/12/14.
 */
public abstract class Extractor {
    /**
     * リソースから条件に合致する情報を抽出します。
     * @param resources
     * @return 条件に合致する情報
     */
    public final List<String> extract(List<String> resources) {
        return resources.stream()
                .filter(r -> this.isValid(r))
                .collect(Collectors.toList());
    }

    /**
     * 条件に合致するかどうかを示します。
     * @param resource
     */
    protected abstract boolean isValid(String resource);
}
