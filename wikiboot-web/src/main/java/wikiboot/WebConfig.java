package wikiboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import wikiboot.support.ProjectUtils;

import javax.servlet.Filter;

/**
 * Web app config.
 *
 * @author Bjørn Erik Pedersen
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    private static final String[] STATIC_SUFFIXES = {"html", "js", "png", "jpg", "css", "less", "js.map"};
    private static final String[] STATIC_RESOURCE_PATTERNS = new String[STATIC_SUFFIXES.length];

    static {
        for (int i = 0; i < STATIC_SUFFIXES.length; i++) {
            STATIC_RESOURCE_PATTERNS[i] = "/**/*." + STATIC_SUFFIXES[i];
        }
    }

    @Autowired
    private SiteEnvironment siteEnvironment;

    @Value("${project.version:}")
    private String appVersion;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        if (siteEnvironment.isDevelopment()) {
            String location = "file:///" + ProjectUtils.getProjectHome() + "/wikiboot-client/src/";
            logger.info("Set up client resources for development to location " + location);

            VersionResourceResolver versionResolver = new VersionResourceResolver()
                   .addFixedVersionStrategy("dev", "/**/*.js")
                   .addContentVersionStrategy("/**");

            registry.addResourceHandler(STATIC_RESOURCE_PATTERNS).addResourceLocations(location)
                    .setCachePeriod(0)
                    .resourceChain(false).addResolver(versionResolver);
        } else {
            logger.info("Set up client resources for production");
            String location = "classpath:static/";

            VersionResourceResolver versionResolver = new VersionResourceResolver()
                    .addFixedVersionStrategy(this.appVersion, "/**/*.js")
                    .addContentVersionStrategy("/**");

            registry.addResourceHandler(STATIC_RESOURCE_PATTERNS).addResourceLocations(location)
                    .resourceChain(true)
                    .addResolver(versionResolver);
        }
    }


    @Bean
    public Cache resourceResolverCache() {
        return new ConcurrentMapCache("resource-resolver-cache", false);
    }

    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

}


