package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaseModel {
    private String kospi;
    private String today;
    private String error;
    private Map<String, CompanyModel> comPanyData;
    private ArrayList<CompanyModel> kospi200List;
    
    // 동적 SQL을 위한 필드
    private HashMap<String, String> where;
    private HashMap<String, String> whereNot;
    private HashMap<String, String> like;
    
    // 추후 가지고 오는 list 개수 설정위해 
    private int limitStart = 0;
    private int limitEnd;
    public String getKospi() {
        return kospi;
    }
    public void setKospi(String kospi) {
        this.kospi = kospi;
    }
    public String getToday() {
        return today;
    }
    public void setToday(String today) {
        this.today = today;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public Map<String, CompanyModel> getComPanyData() {
        return comPanyData;
    }
    public void setComPanyData(Map<String, CompanyModel> comPanyData) {
        this.comPanyData = comPanyData;
    }
    public ArrayList<CompanyModel> getKospi200List() {
        return kospi200List;
    }
    public void setKospi200List(ArrayList<CompanyModel> kospi200List) {
        this.kospi200List = kospi200List;
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
