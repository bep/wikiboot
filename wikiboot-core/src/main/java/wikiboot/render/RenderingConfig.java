package wikiboot.render;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import wikiboot.render.freemarker.FreemarkerJpaTemplateLoader;

import java.util.Properties;

/**
 * Rendering related configuration.
 *
 * @author Bjørn Erik Pedersen
 */
@Configuration
public class RenderingConfig {

    private static final Logger logger = LoggerFactory.getLogger(RenderingConfig.class);

    @Autowired
    FreemarkerJpaTemplateLoader freemarkerJpaTemplateLoader;

    @Bean
    @Qualifier("render")
    public freemarker.template.Configuration renderFreeMarkerConfiguration() throws Exception {
        FreeMarkerConfigurationFactoryBean factory = new FreeMarkerConfigurationFactoryBean();

        Properties settings = new Properties();
        //settings.put("cache_storage", "freemarker.cache.NullCacheStorage");
        factory.setTemplateLoaderPath("classpath:/freemarker/");
        factory.setPreTemplateLoaders(freemarkerJpaTemplateLoader);
        factory.setPreferFileSystemAccess(false);
        factory.setFreemarkerSettings(settings);
        factory.afterPropertiesSet();
        freemarker.template.Configuration configuration = factory.getObject();
        logger.info("Config: " + configuration);
        return configuration;
    }
}
