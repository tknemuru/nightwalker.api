package nightwalker.api.services.extracts.html;

import nightwalker.api.services.formats.IFormattable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 抽出されたリスト
 * Created by takashi on 2014/12/15.
 */
public final class ExtractedList extends ArrayList<String> {
    /**
     * コンストラクタ
     * @param resource リソース
     */
    public ExtractedList(Stream<String> resource) {
        super(resource.collect(Collectors.toList()));
    }

    /**
     * コンストラクタ
     * @param resource リソース
     */
    public ExtractedList(String... resource) {
        super(Arrays.asList(resource));
    }

    /**
     * 抽出処理を行います。
     * @param extractor 抽出処理を行うサービス
     * @return 抽出されたリスト
     */
    public final ExtractedList extract(ResourceExtractor extractor) {
        return extractor.extract(this);
    }

    /**
     * 結合処理を行います。
     * @param list 結合対象のリスト
     * @return 結合されたリスト
     */
    public final ExtractedList concat(ExtractedList list) {
        return new ExtractedList(Stream.concat(this.parallelStream(), list.parallelStream()));
    }

    /**
     * 整形処理を行います。
     * @param formatter 整形処理を行うサービス
     * @return
     */
    public final ExtractedList format(IFormattable formatter) {
        return new ExtractedList(this.parallelStream()
                .map(value -> formatter.format(value)));
    }
}
