package hello;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    @Autowired
    BaseService baseService;
    @Autowired
    AsyncService asyncService;
    
    @RequestMapping(value = "/lotto.jin", method = RequestMethod.GET)
    public String getLotto(Model model, LottoModel lottoModel) {
        Map<String, String> totalNopick = new HashMap<String, String>();
        String error    = "";
        try {
            String[] noData = lottoModel.getNoPick() != "" ?lottoModel.getNoPick().split(",") : new String[0];
            for (String item : noData) {
                totalNopick.put(item, item);
            }
            List<LottoModel> lastLotto = new ArrayList<LottoModel>();
            lastLotto = baseService.getLotto(lottoModel);
            
            // 최근 번호 및 최근 번호랑 관련성 높은 번호들 제거
            if(lastLotto.size() > 0) {
                String [] lastLottoNumbers = lastLotto.get(0).getNumber().split(" ");
                for (String numberThis : lastLottoNumbers) {
                    totalNopick.put(numberThis,numberThis);
                    LottoModel temp = new LottoModel();
                    HashMap<String, String> where = new HashMap<>();
                    where.put("NUMBER", numberThis);
                    temp.setWhere(where);
                    List<LottoModel> historys = baseService.getLottoDetail(temp);
                    for (LottoModel check : historys) {
                        int value = Integer.parseInt(check.getValue());
                        String numberTwo = check.getNumber_two();
                        if( value > 20) {
                            totalNopick.put(numberTwo,numberTwo);
                        }
                    }
                }
            }
            
            // 과거 특정 횟수동안 나오지 않은 번호 제거
            int getNodate = Integer.parseInt(lottoModel.getNoDate());
            Map<String, String> histroryPick = new HashMap<String, String>();
            if(getNodate > 5) {
                for(int index = 1; index <= 45; index++) {
                    String cast = String.valueOf(index);
                    histroryPick.put(cast, cast);
                }
                lottoModel.setLimitEnd(Integer.parseInt(lottoModel.getNoDate()));
                List<LottoModel> historys  = baseService.getLotto(lottoModel);
                for (LottoModel thisItem : historys) {
                    String [] datas = thisItem.getNumber().split(" ");
                    for (String numberThis : datas) {
                        histroryPick.remove( numberThis );
                    }
                }
                for( String item : histroryPick.keySet() ) {
                    totalNopick.put(item,item);
                }
            }
            
            
            int totalGetLotto = lottoModel.getTotalGetLotto() ;
            String[] pickBefore = lottoModel.getPickBefore()!= ""? lottoModel.getPickBefore().split(",") : new String[0];
            
            // 구간별 Pick
            int check_1_10  = lottoModel.getCheckOneStep() == "" ? 0: Integer.parseInt(lottoModel.getCheckOneStep());
            int check_11_20 = lottoModel.getCheckTwoStep()== "" ? 0: Integer.parseInt(lottoModel.getCheckTwoStep());
            int check_21_30 = lottoModel.getCheckThreeStep()== "" ? 0: Integer.parseInt(lottoModel.getCheckThreeStep());
            int check_31_40 = lottoModel.getCheckFourStep()== "" ? 0: Integer.parseInt(lottoModel.getCheckFourStep());
            int check_41_45 = lottoModel.getCheckFiveStep()== "" ? 0: Integer.parseInt(lottoModel.getCheckFiveStep());
            
            ArrayList<String> totalLotto = new ArrayList<String>();
            

            while(totalLotto.size() < totalGetLotto) {
                int totoal      = check_1_10+check_11_20+check_21_30+check_31_40+check_41_45;
                if( pickBefore.length < 7 && totoal < 7) {
                    ArrayList<Integer> result = new ArrayList<Integer>();
                    Map<Integer, Integer> lottoNumber = new HashMap<Integer, Integer>();
                    ArrayList<Integer> check = new ArrayList<Integer>(Arrays.asList(check_1_10,check_11_20,check_21_30,check_31_40,check_41_45));
                    
                    for(int index = 1; index <= 45; index++) {
                        if(!totalNopick.containsKey(String.valueOf(index))) lottoNumber.put(index, index);
                    }
                    
                    for( String before : pickBefore ) {
                        if(before != "") {
                            int cast = Integer.parseInt(before);
                            result.add(cast);
                            lottoNumber.remove(cast);
                        }
                    }
                    
                    ArrayList<Integer> checkRange = new ArrayList<Integer>(Arrays.asList(check_1_10,check_11_20,check_21_30,check_31_40,check_41_45));
                    
                    // 군집별 작성
                    for( int item = 0; item < checkRange.size(); item++ ) {
                        int temp = checkRange.get(item);
                        
                        while( temp > 0 ) {
                            int tempNumber = randomRange(item*10+1, item*10+10); // 군집별 변호 생성
                            if(lottoNumber.containsKey(tempNumber)) {
                                lottoNumber.remove(tempNumber);
                                result.add(tempNumber);
                                temp--;
                            }
                            // 군집 제거 2인경우 0이되었을 경우 해당 군집이 제거되어야 함
                            if( temp == 0 ) {
                                for(int removeIndex = 1; removeIndex <= 10; removeIndex++) {
                                    int index = (tempNumber-1)/10;
                                    lottoNumber.remove(index*10 + removeIndex);
                                }
                            }
                        }
                    }
                    
                    while( result.size() < 6 ){
                        int tempNumber = randomRange(1, 45);
                        if(lottoNumber.containsKey(tempNumber)) {
                            result.add( tempNumber);
                            lottoNumber.remove(tempNumber);
                        }
                    }
                    
                    Collections.sort(result);
                    totalLotto.add(StringUtils.join(result,"&nbsp;&nbsp;&nbsp;&nbsp;"));
                } else {
                    error = "조건이 이상합니다. 조건을 확인해주세요";
                    System.out.println("조건이 이상합니다. 조건을 확인해주세요");
                    break;
                }
            }
            Map<Integer, Integer> sortTotalNo = new HashMap<Integer, Integer>();
            for (String key : totalNopick.keySet()) {
                int temp = Integer.parseInt(totalNopick.get(key));
                sortTotalNo.put(temp,temp);
                
            }
            ArrayList<Integer> sortedKeys = new ArrayList<Integer>(sortTotalNo.keySet());
            Collections.sort(sortedKeys);
            model.addAttribute("totalLotto", totalLotto);
            model.addAttribute("lottoModel", lottoModel);
            model.addAttribute("totalNopick", sortedKeys);
            model.addAttribute("noPick", lottoModel.getNoPick());
            model.addAttribute("lastLotto", lastLotto.get(0).getNumber());
            model.addAttribute("checkOneStep", check_1_10);
            model.addAttribute("checkTwoStep", check_11_20);
            model.addAttribute("checkThreeStep", check_21_30);
            model.addAttribute("checkFourStep", check_31_40);
            model.addAttribute("checkFiveStep", check_41_45);
            model.addAttribute("histroryPick", histroryPick);
            model.addAttribute("noDate", lottoModel.getNoDate());
            model.addAttribute("pickBefore", lottoModel.getPickBefore());
            model.addAttribute("totalGetLotto", lottoModel.getTotalGetLotto());
        } catch (Exception e) {
            error = e.toString();
        }
        model.addAttribute("error", error);
        
        return "lotto"; 
    }
    
    @RequestMapping(value = "/test.jin", method = RequestMethod.GET)
    public String goTest(Model model) {
        return "test"; 
    }
    
    @RequestMapping(value = "/company.jin", method = RequestMethod.GET)
    public String goMain(Model model) {
        KospiModel kospiModel           = new KospiModel();
        CompanyModel companyModel       = new CompanyModel();
        companyModel.setLimitEnd(100);
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
                companyData.put("tradingVolume", tempCompany.getTradingVolume());
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
    
    /* @RequestMapping(value = "/showing", method = RequestMethod.GET)
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
    }*/
    
    @RequestMapping(value = "/settings.jin", method = RequestMethod.GET)
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
    private static int randomRange(int n1, int n2) {
        return (int) (Math.random() * (n2 - n1 + 1)) + n1;
    }
}
