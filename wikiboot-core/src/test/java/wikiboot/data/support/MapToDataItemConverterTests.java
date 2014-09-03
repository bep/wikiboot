package wikiboot.data.support;

import org.junit.Test;
import org.springframework.core.convert.converter.Converter;
import wikiboot.DataItem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link wikiboot.data.support.MapToDataItemConverter}.
 */
public class MapToDataItemConverterTests {

    private Converter<Map<String, Object>, DataItem> converter = new MapToDataItemConverter();

    @Test
    public void convert() throws Exception {
        Map<String, Object> source = new HashMap<>();
        source.put("title", "Miles");
        source.put("instrument", "trumpet");

        DataItem actual = converter.convert(source);
        assertThat(actual.getTitle(), is("Miles"));
        assertThat(actual.getModel().size(), is(2));
        assertThat(actual.getModel().get("title"), is("Miles"));
        assertThat(actual.getModel().get("instrument"), is("trumpet"));
    }

    @Test
    public void extractTitle() throws Exception {
        assertThat(converter.convert(Collections.singletonMap("title", "abc")).getTitle(), is("abc"));
        assertThat(converter.convert(Collections.singletonMap("name", "abc")).getTitle(), is("abc"));
        assertThat(converter.convert(Collections.singletonMap("jazz", "abc")).getTitle(), is(MapToDataItemConverter.TITLE_NOT_FOUND));
        assertThat(converter.convert(Collections.singletonMap("title", 1L)).getTitle(), is(MapToDataItemConverter.TITLE_NOT_FOUND));
    }
}
