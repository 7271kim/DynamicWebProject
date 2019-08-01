package hello;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    @Autowired
    BaseService baseService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goMain() {
        return "header-common"; 
    }
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String goTT(BaseModel baseModel, Model model) {
        List<BaseModel> list = new ArrayList<BaseModel>();
        list = baseService.getMemList(baseModel);
        model.addAttribute("list", list);
        return "header-common"; 
    }
}
