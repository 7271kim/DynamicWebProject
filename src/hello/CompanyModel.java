package hello;

import java.util.HashMap;

import lombok.Data;

@Data
public class CompanyModel {
    private String companyName;
    private String companyCode;
    private String date;
    private String todayPrice;
    private String todayUpdown;
    private String todayRate;
    private String forigin;
    private String forignPersent;
    
    public CompanyModel() {}
    public CompanyModel(String companyName,String companyCode) {
        this.companyCode = companyCode;
        this.companyName = companyName;
    }
    
    public CompanyModel(String companyName,String companyCode,String date,String todayPrice,String todayUpdown,String todayRate) {
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.date = date;
        this.todayPrice = todayPrice;
        this.todayUpdown = todayUpdown;
        this.todayRate = todayRate;
    }
    
    public CompanyModel(String companyName,String companyCode,String date,String todayPrice,String todayUpdown,String todayRate,String forigin,String forignPersent) {
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.date = date;
        this.todayPrice = todayPrice;
        this.todayUpdown = todayUpdown;
        this.todayRate = todayRate;
        this.forigin = forigin;
        this.forignPersent = forignPersent;
    }
    
    public Boolean isEmty() {
        return companyName == "" || companyName == null;
    }
    
    // 동적 SQL을 위한 필드
    private HashMap<String, String> where;
    private HashMap<String, String> whereNot;
    private HashMap<String, String> like;
    
    // 추후 가지고 오는 list 개수 설정위해 
    private int limitStart = 0;
    private int limitEnd;
}
