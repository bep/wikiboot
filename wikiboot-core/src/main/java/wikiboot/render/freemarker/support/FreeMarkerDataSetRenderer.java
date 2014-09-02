package wikiboot.render.freemarker.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wikiboot.DataItem;
import wikiboot.render.AbstractDataSetRenderer;
import wikiboot.render.Rendering;

/**
 * {@link wikiboot.render.DataSetRenderer} for FreeMarker templates.
 *
 * @author Bjørn Erik Pedersen
 */
@Service
class FreeMarkerDataSetRenderer extends AbstractDataSetRenderer {

    @Autowired
    FreeMarkerDataItemRenderer freeMarkerDataItemRenderer;

    @Override
    protected Rendering doRender(DataItem dataItem, String templateId) {
        return freeMarkerDataItemRenderer.render(dataItem, templateId);
    }
}
