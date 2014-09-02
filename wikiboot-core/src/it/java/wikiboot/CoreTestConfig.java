package wikiboot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Todo - move this to some test-util.
 * Todo - this is included if tests are run with MainConfig (component scan)
 *
 * @author Bjørn Erik Pedersen
 */

@Configuration("mainConfig")
@ComponentScan(excludeFilters={
        @ComponentScan.Filter(type= FilterType.REGEX, pattern = ".*WebConfig|.*MainConfig")})
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class, WebMvcAutoConfiguration.class, FreeMarkerAutoConfiguration.class})
public class CoreTestConfig {
}
