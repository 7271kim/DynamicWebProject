package hello;

import java.util.HashMap;

public class BaseModel {
    private int seq;
    private String id;
    private String pw;
    private String name;
    private String email;
    private String address;
    private String gender;
    private String hpnumber;
    private String joinDate;
    private String joinUpDate;
    private String lastLogin;
    private String cancelLogin;
    private String outDate;
    private String emailToken;
    private String emailTokenDate;
    private int isActivity;
    private int loginCount;
    private int totalLoginCount;
    private int authorization;
    
    // 동적 SQL을 위한 필드
    private HashMap<String, String> where;
    private HashMap<String, String> whereNot;
    private HashMap<String, String> like;
    
    // 추후 가지고 오는 list 개수 설정위해 
    private int limitStart = 0;
    private int limitEnd;
	
	
    public int getSeq() {
        return seq;
    }
    public void setSeq(int seq) {
        this.seq = seq;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPw() {
        return pw;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getHpnumber() {
        return hpnumber;
    }
    public void setHpnumber(String hpnumber) {
        this.hpnumber = hpnumber;
    }
    public String getJoinDate() {
        return joinDate;
    }
    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }
    public String getJoinUpDate() {
        return joinUpDate;
    }
    public void setJoinUpDate(String joinUpDate) {
        this.joinUpDate = joinUpDate;
    }
    public String getLastLogin() {
        return lastLogin;
    }
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
    public String getCancelLogin() {
        return cancelLogin;
    }
    public void setCancelLogin(String cancelLogin) {
        this.cancelLogin = cancelLogin;
    }
    public String getOutDate() {
        return outDate;
    }
    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }
    public String getEmailToken() {
        return emailToken;
    }
    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }
    public String getEmailTokenDate() {
        return emailTokenDate;
    }
    public void setEmailTokenDate(String emailTokenDate) {
        this.emailTokenDate = emailTokenDate;
    }
    public int getIsActivity() {
        return isActivity;
    }
    public void setIsActivity(int isActivity) {
        this.isActivity = isActivity;
    }
    public int getLoginCount() {
        return loginCount;
    }
    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }
    public int getTotalLoginCount() {
        return totalLoginCount;
    }
    public void setTotalLoginCount(int totalLoginCount) {
        this.totalLoginCount = totalLoginCount;
    }
    public int getAuthorization() {
        return authorization;
    }
    public void setAuthorization(int authorization) {
        this.authorization = authorization;
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
