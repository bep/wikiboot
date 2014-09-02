package wikiboot.render.freemarker.support;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import wikiboot.DataItem;
import wikiboot.render.DataItemRenderer;
import wikiboot.render.Rendering;

import java.io.StringWriter;

/**
 * A {@link wikiboot.render.DataItemRenderer} that uses FreeMarker as template engine.
 *
 * @author Bjørn Erik Pedersen
 */
@Component
class FreeMarkerDataItemRenderer implements DataItemRenderer {

    Logger logger = LoggerFactory.getLogger(FreeMarkerDataItemRenderer.class);

    @Autowired
    @Qualifier("render")
    Configuration renderFreeMarkerConfiguration;

    @Override
    public Rendering render(DataItem dataItem, String templateName) {
        return process(dataItem, templateName);
    }

    private Rendering process(DataItem dataItem, String templateName) {
        Template template;

        try {
            template = renderFreeMarkerConfiguration.getTemplate(templateName);
            StringWriter sw = new StringWriter();
            template.process(dataItem.getModel(), sw);
            return sw::toString;
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe.getMessage());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to process FreeMarker template", e);
        }


    }
}