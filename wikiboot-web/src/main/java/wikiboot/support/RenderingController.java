package wikiboot.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import wikiboot.Article;
import wikiboot.DataItem;
import wikiboot.DataSet;
import wikiboot.data.YamlDataSet;
import wikiboot.mediawiki.support.MediaWikiClient;

import java.util.List;

/**
 * Controller for rendering of articles.
 *
 * @author Bjørn Erik Pedersen
 */
@RestController
@RequestMapping(value = "/api/rendering", produces = MediaType.APPLICATION_JSON_VALUE)
public class RenderingController {

    private static final Logger logger = LoggerFactory.getLogger(RenderingController.class);

    @Autowired
    private ArticleService articles;

    @Autowired
    private MediaWikiClient mediaWikiClient;

    private final DataSet dataSet = new YamlDataSet("wikiboot/data/test-dataset.yaml");

    @RequestMapping(value = "/preview/{articleSetId}", method = RequestMethod.GET)
    public ContentWrapper preview(@PathVariable long articleSetId, @RequestParam("index") int dataSetIndex) {
        Article article = articles.getOrCreateArticle(articleSetId, dataSetIndex, dataSet);
        String preview = mediaWikiClient.previewArticle(article.getContent(), article.getTitle()).getText();

        return new ContentWrapper(preview);
    }

    @RequestMapping(value = "/render/{articleSetId}", method = RequestMethod.GET)
    public View render(@PathVariable long articleSetId, @RequestParam("index") int dataSetIndex) {
        Article article = articles.getOrCreateArticle(articleSetId, dataSetIndex, dataSet);
        return new RedirectView("/api/article/" + article.getId());
    }

    @RequestMapping(value = "/dataSet", method = RequestMethod.GET)
    @ResponseBody
    public List<DataItem> dataSet() {
        return dataSet.getDataItems();
    }

    @RequestMapping(value = "/dataSet/{dataSetIndex}", method = RequestMethod.GET)
    @ResponseBody
    public DataItem dataItem(@PathVariable int dataSetIndex) {
        return dataSet.getDataItems().get(dataSetIndex);
    }

    public static class ContentWrapper {

        String content;

        public ContentWrapper() {
        }

        public ContentWrapper(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }

}
