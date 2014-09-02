package wikiboot;

import java.util.ArrayList;
import java.util.List;

/**
 * A data set used to create articles.
 *
 * @author Bjørn Erik Pedersen
 */
public abstract class DataSet {

    protected final List<DataItem> dataItems;

    protected DataSet() {
        this.dataItems = new ArrayList<>();
    }

    public DataSet(List<DataItem> dataItems) {
        this.dataItems = dataItems;
    }

    public List<DataItem> getDataItems() {
        return dataItems;
    }

}


