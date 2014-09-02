package wikiboot.support;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Main entry point for application.
 *
 * @author Bjørn Erik Pedersen
 */
@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping(method = RequestMethod.GET)
    String main(ModelMap model) {
        return "main";
    }

}
