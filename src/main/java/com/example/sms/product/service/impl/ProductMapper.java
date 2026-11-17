package com.example.sms.product.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.sms.product.service.ProductVO;

/**
 * 제품 · 단가 매퍼 (mapper/product/Product_SQL.xml)
 */
@Mapper
public interface ProductMapper {

    List<ProductVO> selectProductList(HashMap<String, Object> param);

    ProductVO selectProduct(String productId);

    /** 기준일(yyyy-MM-dd) 현재 적용 단가 */
    Long selectUnitPrice(@Param("productId") String productId, @Param("baseDt") String baseDt);

    int insertProduct(ProductVO vo);

    int insertProductPrice(HashMap<String, Object> param);

    int updateProduct(ProductVO vo);
}
