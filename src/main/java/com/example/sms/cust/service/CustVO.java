package com.example.sms.cust.service;

import java.io.Serializable;

/**
 * 거래처 VO (TB_CUSTOMER)
 */
public class CustVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String custId;
    private String custNm;
    private String bizNo;
    private String ceoNm;
    private String addr;
    private String addrDtl;
    private String telNo;
    private String custGrade;
    private String custGradeNm;
    private String creditStatus;
    private long creditLimit;
    private String chargeUserId;
    private String lastNoticeDt;
    private String useYn;
    private String regUserId;
    private String regDt;
    private String updDt;

    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }
    public String getCustNm() { return custNm; }
    public void setCustNm(String custNm) { this.custNm = custNm; }
    public String getBizNo() { return bizNo; }
    public void setBizNo(String bizNo) { this.bizNo = bizNo; }
    public String getCeoNm() { return ceoNm; }
    public void setCeoNm(String ceoNm) { this.ceoNm = ceoNm; }
    public String getAddr() { return addr; }
    public void setAddr(String addr) { this.addr = addr; }
    public String getAddrDtl() { return addrDtl; }
    public void setAddrDtl(String addrDtl) { this.addrDtl = addrDtl; }
    public String getTelNo() { return telNo; }
    public void setTelNo(String telNo) { this.telNo = telNo; }
    public String getCustGrade() { return custGrade; }
    public void setCustGrade(String custGrade) { this.custGrade = custGrade; }
    public String getCustGradeNm() { return custGradeNm; }
    public void setCustGradeNm(String custGradeNm) { this.custGradeNm = custGradeNm; }
    public String getCreditStatus() { return creditStatus; }
    public void setCreditStatus(String creditStatus) { this.creditStatus = creditStatus; }
    public long getCreditLimit() { return creditLimit; }
    public void setCreditLimit(long creditLimit) { this.creditLimit = creditLimit; }
    public String getChargeUserId() { return chargeUserId; }
    public void setChargeUserId(String chargeUserId) { this.chargeUserId = chargeUserId; }
    public String getLastNoticeDt() { return lastNoticeDt; }
    public void setLastNoticeDt(String lastNoticeDt) { this.lastNoticeDt = lastNoticeDt; }
    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
    public String getRegUserId() { return regUserId; }
    public void setRegUserId(String regUserId) { this.regUserId = regUserId; }
    public String getRegDt() { return regDt; }
    public void setRegDt(String regDt) { this.regDt = regDt; }
    public String getUpdDt() { return updDt; }
    public void setUpdDt(String updDt) { this.updDt = updDt; }
}
