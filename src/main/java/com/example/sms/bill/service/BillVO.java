package com.example.sms.bill.service;

import java.io.Serializable;

/**
 * 청구(세금계산서) VO (TB_BILL)
 */
public class BillVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String billId;
    private String orderId;
    private String contractId;
    private String custId;
    private String custNm;
    private String billDt;
    private long supplyAmt;
    private long taxAmt;
    private long totalAmt;
    private String etaxNo;
    private String issueStatus;
    private String closeYn;
    private String closeYm;
    private String regDt;
    private String updDt;

    public String getBillId() { return billId; }
    public void setBillId(String billId) { this.billId = billId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getContractId() { return contractId; }
    public void setContractId(String contractId) { this.contractId = contractId; }
    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }
    public String getCustNm() { return custNm; }
    public void setCustNm(String custNm) { this.custNm = custNm; }
    public String getBillDt() { return billDt; }
    public void setBillDt(String billDt) { this.billDt = billDt; }
    public long getSupplyAmt() { return supplyAmt; }
    public void setSupplyAmt(long supplyAmt) { this.supplyAmt = supplyAmt; }
    public long getTaxAmt() { return taxAmt; }
    public void setTaxAmt(long taxAmt) { this.taxAmt = taxAmt; }
    public long getTotalAmt() { return totalAmt; }
    public void setTotalAmt(long totalAmt) { this.totalAmt = totalAmt; }
    public String getEtaxNo() { return etaxNo; }
    public void setEtaxNo(String etaxNo) { this.etaxNo = etaxNo; }
    public String getIssueStatus() { return issueStatus; }
    public void setIssueStatus(String issueStatus) { this.issueStatus = issueStatus; }
    public String getCloseYn() { return closeYn; }
    public void setCloseYn(String closeYn) { this.closeYn = closeYn; }
    public String getCloseYm() { return closeYm; }
    public void setCloseYm(String closeYm) { this.closeYm = closeYm; }
    public String getRegDt() { return regDt; }
    public void setRegDt(String regDt) { this.regDt = regDt; }
    public String getUpdDt() { return updDt; }
    public void setUpdDt(String updDt) { this.updDt = updDt; }
}
