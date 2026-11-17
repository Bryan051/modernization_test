package com.example.sms.user.service;

import java.io.Serializable;

/**
 * 로그인 사용자 VO (세션에 "loginVO" 로 저장)
 */
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String userNm;
    private String passwd;
    private String deptId;
    private String deptNm;
    private String authCd;
    private String authNm;
    private String email;
    private String telNo;
    private String useYn;
    private String lastLoginDt;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserNm() { return userNm; }
    public void setUserNm(String userNm) { this.userNm = userNm; }
    public String getPasswd() { return passwd; }
    public void setPasswd(String passwd) { this.passwd = passwd; }
    public String getDeptId() { return deptId; }
    public void setDeptId(String deptId) { this.deptId = deptId; }
    public String getDeptNm() { return deptNm; }
    public void setDeptNm(String deptNm) { this.deptNm = deptNm; }
    public String getAuthCd() { return authCd; }
    public void setAuthCd(String authCd) { this.authCd = authCd; }
    public String getAuthNm() { return authNm; }
    public void setAuthNm(String authNm) { this.authNm = authNm; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelNo() { return telNo; }
    public void setTelNo(String telNo) { this.telNo = telNo; }
    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
    public String getLastLoginDt() { return lastLoginDt; }
    public void setLastLoginDt(String lastLoginDt) { this.lastLoginDt = lastLoginDt; }
}
