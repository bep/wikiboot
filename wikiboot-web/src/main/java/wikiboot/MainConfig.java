package wikiboot;

import com.google.common.cache.CacheBuilder;
import org.h2.server.web.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import wikiboot.mediawiki.support.MediaWikiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Main config.
 *
 * @author Bjørn Erik Pedersen
 */
@Configuration
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class, WebMvcAutoConfiguration.class})
@EnableCaching
@ComponentScan
public class MainConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<ConcurrentMapCache> cacheList = new ArrayList<>();
        cacheList.add(createConcurrentMapCache(MediaWikiClient.CACHE_TTL, MediaWikiClient.CACHE_NAME, 1000));
        cacheManager.setCaches(cacheList);
        return cacheManager;
    }

    private ConcurrentMapCache createConcurrentMapCache(Long timeToLive, String name, long cacheSize) {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().expireAfterWrite(timeToLive, TimeUnit.SECONDS);

        if (cacheSize >= 0) {
            cacheBuilder.maximumSize(cacheSize);
        }
        ConcurrentMap<Object, Object> map = cacheBuilder.build().asMap();
        return new ConcurrentMapCache(name, map, false);
    }

    @Bean
    @Profile("development")
    public ServletRegistrationBean h2Console() {
        ServletRegistrationBean reg = new ServletRegistrationBean(new WebServlet(), "/console/*");
        reg.setLoadOnStartup(1);
        return reg;
    }


}

@Component
class DispatcherServletConfigurer implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServletConfigurer.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof DispatcherServlet) {
            logger.info("Configure servlet " + beanName);
            ((DispatcherServlet) bean).setThrowExceptionIfNoHandlerFound(true);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}