package wikiboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Global controller advice.
 *
 * @author Bjørn Erik Pedersen
 */
@ControllerAdvice
public class SiteAdvice {

    @Autowired
    private SiteEnvironment siteEnvironment;

    @ModelAttribute("siteEnv")
    SiteEnvironment getSiteEnvironment() {
        return this.siteEnvironment;
    }

}
