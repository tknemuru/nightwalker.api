package nightwalker.api.controllers;

import nightwalker.api.models.resources.html.Image;
import nightwalker.api.models.resources.html.PageResource;
import nightwalker.api.services.extracts.ExtractedList;
import nightwalker.api.services.extracts.html.FilenameExtensionExtractor;
import nightwalker.api.services.extracts.html.CssSelectorAttrValueExtractor;
import nightwalker.api.services.formats.html.AbsolutePathFormatter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by takashi on 2014/12/13.
 */
@RequestMapping("/api/v1/url/images")
@RestController
public class ImageController {
    @RequestMapping(method = RequestMethod.GET
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Image> index(HttpServletResponse response
            , @RequestParam("target") String targetUrl) throws URISyntaxException, java.io.IOException
    {
        // スクレイピング対象のページを読み込む
        PageResource resource = new PageResource(
                URLDecoder.decode(targetUrl, StandardCharsets.UTF_8.name()));

        // 抽出を実行
        ExtractedList imageUrls = new CssSelectorAttrValueExtractor("img", "src")
                .extract(resource.getHtml())
                .concat(new CssSelectorAttrValueExtractor("a", "href")
                        .extract(resource.getHtml()))
                .extract(new FilenameExtensionExtractor("jpg", "png"))
                .format(new AbsolutePathFormatter(resource.getUrl().toString()));

        // 画像インスタンスに変換
        List<Image> images = imageUrls.stream()
                .map(url -> {
                    try {
                        return new Image(url);
                    } catch (IOException ex) {
                        throw new UncheckedIOException(ex);
                    }
                })
                .collect(Collectors.toList());

        // レスポンスヘッダにAllow-Originを付与
        response.setHeader("Access-Control-Allow-Origin", "*");

        return images;
    }
}
