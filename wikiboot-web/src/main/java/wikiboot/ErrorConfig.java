package wikiboot;

import cz.jirutka.spring.exhandler.RestHandlerExceptionResolver;
import cz.jirutka.spring.exhandler.RestHandlerExceptionResolverBuilder;
import cz.jirutka.spring.exhandler.support.HttpMessageConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import wikiboot.support.ErrorConstants;
import wikiboot.support.PathExcludingExceptionResolver;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Error configuration.
 *
 * @author Bjørn Erik Pedersen
 */
@Configuration
public class ErrorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SiteEnvironment siteEnvironment;


    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(exceptionHandlerExceptionResolver()); // resolves @ExceptionHandler
        resolvers.add(pathExcludingExceptionResolver());
        resolvers.add(restExceptionResolver());
    }

    @Bean
    public PathExcludingExceptionResolver pathExcludingExceptionResolver() {
        return new PathExcludingExceptionResolver(ErrorConstants.EXCEPTION_STATUS_CODE_MAPPING,
                Collections.singletonMap("siteEnv", this.siteEnvironment),
                "/api", "/manage");
    }

    @Bean
    public RestHandlerExceptionResolver restExceptionResolver() {
        RestHandlerExceptionResolverBuilder builder = RestHandlerExceptionResolver.builder();
        builder.messageSource(httpErrorMessageSource()).defaultContentType(MediaType.APPLICATION_JSON);

        for (Map.Entry<Class<? extends Exception>, HttpStatus> entry : ErrorConstants.EXCEPTION_STATUS_CODE_MAPPING.entrySet()) {
            builder.addErrorMessageHandler(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    @Bean
    public MessageSource httpErrorMessageSource() {
        ReloadableResourceBundleMessageSource m = new ReloadableResourceBundleMessageSource();
        m.setBasename("classpath:/wikiboot/errors/messages");
        m.setDefaultEncoding("UTF-8");
        return m;
    }

    @Bean
    public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver() {
        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
        resolver.setMessageConverters(HttpMessageConverterUtils.getDefaultHttpMessageConverters());
        return resolver;
    }
}
