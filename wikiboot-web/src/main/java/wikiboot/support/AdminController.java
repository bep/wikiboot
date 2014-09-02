package wikiboot.support;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Admin related services.
 *
 * @author Bjørn Erik Pedersen
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(method = RequestMethod.GET)
    String main(ModelMap model) {
        return "admin/index";
    }
}
