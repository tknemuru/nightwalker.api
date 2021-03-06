package nightwalker.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("scratch")
// Separate profile for web tests to avoid clashing databases
public class ApplicationTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    /**
     * レスポンスに抽出対象の画像とリンク先が含まれていることをテストします。
     * @throws Exception
     */
    @Test
    public void レスポンスに抽出対象の画像とリンク先が含まれている() throws Exception {

        this.mvc.perform(get("/api/v1/url/images/?target=http%3a%2f%2fwww%2e28lab%2ecom%2f")).andExpect(status().isOk())
                .andExpect(content().string(containsString("https://www.28lab.com/Content/Image/fujitv.png")))
                .andExpect(content().string(containsString("https://www.28lab.com/video.html")))
                .andExpect(content().string(containsString("https://www.facebook.com/28lab")));

    }
}