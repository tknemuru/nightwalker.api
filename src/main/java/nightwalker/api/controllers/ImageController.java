package nightwalker.api.controllers;

import nightwalker.api.models.resources.html.PageResource;
import nightwalker.api.services.extracts.ExtractedList;
import nightwalker.api.services.extracts.html.FilenameExtensionExtractor;
import nightwalker.api.services.extracts.html.TagAttrValueExtractor;
import nightwalker.api.services.formats.html.AbsolutePathFormatter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by takashi on 2014/12/13.
 */
@RequestMapping("/api/v1/url/images")
@RestController
public class ImageController {
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<String> index(@RequestParam("target") String targetUrl) throws java.io.IOException, URISyntaxException
    {
        // スクレイピング対象のページを読み込む
        PageResource resource = new PageResource(
                URLDecoder.decode(targetUrl, StandardCharsets.UTF_8.name()));

        // 抽出を実行
        ExtractedList images = new TagAttrValueExtractor("img", "src")
                .extract(resource.getHtml())
                .concat(new TagAttrValueExtractor("a", "href")
                        .extract(resource.getHtml()))
                .extract(new FilenameExtensionExtractor("jpg", "png"))
                .format(new AbsolutePathFormatter(resource.getUrl().toString()));

        return images;
    }
}
