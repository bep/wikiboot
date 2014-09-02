package wikiboot.render;

import wikiboot.DataItem;
import wikiboot.DataSet;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Template for {@link DataSetRenderer}s.
 *
 * @author Bjørn Erik Pedersen
 */
public abstract class AbstractDataSetRenderer implements DataSetRenderer {

    @Override
    public List<Rendering> render(DataSet dataSet, String templateId) {
        return  dataSet.getDataItems().stream().map(dataItem -> doRender(dataItem, templateId)).collect(Collectors.toList());
    }

    protected abstract Rendering doRender(DataItem dataItem, String templateId);
}
