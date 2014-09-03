package wikiboot.data;

import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
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

    @Test
    public void textLiteral() {
        ByteArrayResource resource = new ByteArrayResource("test".getBytes());
        YamlDataSet dataSet = new YamlDataSet(resource);

        assertThat(dataSet.getDataItems().size(), is(1));

        DataItem dataItem = dataSet.getDataItems().get(0);

        assertThat(dataItem.getModel().size(), is(1));
        assertThat(dataItem.getModel().get("document"), is("test"));
    }

    @Test
    public void nonStringKey() {
        ByteArrayResource resource = new ByteArrayResource("1: value".getBytes());
        YamlDataSet dataSet = new YamlDataSet(resource);

        assertThat(dataSet.getDataItems().size(), is(1));

        DataItem dataItem = dataSet.getDataItems().get(0);

        assertThat(dataItem.getModel().size(), is(1));
        assertThat(dataItem.getModel().get("[1]"), is("value"));
    }

    @Test
    public void emptyDocument() {
        ByteArrayResource resource = new ByteArrayResource("---".getBytes());
        YamlDataSet dataSet = new YamlDataSet(resource);
        assertThat(dataSet.getDataItems().size(), is(0));
    }

    @Test(expected = IllegalStateException.class)
    public void dataSetNotFound() {
        new YamlDataSet("doesNotExist.yaml");

    }


}
