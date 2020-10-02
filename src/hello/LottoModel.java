package hello;

import java.util.HashMap;

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
    public String getNoDate() {
        return noDate;
    }
    public void setNoDate(String noDate) {
        this.noDate = noDate;
    }
    public int getTotalGetLotto() {
        return totalGetLotto;
    }
    public void setTotalGetLotto(int totalGetLotto) {
        this.totalGetLotto = totalGetLotto;
    }
    public String getNoPick() {
        return noPick;
    }
    public void setNoPick(String noPick) {
        this.noPick = noPick;
    }
    public String getPickBefore() {
        return pickBefore;
    }
    public void setPickBefore(String pickBefore) {
        this.pickBefore = pickBefore;
    }
    public String getCheckOneStep() {
        return checkOneStep;
    }
    public void setCheckOneStep(String checkOneStep) {
        this.checkOneStep = checkOneStep;
    }
    public String getCheckTwoStep() {
        return checkTwoStep;
    }
    public void setCheckTwoStep(String checkTwoStep) {
        this.checkTwoStep = checkTwoStep;
    }
    public String getCheckThreeStep() {
        return checkThreeStep;
    }
    public void setCheckThreeStep(String checkThreeStep) {
        this.checkThreeStep = checkThreeStep;
    }
    public String getCheckFourStep() {
        return checkFourStep;
    }
    public void setCheckFourStep(String checkFourStep) {
        this.checkFourStep = checkFourStep;
    }
    public String getCheckFiveStep() {
        return checkFiveStep;
    }
    public void setCheckFiveStep(String checkFiveStep) {
        this.checkFiveStep = checkFiveStep;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getNumber_two() {
        return number_two;
    }
    public void setNumber_two(String number_two) {
        this.number_two = number_two;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
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

