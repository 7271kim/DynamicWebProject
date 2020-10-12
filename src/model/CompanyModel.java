package model;

import java.util.HashMap;

public class CompanyModel {
    private String companyName;
    private String companyCode;
    private String date;
    private String todayPrice;
    private String todayUpdown;
    private String todayRate;
    private String forigin;
    private String forignPersent;
    private String tradingVolume;
    
    public CompanyModel() {}
    public CompanyModel(String companyName,String companyCode) {
        this.companyCode = companyCode;
        this.companyName = companyName;
    }
    public CompanyModel(String companyName,String companyCode,String date,String todayPrice,String todayUpdown,String todayRate,String forigin,String forignPersent, String tradingVolume) {
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.date = date;
        this.todayPrice = todayPrice;
        this.todayUpdown = todayUpdown;
        this.todayRate = todayRate;
        this.forigin = forigin;
        this.forignPersent = forignPersent;
        this.tradingVolume = tradingVolume;
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

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCompanyCode() {
        return companyCode;
    }
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTodayPrice() {
        return todayPrice;
    }
    public void setTodayPrice(String todayPrice) {
        this.todayPrice = todayPrice;
    }
    public String getTodayUpdown() {
        return todayUpdown;
    }
    public void setTodayUpdown(String todayUpdown) {
        this.todayUpdown = todayUpdown;
    }
    public String getTodayRate() {
        return todayRate;
    }
    public void setTodayRate(String todayRate) {
        this.todayRate = todayRate;
    }
    public String getForigin() {
        return forigin;
    }
    public void setForigin(String forigin) {
        this.forigin = forigin;
    }
    public String getForignPersent() {
        return forignPersent;
    }
    public void setForignPersent(String forignPersent) {
        this.forignPersent = forignPersent;
    }
    public String getTradingVolume() {
        return tradingVolume;
    }
    public void setTradingVolume(String tradingVolume) {
        this.tradingVolume = tradingVolume;
    }
    public HashMap<String, String> getWhere() {
        return where;
    }
    public void setWhere(HashMap<String, String> where) {
        this.where = where;
    }
    public HashMap<String, String> getWhereNot() {
        return whereNot;
    }
    public void setWhereNot(HashMap<String, String> whereNot) {
        this.whereNot = whereNot;
    }
    public HashMap<String, String> getLike() {
        return like;
    }
    public void setLike(HashMap<String, String> like) {
        this.like = like;
    }
    public int getLimitStart() {
        return limitStart;
    }
    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }
    public int getLimitEnd() {
        return limitEnd;
    }
    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
    
}
