package com.example.sms.contract.service;

import java.io.Serializable;

/**
 * 계약 VO (TB_CONTRACT)
 */
public class ContractVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String contractId;
    private String orderId;
    private String custId;
    private String custNm;
    private String contractNm;
    private long contractAmt;
    private String startDt;
    private String endDt;
    private String statusCd;
    private String regDt;
    private String updDt;

    public String getContractId() { return contractId; }
    public void setContractId(String contractId) { this.contractId = contractId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }
    public String getCustNm() { return custNm; }
    public void setCustNm(String custNm) { this.custNm = custNm; }
    public String getContractNm() { return contractNm; }
    public void setContractNm(String contractNm) { this.contractNm = contractNm; }
    public long getContractAmt() { return contractAmt; }
    public void setContractAmt(long contractAmt) { this.contractAmt = contractAmt; }
    public String getStartDt() { return startDt; }
    public void setStartDt(String startDt) { this.startDt = startDt; }
    public String getEndDt() { return endDt; }
    public void setEndDt(String endDt) { this.endDt = endDt; }
    public String getStatusCd() { return statusCd; }
    public void setStatusCd(String statusCd) { this.statusCd = statusCd; }
    public String getRegDt() { return regDt; }
    public void setRegDt(String regDt) { this.regDt = regDt; }
    public String getUpdDt() { return updDt; }
    public void setUpdDt(String updDt) { this.updDt = updDt; }
}
