package nightwalker.api.models.resources.html;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * HTTPページリソース
 * Created by takashi on 2014/12/14.
 */
public final class PageResource {
    /**
     * URL
     */
    @Getter
    private URL url;

    /**
     * コンストラクタ
     * @param url URL
     */
    public PageResource(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    /**
     * HTMLページを取得します。
     * @return HTMLページ
     */
    public String getHtml() throws java.io.IOException {
        return this.getHtmlJavascriptDisabled();
    }

    /**
     * JavaScriptを実行せずに、HTMLページを取得します。
     * @return HTMLページ
     */
    private String getHtmlJavascriptDisabled() throws java.io.IOException {
        HttpURLConnection connection = (HttpURLConnection)this.url.openConnection();
        connection.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder html = new StringBuilder();
        String str;
        while ( (str = reader.readLine())!= null ){
            html.append(str);
        }
        reader.close();
        connection.disconnect();
        return html.toString();
    }

    /**
     * JavaScriptを実行して、HTMLページを取得します。
     * @return HTMLページ
     */
    private String getHtmlJavascriptEnabled() throws java.io.IOException {
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
        webClient.waitForBackgroundJavaScript(10000);
        HtmlPage page = webClient.getPage(this.url);

        return page.asXml();
    }
}
