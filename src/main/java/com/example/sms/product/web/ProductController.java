package com.example.sms.product.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sms.product.service.ProductService;
import com.example.sms.product.service.ProductVO;

/**
 * 제품 컨트롤러
 */
@Controller
public class ProductController {

    @Resource(name = "productService")
    private ProductService productService;

    @RequestMapping(value = "/product/selectProductList.do")
    @ResponseBody
    public Map<String, Object> selectProductList(@RequestParam HashMap<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", productService.selectProductList(param));
        return result;
    }

    @RequestMapping(value = "/product/selectProduct.do")
    @ResponseBody
    public ProductVO selectProduct(@RequestParam("productId") String productId) {
        return productService.selectProduct(productId);
    }

    @RequestMapping(value = "/product/insertProduct.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertProduct(ProductVO vo) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("productId", productService.insertProduct(vo));
        return result;
    }

    @RequestMapping(value = "/product/updateProduct.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateProduct(ProductVO vo) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("cnt", productService.updateProduct(vo));
        return result;
    }
}
