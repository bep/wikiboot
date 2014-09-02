package wikiboot.render;

import wikiboot.DataItem;

/**
 * Renders a {@link wikiboot.DataItem}.
 *
 * @author Bjørn Erik Pedersen
 */
public interface DataItemRenderer {
    Rendering render(DataItem dataItem, String templateName);
}
