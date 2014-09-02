package wikiboot.data;

import org.junit.Test;
import wikiboot.DataItem;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link wikiboot.data.YamlDataSet}.
 */
public class YamlDataSetTests {

    @Test
    public void load() {
        YamlDataSet dataSet = new YamlDataSet("wikiboot/data/test-dataset.yaml");
        assertThat(dataSet.getDataItems().size(), is(3));
        for (DataItem item : dataSet.getDataItems()) {
            assertThat(item.getModel().get("name"), is(notNullValue()));
        }
    }

}
