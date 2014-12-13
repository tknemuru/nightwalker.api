package nightwalker.api.models.resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by takashi on 2014/12/14.
 */
public class HttpPageResource implements IResource {
    /**
     * URL
     */
    private URL _url;

    /**
     * コンストラクタ
     * @param url URL
     */
    public HttpPageResource(String url) throws MalformedURLException {
        this._url = new URL(url);
    }

    /**
     * HTMLページを取得します。
     * @return HTMLページ
     */
    public String getContent() throws java.io.IOException {
        HttpURLConnection connection = (HttpURLConnection)this._url.openConnection();
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
}
