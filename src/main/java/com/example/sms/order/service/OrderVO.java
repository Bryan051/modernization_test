package com.example.sms.order.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 수주 VO (TB_ORDER + 품목 목록)
 */
public class OrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderId;
    private String quoteId;
    private String custId;
    private String custNm;
    private String orderDt;
    private long orderAmt;
    private String orderStatus;
    private String contractId;
    private String closeYn;
    private String closeYm;
    private String chargeUserId;
    private String regDt;
    private String updDt;

    /** 품목 (productId, qty 를 받아서 서비스에서 단가 · 금액을 채움) */
    private List<HashMap<String, Object>> itemList;

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getQuoteId() { return quoteId; }
    public void setQuoteId(String quoteId) { this.quoteId = quoteId; }
    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }
    public String getCustNm() { return custNm; }
    public void setCustNm(String custNm) { this.custNm = custNm; }
    public String getOrderDt() { return orderDt; }
    public void setOrderDt(String orderDt) { this.orderDt = orderDt; }
    public long getOrderAmt() { return orderAmt; }
    public void setOrderAmt(long orderAmt) { this.orderAmt = orderAmt; }
    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
    public String getContractId() { return contractId; }
    public void setContractId(String contractId) { this.contractId = contractId; }
    public String getCloseYn() { return closeYn; }
    public void setCloseYn(String closeYn) { this.closeYn = closeYn; }
    public String getCloseYm() { return closeYm; }
    public void setCloseYm(String closeYm) { this.closeYm = closeYm; }
    public String getChargeUserId() { return chargeUserId; }
    public void setChargeUserId(String chargeUserId) { this.chargeUserId = chargeUserId; }
    public String getRegDt() { return regDt; }
    public void setRegDt(String regDt) { this.regDt = regDt; }
    public String getUpdDt() { return updDt; }
    public void setUpdDt(String updDt) { this.updDt = updDt; }
    public List<HashMap<String, Object>> getItemList() { return itemList; }
    public void setItemList(List<HashMap<String, Object>> itemList) { this.itemList = itemList; }
}
