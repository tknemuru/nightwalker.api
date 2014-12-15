package nightwalker.api.services.extracts.html;

import nightwalker.api.services.extracts.ExtractedList;
import nightwalker.api.services.extracts.ResourceExtractor;

import java.util.Arrays;
import java.util.List;

/**
 * 指定した拡張子のファイルパスを抽出する機能を提供します。
 * Created by takashi on 2014/12/15.
 */
public final class FilenameExtensionExtractor extends ResourceExtractor {
    /**
     * 抽出対象の拡張子
     */
    private List<String> Extensions;

    /**
     * コンストラクタ
     * @param extensions 抽出対象の拡張子
     */
    public FilenameExtensionExtractor(String... extensions) {
        this.Extensions = Arrays.asList(extensions);
    }

    /**
     * HTMLから指定した拡張子のファイルパスを抽出します。
     * @param resource リソース
     * @return 指定した拡張子のファイルパス
     */
    @Override
    public ExtractedList extract(String resource) {
        // TODO: 後でちゃんと実装
        return new ExtractedList();
    }

    /**
     * HTMLから指定した拡張子のファイルパスを抽出します。
     * @param resource リソース
     * @return 指定した拡張子のファイルパス
     */
    @Override
    protected String _extract(String resource) {
        String extension = resource.split("\\.")[resource.split("\\.").length - 1];
        return this.Extensions.contains(extension) ? resource : "";
    }
}
