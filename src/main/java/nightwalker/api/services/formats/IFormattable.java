package nightwalker.api.services.formats;

/**
 * 文字列の整形機能を提供します。
 * Created by takashi on 2014/12/15.
 */
public interface IFormattable {
    /**
     * 整形した文字列を返します。
     * @param org 元の文字列
     * @return 整形した文字列
     */
    String format(String org);
}
