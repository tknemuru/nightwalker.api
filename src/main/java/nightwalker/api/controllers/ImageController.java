package nightwalker.api.controllers;

import nightwalker.api.models.resources.Image;
import nightwalker.api.models.resources.PageResource;
import nightwalker.api.services.extracts.html.ExtractedList;
import nightwalker.api.services.extracts.html.FilenameExtensionExtractor;
import nightwalker.api.services.extracts.html.CssSelectorAttrValueExtractor;
import nightwalker.api.services.extracts.html.ResponseHeaderExtractor;
import nightwalker.api.services.formats.AbsolutePathFormatter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by takashi on 2014/12/13.
 */
@RequestMapping("/api/v1/url/images")
@RestController
public class ImageController {
    @RequestMapping(method = RequestMethod.GET
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public ImageControllerResponse index(HttpServletResponse response
            , @RequestParam("target") String targetUrl) throws URISyntaxException, IOException
    {
        ImageControllerResponse myResponse;
        try{
            // スクレイピング対象のページを読み込む
            PageResource resource = new PageResource(
                    URLDecoder.decode(targetUrl, StandardCharsets.UTF_8.name()));

            // 画像の抽出を実行
            Predicate<String> greaterThanCompareValue = (headerValue) -> Integer.parseInt(headerValue) >= 20000;
            ExtractedList imageUrls = new CssSelectorAttrValueExtractor("img", "src")
                    .extract(resource.getHtml())
                    .concat(new CssSelectorAttrValueExtractor("a", "href")
                            .extract(resource.getHtml()))
                    .extract(new FilenameExtensionExtractor("jpg", "png"))
                    .format(new AbsolutePathFormatter(resource.getUrl().toString()))
                    .extract(new ResponseHeaderExtractor("Content-Length", greaterThanCompareValue));

            // 画像インスタンスに変換
            List<Image> images = imageUrls.parallelStream()
                    .map(url -> {
                        try {
                            System.out.println("Image Read Success -> " + url);
                            return new Image(url);
                        } catch (IOException ex) {
                            System.out.println("***Image Read Failed -> " + url);
                            return null;
                        }
                    })
                    .filter(image -> image != null)
                    .collect(Collectors.toList());

            // 次に読み込むリンク先の抽出を実行
            ExtractedList hrefs = new CssSelectorAttrValueExtractor("a", "href")
                    .extract(resource.getHtml())
                    .extract(new FilenameExtensionExtractor())
                    .concat(new CssSelectorAttrValueExtractor("a", "href")
                            .extract(resource.getHtml())
                            .extract(new FilenameExtensionExtractor("html")))
                    .format(new AbsolutePathFormatter(resource.getUrl().toString()));

            // 画像リストとリンク先リストをレスポンスに埋め込む
            myResponse = new ImageControllerResponse(images, hrefs, null);
        }
        catch (Exception ex) {
            myResponse = new ImageControllerResponse(null, null, "");
            response.setStatus(500);
        }

        // レスポンスヘッダにAllow-Originを付与
        response.setHeader("Access-Control-Allow-Origin", "*");

        return myResponse;
    }

    /**
     * イメージコントローラのレスポンス
     */
    private class ImageControllerResponse {
        /**
         * コンストラクタ
         * @param images 画像リスト
         * @param hrefs リンク先URLリスト
         * @param errorMessage エラーメッセージ
         */
        public ImageControllerResponse(List<Image> images, List<String> hrefs, String errorMessage) {
            this.Resources = images;
            this.StorageAddresses = hrefs;
            this.ErrorMessage = errorMessage;
        }

        /**
         * 画像リスト
         */
        public List<Image> Resources;

        /**
         * 次に読み込むリンク先URLリスト
         */
        public List<String> StorageAddresses;

        /**
         * エラーメッセージ
         */
        public String ErrorMessage;
    }
}
