package hello;

import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements BaseService {
    @Autowired
    BaseDao baseDao;
    
    @Override
    public List<KospiModel> getKospi(KospiModel kospiModel) {
        return baseDao.getKospi(kospiModel);
    }

    @Override
    public void settingKospi(KospiModel kospi) {
        baseDao.settingKospi(kospi);
    }

    @Override
    public void kospiUpdate(KospiModel kospi) {
        baseDao.kospiUpdate(kospi);
    }

    @Override
    public List<CompanyModel> getKospi200(CompanyModel company) {
        return baseDao.getKospi200(company);
    }

    @Override
    public List<CompanyModel> getTodayCompany(CompanyModel company) {
        return baseDao.getTodayCompany(company);
    }

    @Override
    public void settingKospi200(CompanyModel company) {
        baseDao.settingKospi200(company);
    }

    @Override
    public void settingTodayCompany(CompanyModel company) {
        baseDao.settingTodayCompany(company);
    }

    @Override
    public void updateTodayCompany(CompanyModel company) {
        baseDao.updateTodayCompany(company);
    }

    @Override
    public void updateKospi200(CompanyModel company) {
        baseDao.updateKospi200(company);
    }

    @Override
    public List<LottoModel> getLotto(LottoModel lottoModel) {
        return baseDao.getLotto(lottoModel);
    }
    @Override
    public List<LottoModel> getLottoDetail(LottoModel lottoModel) {
        return baseDao.getLottoDetail(lottoModel);
    }
    

    @org.springframework.scheduling.annotation.Scheduled(cron="0 0 22 * * MON-FRI")
    @Override
    public void scheduled() {
        settingsKospi100();
        settingsKospiToday();
        settingTodayCompany();
    }
    private void settingsKospi100() {
        try {
            for(int index = 1; index <= 10; index++) {
                String urlKospi = "https://finance.naver.com/sise/entryJongmok.nhn?&page="+index;
                Document doc;
                doc = Jsoup.connect(urlKospi).get();
                Element table           = doc.getElementsByTag("table").get(0);
                Elements companyLow      = table.select("tbody tr");
                for( Element item : companyLow ) {
                    String companyName= "",companyCode= "";
                    if(!item.select(".ctg a").isEmpty()) {
                        Element companyElement = item.select(".ctg a").first();
                        companyName  = companyElement.text();
                        System.out.println(companyName);
                        companyCode  = companyElement.attr("href").split("code=")[1];
                    }
                    if(companyCode!="") {
                        CompanyModel company = new CompanyModel( companyName, companyCode);
                        CompanyModel temp = getKospi200One(companyCode);
                        if( temp.getCompanyCode() == null ) {
                            settingKospi200(company);
                        } 
                    }
                }
            }
        } catch (Exception e) {
        }
   }
    
    private void settingTodayCompany() {
        CompanyModel companyModel = new CompanyModel();
        List<CompanyModel> companyList = getKospi200(companyModel);
        try {
            for (CompanyModel compay : companyList) {
                String companyCode = compay.getCompanyCode();
                String companyName = compay.getCompanyName();
                String url = "https://finance.naver.com/item/frgn.nhn?code="+companyCode;
                Document doc;
                doc = Jsoup.connect(url).get();
                if(doc.select(".inner_sub table") != null) {
                    Element table                     = doc.select(".inner_sub table").get(1);
                    Element todayKospiRow   = table.select("tbody tr").get(3);
                    String todayDate        = todayKospiRow.select("td").get(0).text();
                    String todayPrice       = todayKospiRow.select("td").get(1).text();
                    String number           = todayKospiRow.select("td").get(2).text().replaceAll(",", "");
                    String todayUpdown      = todayKospiRow.select("td").get(2).hasAttr("red02") ? number : "-"+number;
                    String todayRate        = todayKospiRow.select("td").get(3).text().replaceAll("%", "");
                    String tradingVolume    = todayKospiRow.select("td").get(4).text().replaceAll(",", "");
                    String forigin = todayKospiRow.select("td").get(6).text();
                    String forignPersent = todayKospiRow.select("td").get(8).text();
                    CompanyModel thisCompany = new CompanyModel(companyName, companyCode, todayDate, todayPrice, todayUpdown, todayRate, forigin, forignPersent,tradingVolume);
                    CompanyModel temp = getTodayCompanyOne(companyCode, todayDate);
                    if( temp.isEmty() ) {
                        System.out.println(thisCompany.getCompanyName()=="");
                        settingTodayCompany(thisCompany);
                    }else {
                        HashMap<String, String> where = new HashMap<>();
                        where.put("COMPANYCODE",companyCode);
                        where.put("DATE",todayDate);
                        thisCompany.setWhere(where);
                        updateTodayCompany(thisCompany);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
   private void settingsKospiToday() {
        try {
            for(int index = 2; index <= 4; index++){
                String urlKospi = "https://finance.naver.com/sise/sise_index_day.nhn?code=KOSPI&page=1";
                Document doc;
                doc = Jsoup.connect(urlKospi).get();
                Element table           = doc.getElementsByTag("table").get(0);
                Element todayKospiRow   = table.select("tbody tr").get(index);
                String today            = todayKospiRow.getElementsByTag("td").get(0).text();
                String kospi            = todayKospiRow.getElementsByTag("td").get(1).text();
                KospiModel kospiToday = new KospiModel(today, kospi);
                KospiModel temp = getOne(today);
                if( temp.getDate() == null ) {
                    settingKospi(kospiToday);
                } else {
                    HashMap<String, String> where = new HashMap<>();
                    where.put("DATE",today);
                    kospiToday.setWhere(where);
                    kospiUpdate(kospiToday);
                }
            }
        } catch (Exception e) {
        }
     }
    
    private KospiModel getOne ( String date ) {
        HashMap<String, String> where = new HashMap<>();
        KospiModel bean = new KospiModel();
        where.put("DATE", date);
        bean.setWhere(where);
        List<KospiModel> lists = getKospi(bean);
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
        List<CompanyModel> lists = getKospi200(bean);
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
        List<CompanyModel> lists = getTodayCompany(bean);
        if(lists.size() > 0 ) {
            bean = lists.get(0);
        }
        
        return bean;
    }

}
