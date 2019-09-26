package hello;

import java.util.HashMap;

import lombok.Data;

@Data
public class LottoModel {
    private String noDate = "15";
    private int totalGetLotto   = 5;
    private String noPick       = "";
    private String pickBefore   = "";
    private String checkOneStep    = "";
    private String checkTwoStep    = "";
    private String checkThreeStep  = "";
    private String checkFourStep   = "";
    private String checkFiveStep   = "";
    private String date;
    private String number;
    private String number_two;
    private String value;
    
 // 동적 SQL을 위한 필드
    private HashMap<String, String> where;
    private HashMap<String, String> whereNot;
    private HashMap<String, String> like;
    
    // 추후 가지고 오는 list 개수 설정위해 
    private int limitStart = 0;
    private int limitEnd;
}

