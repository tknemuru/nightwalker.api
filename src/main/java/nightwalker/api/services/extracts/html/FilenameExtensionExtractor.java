package nightwalker.api.services.extracts.html;

import nightwalker.api.helper.UrlHelper;

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
     * 抽出対象の拡張子が指定されているかどうかを示します。
     * 未指定の場合はディレクトリパスが抽出されます。
     */
    private  boolean HasExtensions;

    /**
     * コンストラクタ
     * @param extensions 抽出対象の拡張子
     */
    public FilenameExtensionExtractor(String... extensions) {
        this.Extensions = Arrays.asList(extensions);

        // 抽出対象の拡張子が指定されているか確認
        this.HasExtensions = (extensions.length > 0)
                && this.Extensions.stream()
                    .anyMatch(ext -> !ext.equals(""));
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
    protected String _extract(final String resource) {
        // TODO:対応が面倒なので、ひとまず例外は握りつぶしておく。
        boolean isFilePath = false;
        try {
            isFilePath = UrlHelper.isFilePath(resource);
        }
        catch (Exception ex) {
        }

        if(isFilePath){
            // ファイルパスならば、指定した拡張子と一致しているパスのみを抽出する
            String noQueryResource = resource.split("\\?")[0];
            String extension = noQueryResource.split("\\.")[noQueryResource.split("\\.").length - 1];
            return this.Extensions.contains(extension) ? resource : "";
        }
        else {
            // ディレクトリパスならば、拡張子が未指定の場合のみ抽出対象にする
            return !this.HasExtensions ? resource : "";
        }
    }
}
