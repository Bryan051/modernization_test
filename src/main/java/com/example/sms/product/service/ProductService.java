package com.example.sms.product.service;

import java.util.HashMap;
import java.util.List;

/**
 * 제품 · 단가 서비스
 */
public interface ProductService {

    List<ProductVO> selectProductList(HashMap<String, Object> param);

    ProductVO selectProduct(String productId);

    /** 제품 등록 (기본단가를 기간별 단가 표에도 함께 남긴다) */
    String insertProduct(ProductVO vo);

    int updateProduct(ProductVO vo);
}
