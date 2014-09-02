package wikiboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Convenient accessor methods to environment variables.
 *
 * @author Bjørn Erik Pedersen
 */
@Component
public class SiteEnvironment {

    @Autowired
    private Environment env;

    @Value("${project.dev.use-less-js:false}")
    private boolean useLessJs;


    public boolean isDevelopment() {
        return this.env.acceptsProfiles("development");
    }

    public boolean isIntegrationTest() {
        return this.env.acceptsProfiles("integrationTest");
    }

    public boolean isUseLessJs() {
        return isDevelopment() && this.useLessJs;
    }

    public boolean isUseLessCompiler() {
        return isDevelopment() && !this.useLessJs;
    }

    public boolean isUseLiveReload() {
        return isDevelopment() && !isIntegrationTest();
    }

}
