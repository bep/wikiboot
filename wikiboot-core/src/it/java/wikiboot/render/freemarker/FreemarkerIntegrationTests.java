package wikiboot.render.freemarker;

import freemarker.template.Configuration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import wikiboot.ResourceNotFound;
import wikiboot.Template;
import wikiboot.test.AbstractIntegrationTests;

import java.io.StringWriter;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@Transactional
public class FreemarkerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    @Qualifier("render")
    Configuration renderFreeMarkerConfiguration;

    @Autowired
    FreemarkerJpaTemplateLoader freemarkerJpaTemplateLoader;

    @Test
    public void getTemplate() throws Exception {

        Template template = createTemplate();

        freemarker.template.Template freemarkerTemplate = renderFreeMarkerConfiguration.getTemplate(String.valueOf(template.getId()));

        assertThat(freemarkerTemplate, not(nullValue()));

        StringWriter templateString = new StringWriter();
        freemarkerTemplate.dump(templateString);

        assertThat(templateString.toString(), is("some template"));
    }

    @Test
    @Transactional
    public void findTemplateSourceWithLocaleInId() throws Exception {
        Template template = createTemplate();
        String idWithLocale = template.getId() + "_" + Locale.US.toLanguageTag();
        System.err.println(idWithLocale);
        assertThat(freemarkerJpaTemplateLoader.findTemplateSource(String.valueOf(idWithLocale)), not(nullValue()));
    }

    @Test(expected = ResourceNotFound.class)
    public void templateNotFound() throws Exception {
        freemarkerJpaTemplateLoader.findTemplateSource("123467");
    }

    private Template createTemplate() {
        Template template = Template.from("some template");
        templateRepository.save(template);
        return template;
    }

}
