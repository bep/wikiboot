package wikiboot.render.freemarker;

import freemarker.cache.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wikiboot.ResourceNotFound;
import wikiboot.Template;
import wikiboot.render.TemplateSource;
import wikiboot.support.TemplateRepository;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * {@link freemarker.cache.TemplateLoader} loading templates via JPA.
 *
 * @author Bjørn Erik Pedersen
 */
@Component
public class FreemarkerJpaTemplateLoader implements TemplateLoader {

    private static final Logger logger = LoggerFactory.getLogger(FreemarkerJpaTemplateLoader.class);

    @Autowired
    private TemplateRepository templateRepository;

    @Override
    public Object findTemplateSource(String name) throws IOException {

        // Strip locale for now
        String trimmedName = name.contains("_") ? name.substring(0, name.indexOf("_")) : name;
        Template template = templateRepository.findOne(Long.valueOf(trimmedName));
        if (template == null) {
            logger.warn("No template found with id " + name);
            throw new ResourceNotFound("template", name);
        }
        return new TemplateSource(template.getUuid(), template.getContent(), System.currentTimeMillis()); // todo
    }

    @Override
    public long getLastModified(Object templateSource) {
        return ((TemplateSource) templateSource).getLastModified();
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        TemplateSource template = (TemplateSource) templateSource;
        return new StringReader(template.getContent());
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {

    }
}
