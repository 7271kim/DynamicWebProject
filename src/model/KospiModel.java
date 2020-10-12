package model;

import java.util.HashMap;

public class KospiModel {
    private String date;
    private String kospi;
    
    public KospiModel() {
    }
    
    public KospiModel( String date, String kospi ) {
        this.date = date;
        this.kospi = kospi;
    }
    
    // 동적 SQL을 위한 필드
    private HashMap<String, String> where;
    private HashMap<String, String> whereNot;
    private HashMap<String, String> like;
    
    // 추후 가지고 오는 list 개수 설정위해 
    private int limitStart = 0;
    private int limitEnd;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKospi() {
        return kospi;
    }

    public void setKospi(String kospi) {
        this.kospi = kospi;
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
