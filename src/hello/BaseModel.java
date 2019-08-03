package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
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
}
