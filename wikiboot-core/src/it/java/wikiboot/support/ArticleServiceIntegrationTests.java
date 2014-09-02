package wikiboot.support;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import wikiboot.*;
import wikiboot.data.YamlDataSet;
import wikiboot.test.AbstractIntegrationTests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;


@Transactional
public class ArticleServiceIntegrationTests extends AbstractIntegrationTests {

    private final DataSet dataSet = new YamlDataSet("wikiboot/data/test-dataset.yaml");

    @Autowired
    ArticleService articleService;

    @Test
    public void createNewArticleInExistingSet() throws Exception {
        ArticleSet articleSet = new ArticleSet(templateRepository.save(Template.from("some-content")));
        articleSetRepository.save(articleSet);

        Article article = articleService.getOrCreateArticle(articleSet.getId(), 0, dataSet);

        assertThat(article, not(nullValue()));
        assertThat(article.getId(), not(nullValue()));
        assertThat(article.getArticleSet(), is(articleSet));
        assertThat(article, sameInstance(articleSet.getArticles().iterator().next()));
    }

    @Test
    public void articleExists() throws Exception {
        ArticleSet articleSet = new ArticleSet(templateRepository.save(Template.from("some-content")));
        Article article = new Article("title", "content");
        articleSet.addArticle(0, article);
        articleSetRepository.save(articleSet);

        Article reloaded = articleService.getOrCreateArticle(articleSet.getId(), 0, dataSet);

        assertThat(reloaded, not(nullValue()));
        assertThat(reloaded, is(article));

    }

    @Test(expected = ResourceNotFound.class)
    public void articleSetNotFund() throws Exception {
        articleService.getOrCreateArticle(9999, 0, dataSet);
    }
}
