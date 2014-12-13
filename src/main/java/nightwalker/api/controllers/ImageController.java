package nightwalker.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takashi on 2014/12/13.
 */
@RequestMapping("/api/v1/image")
@RestController
public class ImageController {
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<String> index()
    {
        return new ArrayList<String>();
    }
}
