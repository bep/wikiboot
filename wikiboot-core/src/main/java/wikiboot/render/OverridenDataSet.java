package wikiboot.render;

import org.javatuples.KeyValue;
import org.springframework.util.Assert;
import wikiboot.DataItem;
import wikiboot.DataSet;
import wikiboot.render.support.DataItemOverrideProvider;

import java.util.Map;

/**
 * Override certain key values in a {@link DataSet} with replacements.
 *
 * @author Bjørn Erik Pedersen
 */
public class OverridenDataSet extends DataSet {

    private final static DataItemOverrideProvider dataItemOverrideProvider = new DataItemOverrideProvider();

    public OverridenDataSet(Map<KeyValue<String, String>, String> overrides, DataSet dataSet) {
        Assert.notNull(dataSet, "dataSet must not be null");
        for (DataItem item : dataSet.getDataItems()) {
            this.dataItems.add(dataItemOverrideProvider.apply(overrides, item));
        }
    }
}
