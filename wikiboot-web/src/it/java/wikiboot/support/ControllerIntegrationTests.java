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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import wikiboot.DataItem;
import wikiboot.MainConfig;
import wikiboot.Template;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
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
        ResponseEntity<Template> entity = restTemplate.getForEntity(getBaseUrl() + "/api/templates/1", Template.class);
        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(entity.getBody().getId(), is(1L));
    }

    // Rendering tests

    @Test
    public void renderPreview() throws Exception {
        ResponseEntity<RenderingController.ContentWrapper> entity =
                restTemplate.getForEntity(getBaseUrl() + "/api/rendering/preview/1?index=0", RenderingController.ContentWrapper.class);
        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(StringUtils.hasText(entity.getBody().getContent()), is(true));
    }

    @Test
    public void render() throws Exception {
        // todo clear up the message converter issue with json+schema and TestRestTemplate's redirect and test the real return value from this (-> Article)
        ResponseEntity<String> entity =
                restTemplate.getForEntity(getBaseUrl() + "/api/rendering/render/1?index=0", String.class);
        assertThat(entity.getStatusCode(), is(HttpStatus.FOUND));
    }


    @Test
    public void dataSet() throws Exception {
        ParameterizedTypeReference<List<DataItem>> typeRef = new ParameterizedTypeReference<List<DataItem>>() {
        };
        ResponseEntity<List<DataItem>> entity =
                restTemplate.exchange(getBaseUrl() + "/api/rendering/dataSet", HttpMethod.GET, null, typeRef);
        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(entity.getBody().isEmpty(), is(false));
    }

    @Test
    public void dataItem() throws Exception {
        ResponseEntity<DataItem> entity = restTemplate.getForEntity(getBaseUrl() + "/api/rendering/dataSet/0", DataItem.class);
        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(entity.getBody().getModel().isEmpty(), is(false));
    }


    // Web ui tests

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
