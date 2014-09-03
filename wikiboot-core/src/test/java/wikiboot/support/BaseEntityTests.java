package wikiboot.support;

import org.junit.Test;
import wikiboot.Article;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link wikiboot.support.BaseEntity}.
 */
public class BaseEntityTests {

    @Test
    public void equals() throws Exception {
        TestEntity t1 = new TestEntity("t");
        TestEntity t2 = new TestEntity("t");
        assertThat(t1, is(t1));
        assertThat(t1, not(t2));
        assertThat(t1, not(new Article()));
        assertThat(t1.equals(null), is(false));
    }

    @Test
    public void hashCodeShouldBeCreatedFromUuid() throws Exception {
        TestEntity t1 = new TestEntity("t");
        assertThat(t1.hashCode(), is(t1.getUuid().hashCode()));
    }

    private static class TestEntity extends BaseEntity {
        String val;

        private TestEntity(String val) {
            this.val = val;
        }
    }
}
