package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.LottoService;

@Controller
public class LottoController {
    
    @Autowired
    LottoService lottoService;
    
    private static Logger LOGGER = LogManager.getLogger(LottoController.class);
    
    @RequestMapping(value = "/lottoTotalUpdate.jin", method = RequestMethod.GET)
    public String getLottoUpdate( Model model ) {
        lottoService.totalLottoDataUpdate();
        model.addAttribute("result", "작업이 끝났습니다.");
        return "lottoUpdate"; 
    }

}
