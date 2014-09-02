package wikiboot.support;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import wikiboot.Article;
import wikiboot.ArticleSet;
import wikiboot.Template;
import wikiboot.TemplateType;
import wikiboot.render.ValueOverride;
import wikiboot.test.AbstractIntegrationTests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Integration tests for the repositories.
 *
 * @author Bjørn Erik Pedersen
 */

@Transactional
public class RepositoryIntegrationTests extends AbstractIntegrationTests {

    @Test
    public void createTemplate() {

        Template first = templateRepository.save(Template.from("some-content"));

        assertThat(first, not(nullValue()));
        assertThat(first.getId(), not(nullValue()));
        assertThat(first.getType(), is(TemplateType.FREEMARKER));
        assertThat(first.getContent(), is("some-content"));
    }

    @Test
    public void createArticleSet() {
        ArticleSet articleSet = getArticleSet();

        articleSet.addValueOverride("aKey", "anOldValue", "aNewValue");

        ArticleSet savedArticleSet = articleSetRepository.save(articleSet);

        assertThat(savedArticleSet.getLocalTemplate().getContent(), is(savedArticleSet.getTemplate().getContent()));
        assertThat(savedArticleSet.getLocalTemplate().getType(), is(savedArticleSet.getTemplate().getType()));
        assertThat(savedArticleSet.getLocalTemplate().getId(), not(savedArticleSet.getTemplate().getId()));
        assertThat(savedArticleSet.getValueOverrides(), hasItem(ValueOverride.of("aKey", "anOldValue", "aNewValue")));

    }

    @Test
    public void findArticleByArticleSetAndIndex() {
        ArticleSet articleSet = getArticleSet();
        Article article = new Article("title", "some content");
        articleSet.addArticle(0, article);
        articleSetRepository.save(articleSet);

        flush();

        Article foundArticle = articleRepository.findByArticleSetIdAndIndex(1L, 0);

        assertThat(articleRepository.findAll().size(), is(1));
        assertThat(foundArticle, not(nullValue()));
        assertThat(foundArticle.getTitle(), is("title"));
    }

    private ArticleSet getArticleSet() {
        Template template = templateRepository.save(Template.from("article-set"));
        return new ArticleSet(template);
    }
}
