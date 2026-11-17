package com.example.sms.product.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.sms.cmm.util.SmsUtil;
import com.example.sms.product.service.ProductService;
import com.example.sms.product.service.ProductVO;

/**
 * 제품 · 단가 서비스 구현
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Resource(name = "productMapper")
    private ProductMapper productMapper;

    @Override
    public List<ProductVO> selectProductList(HashMap<String, Object> param) {
        return productMapper.selectProductList(param);
    }

    @Override
    public ProductVO selectProduct(String productId) {
        return productMapper.selectProduct(productId);
    }

    @Override
    public String insertProduct(ProductVO vo) {
        String strProductId = SmsUtil.genId("P");
        vo.setProductId(strProductId);
        productMapper.insertProduct(vo);

        // 기본단가도 기간별 단가 표에 한 줄 남긴다 (2020.02 기간별 단가 도입)
        HashMap<String, Object> price = new HashMap<String, Object>();
        price.put("productId", strProductId);
        price.put("unitPrice", vo.getUnitPrice());
        price.put("applyFromDt", SmsUtil.getToday("yyyy-MM-dd"));
        price.put("applyToDt", "9999-12-31");
        productMapper.insertProductPrice(price);
        return strProductId;
    }

    @Override
    public int updateProduct(ProductVO vo) {
        return productMapper.updateProduct(vo);
    }
}
