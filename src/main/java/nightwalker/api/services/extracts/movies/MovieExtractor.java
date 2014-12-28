package nightwalker.api.services.extracts.movies;

import javax.xml.parsers.ParserConfigurationException;

import nightwalker.api.models.resources.Movie;
import nightwalker.api.models.resources.MovieType;
import nightwalker.api.models.resources.PageResource;
import nightwalker.api.services.extracts.plans.ElementsParser;
import nightwalker.api.services.extracts.plans.PageParser;
import nightwalker.api.services.extracts.plans.PlanValidator;
import nightwalker.api.services.extracts.plans.ValuesParser;
import nightwalker.api.services.parses.xml.XmlParser;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.UncheckedIOException;
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
    public final List<Movie> extract() throws SAXException, IOException, ParserConfigurationException {
        return this.extract(null);
    }
    
    /**
     * 動画の抽出を行います。*
     * @param page 抽出対象のページ
     * @return 抽出した動画情報
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public final List<Movie> extract(PageResource page) throws SAXException, IOException, ParserConfigurationException {
        // 動画タイプの取得
        MovieType movieType = MovieType.valueOf(this.Plan.getElementsByTagName("type").item(0).getTextContent());

        // コンテナの取得
        org.jsoup.select.Elements containers = this.getContainers(page);
        
        // コンテナごとに動画情報を取得
        List<Movie> movies = new ArrayList<>();
        containers.forEach(container ->
                {
                    try{
                        
                        Movie movie = new Movie();
                        movie.setMovieType(movieType);
                        movie.setImage(this.getValues(container, "image"));
                        movie.setVideo(this.getValues(container, "video"));
                        movie.setDesc(this.getValues(container, "desc"));
                        movie.setTime(this.getValues(container, "time"));
                        movie.setTags(this.getValues(container, "tags"));
                        movie.setNextPage(this.getValues(container, "nextpage"));
                        movies.add(movie);
                    }
                    catch (IOException ex) {
                        throw new UncheckedIOException(ex);
                    }
                }
        );
        
        return movies;
    }

    /**
     * コンテナを取得します。*
     * @param page 取得対象のページ
     * @return
     * @throws IOException
     */
    private org.jsoup.select.Elements getContainers(PageResource page) throws IOException {
        Element containerPlan = (Element)(this.Plan.getElementsByTagName("container").item(0));
        
        // 子は必ずpage
        PlanValidator.requiredChildNodesValidationThrowsException(containerPlan, "page");
        PlanValidator.childNodesNameValidationThrowsException(containerPlan, "page");
        
        // pageを取得
        Element pagePlan = (Element)(XmlParser.removeNewLineChildNodes(containerPlan)
                .getFirstChild());
        if(page == null) {
            page = PageParser.parse(pagePlan);
        }
        
        // pageの子は必ずelements
        Element elementsPlan = (Element)(XmlParser.removeNewLineChildNodes(pagePlan)
                .getFirstChild());
        
        // elementsを取得
        return ElementsParser.parse(elementsPlan, page);
    }

    /**
     * valuesを取得します。*
     * @param container コンテナ
     * @param key 取得する情報のキー
     * @return values
     * @throws IOException
     */
    private List<String> getValues(org.jsoup.nodes.Element container, String key) throws IOException {
        Element plan = (Element)(this.Plan.getElementsByTagName(key).item(0));
        return this.getValues(plan, container);
    }
    
    /**
     * valuesを取得します。*
     * @param plan 抽出計画
     * @param container
     * @return
     * @throws IOException
     */
    private List<String> getValues(final Element plan, final org.jsoup.nodes.Element container) throws IOException {
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
                values.addAll(ValuesParser.parse(childPlan, container));
            }
            else{
                // pageならば新たなページを読み込む
                values.addAll(this.getValues(childPlan, PageParser.parse(childPlan, container).getDocument()));
            }
        }
        
        return values;
    }
}
