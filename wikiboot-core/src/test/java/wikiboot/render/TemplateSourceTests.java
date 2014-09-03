package wikiboot.render;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class TemplateSourceTests {

    @Test
    public void templateSourceShouldUseIdForEquals() {
        TemplateSource templateSource = new TemplateSource("10", "abc", 100L);
        assertThat(templateSource, is(templateSource));
        assertThat(new TemplateSource("1", "a", 1L), is(new TemplateSource("1", "a", 1L)));
        assertThat(new TemplateSource("1", "a", 1L), is(new TemplateSource("1", "ab", 1L)));
        assertThat(new TemplateSource("1", "a", 1L), is(new TemplateSource("1", "a", 2L)));
        assertThat(new TemplateSource("1", "a", 1L), not(new TemplateSource("2", "a", 1L)));
        assertThat(templateSource.equals(null), is(false));
        assertThat(templateSource.equals(new Object()), is(false));
    }

    @Test
    public void templateSourceShouldUseIdForHashCode() {
        assertThat(new TemplateSource("1", "a", 1L).hashCode(), is("1".hashCode()));

    }
}
