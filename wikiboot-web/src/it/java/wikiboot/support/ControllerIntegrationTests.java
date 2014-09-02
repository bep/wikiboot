package wikiboot.support;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import wikiboot.MainConfig;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;


/**
 * Some basic smoke tests to make sure the controllers are wired up correctly.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainConfig.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
@ActiveProfiles({"development", "integrationTest"})
public class ControllerIntegrationTests {

    @Value("${local.server.port}")
    private int port;

    private final RestTemplate restTemplate = new TestRestTemplate();
    private final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);

    @Test
    public void nonExistingResourceShouldReturnNotFoundErrorBody() throws Exception {
        ResponseEntity<String> entity = restTemplate.getForEntity(getBaseUrl() + "/api/rendering/preview/123456?index=878", String.class);

        assertThat(entity.getBody(), containsString("http://httpstatus.es/404"));
        assertThat(entity.getBody(), containsString("Resource 'articleSet' with id 123456 not found"));
    }


    @Test
    public void noHandlerFoundShouldReturnErrorBody() throws Exception {
        ResponseEntity<String> entity = restTemplate.getForEntity(getBaseUrl() + "/api/rendering8/abc/abc/notFound", String.class);

        assertThat(entity.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(entity.getBody(), containsString("http://httpstatus.es/404"));
        assertThat(entity.getBody(), containsString("Unable to determine a corresponding handler"));

    }

    @Test
    public void noTemplateFoundShouldReturnErrorNotFound() throws Exception {
        ResponseEntity<String> entity = restTemplate.getForEntity(getBaseUrl() + "/api/templates/12345", String.class);

        assertThat(entity.getStatusCode(), is(HttpStatus.NOT_FOUND));
        // todo figure out a way to set up Spring Data Rest's error handling ... or remove Spring Data Rest
        // assertThat(entity.getBody(), containsString("http://httpstatus.es/404"));
        //assertThat(entity.getBody(), not(containsString("Unable to determine a corresponding handler")));
    }

    @Test
    public void templateFoundShouldReturnOK() throws Exception {
        ResponseEntity<String> entity = restTemplate.getForEntity(getBaseUrl() + "/api/templates/1", String.class);

        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void goToHomePage() throws Exception {
        HtmlPage page = webClient.getPage(getBaseUrl() + "/");
        assertThat(page.getTitleText(), containsString("Wikiboot"));
    }

    @Test
    public void pageNotFoundInRootContextShouldReturnAnErrorPage() throws Exception {
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        HtmlPage page = webClient.getPage(getBaseUrl() + "/notFound");
        assertThat(page.getTitleText(), containsString("Error"));
        assertThat(page.getBody().getTextContent(), containsString("Error (404)"));
    }

    private String getBaseUrl() {
        return "http://localhost:" + this.port;
    }


}
