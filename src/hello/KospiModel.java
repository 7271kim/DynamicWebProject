package hello;

import java.util.HashMap;

import lombok.Data;

@Data
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
}
