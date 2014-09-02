package wikiboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.net.URI;

/**
 * Core specific configuration.
 *
 * @author Bjørn Erik Pedersen
 */
@Configuration
class CoreConfig {


}


@Configuration
class WikibootRepositoryRestConfiguration extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Template.class, Article.class, ArticleSet.class);
        config.setDefaultMediaType(MediaType.APPLICATION_JSON);
        config.setBaseUri(URI.create("/api"));
    }
}
