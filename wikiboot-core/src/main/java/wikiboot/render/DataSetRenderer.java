package wikiboot.render;

import wikiboot.DataSet;

import java.util.List;

/**
 * Render a {@link DataSet} for a given template.
 *
 * @author Bjørn Erik Pedersen
 */
public interface DataSetRenderer {

    List<Rendering> render(DataSet dataSet, String templateId);
}
