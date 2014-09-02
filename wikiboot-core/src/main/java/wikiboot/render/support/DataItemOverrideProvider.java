package wikiboot.render.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javatuples.KeyValue;
import org.springframework.beans.BeanUtils;
import org.springframework.core.CollectionFactory;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import wikiboot.DataItem;

import java.util.Collection;
import java.util.Map;

/**
 * Provide overrides for values in a {@link DataItem}.
 *
 * @author Bjørn Erik Pedersen
 */
public class DataItemOverrideProvider {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public DataItem apply(Map<KeyValue<String, String>, String> overrides, DataItem item) {
        Assert.notNull(item, "dataItem must not be null");
        if (overrides == null || overrides.isEmpty()) {
            return item;
        }
        ModelMap newModelMap = new ModelMap();
        for (String key : item.getModel().keySet()) {
            Object originalValue = item.getModel().get(key);
            Object newValue = replaceWithOverrideIfFound(overrides, key, originalValue);
            newModelMap.put(key, newValue);
        }

        return new DataItem(item.getTitle(), newModelMap);
    }

    private Object replaceWithOverrideIfFound(Map<KeyValue<String, String>, String> overrides, String propertyPath, Object originalValue) {
        if (String.class.isAssignableFrom(originalValue.getClass())) {
            String override = overrides.get(new KeyValue<>(propertyPath, (String) originalValue));
            return override != null ? override : originalValue;
        } else if (originalValue.getClass().isArray() && String.class.isAssignableFrom(originalValue.getClass().getComponentType())) {
            String[] originalArr = (String[]) originalValue;
            String[] newArr = new String[originalArr.length];
            for (int i = 0; i < originalArr.length; i++) {
                newArr[i] = (String) replaceWithOverrideIfFound(overrides, propertyPath, originalArr[i]);
            }
            return newArr;
        } else if (BeanUtils.isSimpleProperty(originalValue.getClass())) {
            // no replacements for them yet.
            return originalValue;
        } else if (originalValue instanceof Collection) {
            Collection originalCollection = (Collection) originalValue;
            Collection<Object> newCollection = CollectionFactory.createApproximateCollection(originalCollection, originalCollection.size());
            for (Object o : originalCollection) {
                newCollection.add(replaceWithOverrideIfFound(overrides, propertyPath, o));
            }
            return newCollection;
        } else if (originalValue instanceof Map) {
            Map originalMap = ((Map) originalValue);
            Map<Object, Object> newMap = CollectionFactory.createApproximateMap(originalMap, originalMap.size());
            for (Object key : originalMap.keySet()) {
                newMap.put(key, replaceWithOverrideIfFound(overrides, propertyPath + "." + key, originalMap.get(key)));
            }
            return newMap;
        } else {
            Map props = OBJECT_MAPPER.convertValue(originalValue, Map.class);
            return replaceWithOverrideIfFound(overrides, propertyPath, props);

        }

    }
}
