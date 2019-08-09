package hello;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.sun.org.apache.bcel.internal.generic.LUSHR;

@Controller
public class MainController {
    @Autowired
    BaseService baseService;
    @Autowired
    AsyncService asyncService;
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String goTest(Model model) {
        return "test"; 
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goMain(Model model) {
        KospiModel kospiModel           = new KospiModel();
        CompanyModel companyModel       = new CompanyModel();
        List<KospiModel> kospiValue     = new ArrayList<KospiModel>();
        Map<String, Map<String, Map<String, String>>> companyList = new HashMap<String, Map<String,Map<String,String>>>();
        
        List<CompanyModel> kospi200     = new ArrayList<CompanyModel>();
        kospi200 = baseService.getKospi200(companyModel);
        
        for( CompanyModel item : kospi200 ) {
            
            String companyCode = item.getCompanyCode();
            
            CompanyModel search       = new CompanyModel();
            HashMap<String, String> where = new HashMap<>();
            where.put("kospi200.COMPANYCODE",companyCode);
            search.setWhere(where);
            
            List<CompanyModel> tempList = new ArrayList<CompanyModel>();
            tempList = baseService.getTodayCompany(search);
            
            Map<String, Map<String, String>> companys = new HashMap<String, Map<String,String>>(); 
            
            for( CompanyModel tempCompany : tempList ) {
                Map<String, String> companyData = new HashMap<String, String>();
                companyData.put("companyName", tempCompany.getCompanyName());
                companyData.put("todayPrice", tempCompany.getTodayPrice());
                companyData.put("forigin", tempCompany.getForigin());
                companys.put(tempCompany.getDate(),companyData);
            }
            companyList.put(companyCode, companys);
        }

        kospiValue = baseService.getKospi(kospiModel);
        
        Map<String, String> kospiKeys = new HashMap<String, String>();
        List keysSort = new ArrayList<String>(0);
        kospi200 = baseService.getKospi200(companyModel);
        for( CompanyModel item : kospi200 ) {
            kospiKeys.put(item.getCompanyCode(), item.getCompanyName());
            keysSort.add(item.getCompanyCode());
        }
        List<String> dateSort           = new ArrayList<String>(companyList.get("005930").keySet());
        Collections.sort(dateSort);
        
        model.addAttribute("KospiValue", kospiValue);
        model.addAttribute("companyList", companyList);
        model.addAttribute("kospiKeys", kospiKeys);
        model.addAttribute("keysSort", keysSort);
        model.addAttribute("dateSort", dateSort);
        
        return "list"; 
    }
    
    @RequestMapping(value = "/showing", method = RequestMethod.GET)
    public String showing( Model model, KospiModel kospiModel ) {
        List<KospiModel> kospiValue = new ArrayList<KospiModel>();
        kospiValue = baseService.getKospi(kospiModel);
        model.addAttribute("KospiValue", kospiValue);
        
        return "showing"; 
    }
    
    @RequestMapping(value = "/companyData", method = RequestMethod.GET)
    public String showing( Model model, CompanyModel company ) {
        List<CompanyModel> list = new ArrayList<CompanyModel>();
        if(company.getCompanyCode() != null) {
        HashMap<String, String> where = new HashMap<>();
        where.put("kospi200.COMPANYCODE",company.getCompanyCode());
        company.setWhere(where);
        }
        list = baseService.getTodayCompany(company);
        model.addAttribute("list", list);
        
        return "companyData"; 
    }
    
    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String settings( Model model, KospiModel kospiModel ) {
        baseService.scheduled();
        return "showing"; 
    }
    
    private void settingTodayCompany() {
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy.MM.dd");
        Date today = new Date();
        String date = format.format(today);
        try {
            for(int index = 1; index <= 20; index++) {
                String urlKospi = "https://finance.naver.com/sise/entryJongmok.nhn?&page="+index;
                Document doc;
                doc = Jsoup.connect(urlKospi).get();
                Element table           = doc.getElementsByTag("table").get(0);
                Elements companyLow      = table.select("tbody tr");
                for( Element item : companyLow ) {
                    String companyName= "",companyCode= "",todayPrice= "",todayUpdown= "",todayRate = "";
                    if(!item.select(".ctg a").isEmpty()) {
                        Element companyElement = item.select(".ctg a").first();
                        companyName  = companyElement.text();
                        companyCode  = companyElement.attr("href").split("code=")[1];
                    }
                    if(!item.select(".number_2").isEmpty()) {
                        todayPrice = item.select(".number_2").first().text();
                    }
                    if(!item.select(".rate_down2 .tah").isEmpty()) {
                        Element todayUpdownElement = item.select(".rate_down2 .tah").first();
                        String number = todayUpdownElement.text().replaceAll(",", "");
                        todayUpdown = todayUpdownElement.hasAttr("red02") ? number : "-"+number;
                    }
                    if(!item.select(".number_2 .tah").isEmpty()) {
                        Element rateElement = item.select(".number_2 .tah").first();
                        todayRate         = rateElement.text().replaceAll("%", "");
                    }
                    if(companyCode!="") {
                        CompanyModel company = new CompanyModel( companyName, companyCode, date, todayPrice, todayUpdown, todayRate);
                        CompanyModel temp = getKospi200One(companyCode);
                        if( temp.getCompanyCode() == null ) {
                            baseService.settingKospi200(company);
                        } 
                        CompanyModel temp2 = getTodayCompanyOne(companyCode,date);
                        if( temp2.getCompanyCode() == null ) {
                            baseService.settingTodayCompany(company);
                        } else {
                            HashMap<String, String> where = new HashMap<>();
                            where.put("COMPANYCODE",companyCode);
                            where.put("DATE",date);
                            company.setWhere(where);
                            baseService.updateTodayCompany(company);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
   }
    
    private void settingsTodayForign() {
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy.MM.dd");
        Date today = new Date();
        String date = format.format(today);
        try {
            CompanyModel companyModel = new CompanyModel();
            HashMap<String, String> where = new HashMap<>();
            where.put("DATE",date);
            companyModel.setWhere(where);
            
            List<CompanyModel> list = new ArrayList<CompanyModel>();
            list = baseService.getTodayCompany(companyModel);
            
            for(int index = 0; index < list.size(); index++) {
                String companyCode = list.get(index).getCompanyCode();
                String url = "https://finance.naver.com/item/frgn.nhn?code="+companyCode;
                Document doc;
                doc = Jsoup.connect(url).get();
                if(doc.select(".inner_sub table") != null) {
                    Element table                     = doc.select(".inner_sub table").get(1);
                    Element todayKospiRow     = table.select("tbody tr").get(3);
                    String forigin = todayKospiRow.select("td").get(6).text();
                    String forignPersent = todayKospiRow.select("td").get(8).text();
                    CompanyModel temp = new CompanyModel();
                    temp.setForigin(forigin);
                    temp.setForignPersent(forignPersent);
                    where.put("COMPANYCODE",companyCode);
                    temp.setWhere(where);
                    baseService.updateTodayCompany(temp);
                }
            }
        } catch (Exception e) {
        }
   }
    
   private void settingsKospiToday() {
        try {
            String urlKospi = "https://finance.naver.com/sise/sise_index_day.nhn?code=KOSPI&page=1";
            Document doc;
            doc = Jsoup.connect(urlKospi).get();
            Element table           = doc.getElementsByTag("table").get(0);
            Element todayKospiRow   = table.select("tbody tr").get(2);
            String today            = todayKospiRow.getElementsByTag("td").get(0).text();
            String kospi            = todayKospiRow.getElementsByTag("td").get(1).text();
            KospiModel kospiToday = new KospiModel(today, kospi);
            KospiModel temp = getOne(today);
            if( temp.getDate() == null ) {
                baseService.settingKospi(kospiToday);
            } else {
                HashMap<String, String> where = new HashMap<>();
                where.put("DATE",today);
                kospiToday.setWhere(where);
                baseService.kospiUpdate(kospiToday);
            }
        } catch (Exception e) {
        }
     }
    
    private KospiModel getOne ( String date ) {
        HashMap<String, String> where = new HashMap<>();
        KospiModel bean = new KospiModel();
        where.put("DATE", date);
        bean.setWhere(where);
        List<KospiModel> lists = baseService.getKospi(bean);
        if(lists.size() > 0 ) {
            bean = lists.get(0);
        }
        
        return bean;
    }
    
    private CompanyModel getKospi200One ( String companyCode ) {
        HashMap<String, String> where = new HashMap<>();
        CompanyModel bean = new CompanyModel();
        where.put("COMPANYCODE", companyCode);
        bean.setWhere(where);
        List<CompanyModel> lists = baseService.getKospi200(bean);
        if(lists.size() > 0 ) {
            bean = lists.get(0);
        }
        
        return bean;
    }
    
    private CompanyModel getTodayCompanyOne ( String companyCode, String date ) {
        HashMap<String, String> where = new HashMap<>();
        CompanyModel bean = new CompanyModel();
        where.put("company.COMPANYCODE", companyCode);
        where.put("DATE", date);
        bean.setWhere(where);
        List<CompanyModel> lists = baseService.getTodayCompany(bean);
        if(lists.size() > 0 ) {
            bean = lists.get(0);
        }
        
        return bean;
    }
}
