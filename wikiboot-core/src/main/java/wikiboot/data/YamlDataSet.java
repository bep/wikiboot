package wikiboot.data;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;
import wikiboot.DataSet;
import wikiboot.data.support.MapToDataItemConverter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Creates a {@link wikiboot.DataSet} from a Yaml resource.
 *
 * @author Bjørn Erik Pedersen
 */
public class YamlDataSet extends DataSet {

    private static final MapToDataItemConverter MAP_TO_DATA_ITEM_CONVERTER = new MapToDataItemConverter();

    public YamlDataSet(Resource resource) {
        try {
            initDataSet(resource);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load data set", e);
        }
    }

    public YamlDataSet(String classPathResourcePath) {
        this(new ClassPathResource(classPathResourcePath));
    }

    private void initDataSet(Resource resource) throws Exception {

        Yaml yaml = new Yaml();

        for (Object object : yaml.loadAll(resource.getInputStream())) {
            if (object != null) {
                Map<String, Object> documentMap = asMap(object);
                this.dataItems.add(MAP_TO_DATA_ITEM_CONVERTER.convert(documentMap));
            }
        }

    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> asMap(Object object) {
        // YAML can have numbers as keys
        Map<String, Object> result = new LinkedHashMap<>();
        if (!(object instanceof Map)) {
            // A document can be a text literal
            result.put("document", object);
            return result;
        }

        Map<Object, Object> map = (Map<Object, Object>) object;
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                value = asMap(value);
            }
            Object key = entry.getKey();
            if (key instanceof CharSequence) {
                result.put(key.toString(), value);
            } else {
                // It has to be a map key in this case
                result.put("[" + key.toString() + "]", value);
            }
        }
        return result;
    }

}
