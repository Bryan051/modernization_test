package com.example.sms.quote.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 견적 VO (TB_QUOTE + 품목 목록)
 */
public class QuoteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String quoteId;
    private String opptyId;
    private String custId;
    private String custNm;
    private String quoteNm;
    private String quoteDt;
    private String validDt;
    private long totalAmt;
    private String statusCd;
    private String chargeUserId;
    private String regDt;
    private String updDt;

    /** 품목 (productId, qty 를 받아서 서비스에서 단가 · 금액을 채움) */
    private List<HashMap<String, Object>> itemList;

    public String getQuoteId() { return quoteId; }
    public void setQuoteId(String quoteId) { this.quoteId = quoteId; }
    public String getOpptyId() { return opptyId; }
    public void setOpptyId(String opptyId) { this.opptyId = opptyId; }
    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }
    public String getCustNm() { return custNm; }
    public void setCustNm(String custNm) { this.custNm = custNm; }
    public String getQuoteNm() { return quoteNm; }
    public void setQuoteNm(String quoteNm) { this.quoteNm = quoteNm; }
    public String getQuoteDt() { return quoteDt; }
    public void setQuoteDt(String quoteDt) { this.quoteDt = quoteDt; }
    public String getValidDt() { return validDt; }
    public void setValidDt(String validDt) { this.validDt = validDt; }
    public long getTotalAmt() { return totalAmt; }
    public void setTotalAmt(long totalAmt) { this.totalAmt = totalAmt; }
    public String getStatusCd() { return statusCd; }
    public void setStatusCd(String statusCd) { this.statusCd = statusCd; }
    public String getChargeUserId() { return chargeUserId; }
    public void setChargeUserId(String chargeUserId) { this.chargeUserId = chargeUserId; }
    public String getRegDt() { return regDt; }
    public void setRegDt(String regDt) { this.regDt = regDt; }
    public String getUpdDt() { return updDt; }
    public void setUpdDt(String updDt) { this.updDt = updDt; }
    public List<HashMap<String, Object>> getItemList() { return itemList; }
    public void setItemList(List<HashMap<String, Object>> itemList) { this.itemList = itemList; }
}
