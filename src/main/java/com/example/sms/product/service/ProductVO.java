package com.example.sms.product.service;

import java.io.Serializable;

/**
 * 제품 VO (TB_PRODUCT)
 */
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String productId;
    private String productNm;
    private String productType;
    private String unitCd;
    private long unitPrice;
    private String useYn;

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getProductNm() { return productNm; }
    public void setProductNm(String productNm) { this.productNm = productNm; }
    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }
    public String getUnitCd() { return unitCd; }
    public void setUnitCd(String unitCd) { this.unitCd = unitCd; }
    public long getUnitPrice() { return unitPrice; }
    public void setUnitPrice(long unitPrice) { this.unitPrice = unitPrice; }
    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
}
