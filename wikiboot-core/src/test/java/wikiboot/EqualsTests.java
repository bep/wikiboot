package wikiboot;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;
import wikiboot.render.TemplateSource;
import wikiboot.support.BaseEntity;

import java.util.Arrays;
import java.util.List;

/**
 * Verify that the {@link #equals(Object)} implementations are sane.
 */
public class EqualsTests {

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

}
