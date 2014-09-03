package wikiboot;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import wikiboot.render.TemplateSource;
import wikiboot.support.BaseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;
import static wikiboot.test.MatchesPattern.matchesPattern;

/**
 * Verify that the implementations of {@link java.lang.Object} are sane.
 */
public class ObjectOverridesTests {

    private static final String UUID_PATTERN = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";


    private static final List<Class> CLASSES_UNDER_TEST = Arrays.asList(
            Article.class, BaseEntity.class, ArticleSet.class, DataSet.class,
            Template.class, TemplateSource.class, TemplateType.class, ValueOverride.class, ValueOverride.ValueOverrideId.class
    );

    @Test
    public void equalsContract() {
        for (Class clazz : CLASSES_UNDER_TEST) {
            try {
                EqualsVerifier.forClass(clazz)
                              .usingGetClass().suppress(Warning.NULL_FIELDS)
                              .verify();
            } catch (AssertionError error) {
                throw new AssertionError(clazz.getName() + ": " + error.getMessage());
            }
        }
    }

    @Test
    public void articleSetToString() {
        ArticleSet articleSet = createArticleSet();
        articleSet.addArticle(0, createArticle());
        String stringVal = articleSet.toString();
        assertIdentity(stringVal);
        assertThat(stringVal, matchesPattern(
                ".*template=<Template>, localTemplate=<Template>, articles=<size=1>, valueOverrides=<null>, dataSet=<null>, overrides=<null>]"));
    }

    @Test
    public void articleToString() {
        Article article = createArticle();
        String stringVal = article.toString();
        assertIdentity(stringVal);
        assertThat(stringVal, matchesPattern(".*title=a title, content=some content.*"));
    }

    @Test
    public void templateToString() {
        Template template = createTemplate();
        String stringVal = template.toString();
        assertIdentity(stringVal);
        assertThat(stringVal, matchesPattern(".*type=FREEMARKER, content=some content.*"));
    }

    @Test
    public void templateWithLongAndBoringContentToString() {
        Template template = createTemplate(RandomStringUtils.random(100));
        String stringVal = template.toString();
        assertIdentity(stringVal);
        assertThat(stringVal, matchesPattern(".*type=FREEMARKER, content=<String>.*"));
    }

    @Test
    public void valueOverrideToString() {
        ValueOverride valueOverride = ValueOverride.of(createArticleSet(), "path", "old", "new");
        String stringVal = valueOverride.toString();
        assertThat(stringVal, matchesPattern(".*key=path, oldValue=old, newValue=new, articleSet=<ArticleSet.*"));
    }

    private void assertIdentity(String val) {
        assertThat(val, matchesPattern(".*id=<null>, uuid=" + UUID_PATTERN + ".*"));

    }

    private ArticleSet createArticleSet() {
        return new ArticleSet(createTemplate());
    }

    private Article createArticle() {
        return new Article("a title", "some content");
    }

    private Template createTemplate(String content) {
        return Template.from(content);

    }

    private Template createTemplate() {
        return createTemplate("some content");
    }

}
