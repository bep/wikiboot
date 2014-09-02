package wikiboot.render.support;

import org.javatuples.KeyValue;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import wikiboot.DataItem;
import wikiboot.data.YamlDataSet;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("unchecked")
/**
 * Tests for {@link wikiboot.render.support.DataItemOverrideProvider}.
 */
public class DataItemOverrideProviderTests {

    private final DataItemOverrideProvider provider = new DataItemOverrideProvider();
    DataItem dataItem = new YamlDataSet("DataItemOverrideProviderTests.yaml").getDataItems().get(0);

    @Test
    public void apply() throws Exception {
        DataItem newItem = provider.apply(createOverrides(), dataItem);
        assertThat(newItem.getTitle(), is("some title"));
        assertThat(newItem.getModel().get("key"), is("newValue"));
        assertThat(newItem, not(sameInstance(dataItem)));

    }

    @Test
    public void applyWithList() throws Exception {
        DataItem newItem = provider.apply(createOverrides(), dataItem);
        assertThat(((List) newItem.getModel().get("list")).get(0), is("newValue"));
    }

    @Test
    public void applyWithNestedList() throws Exception {
        DataItem newItem = provider.apply(createOverrides(), dataItem);
        List nestedList = (List) newItem.getModel().get("nestedList");
        assertThat(nestedList.get(0), is("newValue"));
        nestedList.stream().filter(o -> o instanceof List).forEach(o -> {
            assertThat(((List) o).get(0), is("newValue"));
        });
    }

    @Test
    public void applyWithNestedObject() throws Exception {
        DataItem newItem = provider.apply(createOverrides(), dataItem);
        Map nestedObject = (Map) newItem.getModel().get("nestedObject");
        Map nested = (Map) nestedObject.get("nested");

        assertThat(nestedObject.get("v1"), is("newValue"));
        assertThat(nested.get("v2"), is("newValue"));
        assertThat(nested.get("v3"), is("otherValue"));

    }

    @Test
    public void applyWithSimpleBean() throws Exception {
        TestBean testBean = new TestBean();
        ModelMap model = new ModelMap("nestedObject", testBean);
        DataItem oldDataItem = new DataItem("title", model);
        DataItem newItem = provider.apply(createOverrides(), oldDataItem);

        Map updated = (Map) newItem.getModel().get("nestedObject");
        Map nested = (Map) updated.get("nested");
        List<String> vStringArray = (List<String>) updated.get("vStringArray");

        assertThat(vStringArray.get(0), is("newValue"));
        assertThat(updated.get("v1"), is("newValue"));
        assertThat(updated.get("v2"), is("oldValue"));
        assertThat(nested.get("v1"), is("oldValue"));
        assertThat(nested.get("v2"), is("newValue"));

    }

    @Test
    public void applyWithStringArray() throws Exception {
        String[] vStringArray = {"oldValue", "someOther"};
        ModelMap model = new ModelMap("key", vStringArray);
        DataItem oldDataItem = new DataItem("title", model);
        DataItem newItem = provider.apply(createOverrides(), oldDataItem);

        assertThat(newItem.getModel().get("key"), is(instanceOf(String[].class)));

        String[] strArr = (String[]) newItem.getModel().get("key");

        assertThat(strArr[0], is("newValue"));
        assertThat(strArr, not(sameInstance(vStringArray)));
    }

    @Test
    public void noOverrides() throws Exception {
        DataItem newItem = provider.apply(null, dataItem);
        assertThat(newItem, is(sameInstance(dataItem)));
        newItem = provider.apply(Collections.emptyMap(), dataItem);
        assertThat(newItem, is(sameInstance(dataItem)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void noDataItem() throws Exception {
        provider.apply(createOverrides(), null);

    }

    Map<KeyValue<String, String>, String> createOverrides() {
        Map<KeyValue<String, String>, String> map = new HashMap<>();
        map.put(new KeyValue<>("key", "oldValue"), "newValue");
        map.put(new KeyValue<>("list", "oldValue"), "newValue");
        map.put(new KeyValue<>("nestedList", "oldValue"), "newValue");
        map.put(new KeyValue<>("nestedObject.v1", "oldValue"), "newValue");
        map.put(new KeyValue<>("nestedObject.vStringArray", "oldValue"), "newValue");
        map.put(new KeyValue<>("nestedObject.nested.v2", "oldValue"), "newValue");
        return map;
    }


    public static class TestBeanBase {
        public String v1 = "oldValue", v2 = "oldValue";
        public int vInt = 32;
        public int[] vIntArray = {1, 2};
        public String[] vStringArray = {"oldValue", "someOther"};

        public TestBeanBase() {
        }

    }

    private static class TestBean extends TestBeanBase {
        public TestBeanBase nested = new TestBeanBase();

    }
}
