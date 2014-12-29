package nightwalker.api.services.extracts.movies;

import javax.xml.parsers.ParserConfigurationException;

import nightwalker.api.models.resources.Movie;
import nightwalker.api.models.resources.MovieResponse;
import nightwalker.api.models.resources.MovieType;
import nightwalker.api.models.resources.PageResource;
import nightwalker.api.services.extracts.plans.ElementsParser;
import nightwalker.api.services.extracts.plans.PageParser;
import nightwalker.api.services.extracts.plans.PlanValidator;
import nightwalker.api.services.extracts.plans.ValuesParser;
import nightwalker.api.services.parses.xml.XmlParser;
import org.jsoup.helper.StringUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 動画の抽出機能を提供します。*
 * Created by takashi on 2014/12/25.
 */
public final class MovieExtractor {
    /**
     * 抽出計画*
     */
    private Element Plan;

    /**
     * コンストラクタ*
     * @param plan 抽出計画
     */
    public MovieExtractor(Element plan) {
        this.Plan = plan;
    }
    
    /**
     * 動画の抽出を行います。*
     * @return 抽出した動画情報
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public final MovieResponse extract() throws SAXException, IOException, ParserConfigurationException, URISyntaxException {
        return this.extract(null);
    }
    
    /**
     * 動画の抽出を行います。*
     * @param pageUrl 抽出対象ページのURL
     * @return 抽出した動画情報
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public final MovieResponse extract(String pageUrl) throws SAXException, IOException, ParserConfigurationException, URISyntaxException {
        // 動画タイプの取得
        MovieType movieType = MovieType.valueOf(this.Plan.getElementsByTagName("type").item(0).getTextContent());

        // 取得件数の取得
        int maxCount = Integer.parseInt(this.Plan.getElementsByTagName("maxcount").item(0).getTextContent());
        
        // ページの取得
        PageResource page = this.getPage(pageUrl);
        
        // コンテナの取得
        org.jsoup.select.Elements containers = this.getContainers(page);
        
        // コンテナごとに動画情報を取得
        List<Movie> movies = new ArrayList<>();
        int count = 0;
        for(org.jsoup.nodes.Element container: containers) {
            Movie movie = new Movie();
            movie.setMovieType(movieType);
            movie.setImage(this.getValues(container, "image", page.getUrl().toString()));
            movie.setVideo(this.getValues(container, "video", page.getUrl().toString()));
            movie.setDesc(this.getValues(container, "desc", page.getUrl().toString()));
            movie.setTime(this.getValues(container, "time", page.getUrl().toString()));
            movie.setTags(this.getValues(container, "tags", page.getUrl().toString()));
            movies.add(movie);
            count++;
            if(count >= maxCount) { break; }
        }
        
        // 次に読み込むページのリストを取得
        List<String> nextPages = this.getValues(page.getDocument(), "nextpage", page.getUrl().toString());
        
        return new MovieResponse(movies, nextPages);
    }

    /**
     * ページを取得します。*
     * @param url 抽出対象のURL
     * @return 抽出対象のページ
     */
    private PageResource getPage(String url) throws IOException{
        if(StringUtil.isBlank(url)) {
            // 抽出計画からページを生成
            Element pagePlan = (Element)(this.Plan.getElementsByTagName("page").item(0));
            return PageParser.parse(pagePlan);
        }
        else {
            // 渡されたURLからページを生成
            return new PageResource(url);
        }
    }
    
    /**
     * コンテナを取得します。*
     * @param page 取得対象のページ
     * @return
     * @throws IOException
     */
    private org.jsoup.select.Elements getContainers(PageResource page) throws IOException {
        Element containerPlan = (Element)(this.Plan.getElementsByTagName("container").item(0));
        
        // 子は必ずelements
        PlanValidator.requiredChildNodesValidationThrowsException(containerPlan, "elements");
        PlanValidator.childNodesNameValidationThrowsException(containerPlan, "elements");
        
        // elementsを取得
        Element elementsPlan = (Element)(XmlParser.removeNewLineChildNodes(containerPlan)
                .getFirstChild());
        return ElementsParser.parse(elementsPlan, page);
    }

    /**
     * valuesを取得します。*
     * @param container コンテナ
     * @param key 取得する情報のキー
     * @return values
     * @throws IOException
     */
    private List<String> getValues(org.jsoup.nodes.Element container, String key, String pageUrl) throws IOException, URISyntaxException {
        Element plan = (Element)(this.Plan.getElementsByTagName(key).item(0));
        return this.getValues(plan, container, pageUrl);
    }
    
    /**
     * valuesを取得します。*
     * @param plan 抽出計画
     * @param container
     * @return
     * @throws IOException
     */
    private List<String> getValues(final Element plan, final org.jsoup.nodes.Element container, String pageUrl) throws IOException, URISyntaxException {
        List<String> values = new ArrayList<>();

        // 子はvaluesかpage
        PlanValidator.childNodesNameValidationThrowsException(plan, "values", "page");
        boolean hasValues = PlanValidator.requiredChildNodesValidation(plan, "values");
        boolean hasPage = PlanValidator.requiredChildNodesValidation(plan, "page");
        if((!hasValues) && (!hasPage)) {
            throw new IllegalArgumentException("子はvaluesかpageである必要があります。");
        }

        NodeList childPlans = XmlParser.removeNewLineChildNodes(plan).getChildNodes();
        for(int i = 0; i < childPlans.getLength(); i++) {
            Element childPlan = (Element)(childPlans.item(i));
            if(childPlan.getNodeName().equals("values")) {
                // valuesなら現在のコンテナでvaluesを取得する
                values.addAll(ValuesParser.parse(childPlan, container, pageUrl));
            }
            else{
                // pageならば新たなページを読み込む
                PageResource page = PageParser.parse(childPlan, container, pageUrl);
                values.addAll(this.getValues(childPlan, page.getDocument(), page.getUrl().toString()));
            }
        }
        
        return values;
    }
}
