package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goMain() {
        return "header-common"; 
    }
    
    @RequestMapping(value = "/tt", method = RequestMethod.GET)
    public String goTT() {
        return "header-common"; 
    }
}
