package wikiboot.data.support;

import org.springframework.core.convert.converter.Converter;
import org.springframework.ui.ModelMap;
import wikiboot.DataItem;

import java.util.Map;

/**
 * Convert a {@link Map} into a {@link wikiboot.DataItem}.
 *
 * @author Bjørn Erik Pedersen
 */
public class MapToDataItemConverter implements Converter<Map<String, Object>, DataItem> {

    private static final String[] TITLE_KEYS = {"title", "name"};

    static final String TITLE_NOT_FOUND = "TITLE_NOT_FOUND";

    @Override
    public DataItem convert(Map<String, Object> source) {
        String title = extractTitle(source);
        ModelMap model = new ModelMap();
        model.addAllAttributes(source);
        return new DataItem(title, model);
    }

    private String extractTitle(Map<String, Object> source) {
        for (String key : TITLE_KEYS) {
            if (source.containsKey(key)) {
                Object val = source.get(key);
                if (val instanceof String) {
                    return (String) val;
                }
            }
        }
        return TITLE_NOT_FOUND;
    }
}
