package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    @Autowired
    BaseService baseService;
    @Autowired
    AsyncService asyncService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goMain() {
        return "header-common"; 
    }
    
    /* @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String goTT(BaseModel baseModel, Model model) {
        List<BaseModel> list = new ArrayList<BaseModel>();
        list = baseService.getMemList(baseModel);
        model.addAttribute("list", list);
        return "header-common"; 
    }*/
    
    @RequestMapping(value = "/parsing", method = RequestMethod.GET)
    public String getParsing(BaseModel baseModel, Model model) {
        baseModel = getTodayKospi(baseModel);
        baseModel = getKospi200Data(baseModel);
        baseModel = getForign(baseModel);
        
        model.addAttribute("baseModel", baseModel);
        model.addAttribute("keys", baseModel.getComPanyData().keySet());
        
        return "parsing"; 
    }
    
     private BaseModel getForign( BaseModel baseModel ) {
      try {
          if ( baseModel.getComPanyData() == null) return baseModel;
          for(String code : baseModel.getComPanyData().keySet()) {
              String url = "https://finance.naver.com/item/frgn.nhn?code="+code;
              Document doc;
              doc = Jsoup.connect(url).get();
              if(doc.select(".inner_sub table") != null) {
                  Element table           = doc.select(".inner_sub table").get(1);
                  Element todayKospiRow   = table.select("tbody tr").get(3);
                  CompanyModel thisCompany = baseModel.getComPanyData().get(code);
                  String forigin = todayKospiRow.select("td").get(5).text();
                  String forignPersent = todayKospiRow.select("td").get(8).text();
                  thisCompany.setForigin(forigin);
                  thisCompany.setForignPersent(forignPersent);
              }
          }
          
      } catch (Exception e) {
          baseModel.setError(e.toString());
      }
      
      return baseModel;
    }
    
    private BaseModel getTodayKospi( BaseModel baseModel ) {
      //오늘의 코스피
        try {
            String urlKospi = "https://finance.naver.com/sise/sise_index_day.nhn?code=KOSPI&page=1";
            Document doc;
            doc = Jsoup.connect(urlKospi).get();
            Element table           = doc.getElementsByTag("table").get(0);
            Element todayKospiRow   = table.select("tbody tr").get(2);
            String today            = todayKospiRow.getElementsByTag("td").get(0).text();
            String kospi            = todayKospiRow.getElementsByTag("td").get(1).text();
            baseModel.setKospi(kospi);
            baseModel.setToday(today);
        } catch (Exception e) {
            baseModel.setError(e.toString());
        }
        
        return baseModel;
    }
    
    private BaseModel getKospi200Data ( BaseModel baseModel ) {
        try {
            Map<String, CompanyModel> comPanyData = new HashMap<String, CompanyModel>();
            ArrayList<CompanyModel> kospi200List = new ArrayList<CompanyModel>();
            for(int index = 1; index <= 20; index++) {
                String urlKospi = "https://finance.naver.com/sise/entryJongmok.nhn?&page="+index;
                Document doc;
                doc = Jsoup.connect(urlKospi).get();
                Element table           = doc.getElementsByTag("table").get(0);
                Elements companyLow      = table.select("tbody tr");
                for( Element item : companyLow ) {
                    CompanyModel company = new CompanyModel();
                    if(!item.select(".ctg a").isEmpty()) {
                        Element companyElement = item.select(".ctg a").first();
                        String companyName  = companyElement.text();
                        String companyCode  = companyElement.attr("href").split("code=")[1];
                        company.setCompanyName(companyName);
                        company.setCompanyCode(companyCode);
                    }
                    if(!item.select(".number_2").isEmpty()) {
                        String todayPrice = item.select(".number_2").first().text();
                        company.setTodayPrice(todayPrice);
                    }
                    if(!item.select(".rate_down2 .tah").isEmpty()) {
                        Element todayUpdownElement = item.select(".rate_down2 .tah").first();
                        String number = todayUpdownElement.text().replaceAll(",", "");
                        String todayUpdown = todayUpdownElement.hasAttr("red02") ? number : "-"+number;
                        company.setTodayUpdown(todayUpdown);
                    }
                    if(!item.select(".number_2 .tah").isEmpty()) {
                        Element rateElement = item.select(".number_2 .tah").first();
                        String todayRate         = rateElement.text().replaceAll("%", "");
                        company.setTodayRate(todayRate);
                    }
                    if(company.getCompanyName() != null) {
                        comPanyData.put(company.getCompanyCode(), company);
                        kospi200List.add(company);
                    }
                }
            }
            baseModel.setComPanyData(comPanyData);
            baseModel.setKospi200List(kospi200List);
            
        } catch (Exception e) {
            baseModel.setError(e.toString());
        }
        
        return baseModel;
    }
}
