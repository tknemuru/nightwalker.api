package nightwalker.api.models.resources;

/**
 * スクレイピング対象のリソース
 * Created by takashi on 2014/12/14.
 */
public interface IResource {
    /**
     * リソースのコンテンツを取得します。
     * @return コンテンツ
     */
    String getContent() throws java.io.IOException;
}
