package wikiboot.render.freemarker;

import freemarker.cache.MultiTemplateLoader;
import freemarker.template.Configuration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import wikiboot.Template;
import wikiboot.test.AbstractIntegrationTests;

import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class FreemarkerJpaTemplateLoaderIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    @Qualifier("render")
    Configuration renderFreeMarkerConfiguration;


    @Test
    @Transactional
    public void getTemplate() throws Exception {

        assertThat(renderFreeMarkerConfiguration.getTemplateLoader(), instanceOf(MultiTemplateLoader.class));

        Template template = Template.from("some template");
        templateRepository.save(template);

        freemarker.template.Template freemarkerTemplate = renderFreeMarkerConfiguration.getTemplate(String.valueOf(template.getId()));

        assertThat(freemarkerTemplate, not(nullValue()));

        StringWriter templateString = new StringWriter();
        freemarkerTemplate.dump(templateString);

        assertThat(templateString.toString(), is("some template"));

    }
}