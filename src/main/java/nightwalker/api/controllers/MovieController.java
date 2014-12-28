package nightwalker.api.controllers;

import nightwalker.api.services.extracts.movies.MovieExtractor;
import nightwalker.api.models.resources.Movie;
import nightwalker.api.services.extracts.movies.MovieExtractorProvider;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by takashi on 2014/12/13.
 */
@RequestMapping("/api/v1/movies")
@RestController
public class MovieController {
    @RequestMapping(method = RequestMethod.GET
            , produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Movie> index(HttpServletResponse response) throws URISyntaxException, IOException
    {
        try{
            MovieExtractor extractor = MovieExtractorProvider.getJapaneseXvideosExtractor();
            List<Movie> movies = extractor.extract();
            return movies;
        }
        catch (Exception ex) {
            response.setStatus(500);
        }

        // レスポンスヘッダにAllow-Originを付与
        response.setHeader("Access-Control-Allow-Origin", "*");

        return null;
    }
}
