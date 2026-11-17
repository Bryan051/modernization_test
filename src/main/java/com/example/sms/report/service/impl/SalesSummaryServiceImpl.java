package com.example.sms.report.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.cmm.util.SmsUtil;
import com.example.sms.report.service.SalesSummaryService;

/**
 * 매출 집계 서비스 구현
 *
 * 2021.03.15 김철수  최초 생성 (리포트 추가)
 * 2021.07.02 김철수  제품별 조회 추가
 */
@Service("salesSummaryService")
public class SalesSummaryServiceImpl implements SalesSummaryService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public List<HashMap<String, Object>> selectSalesSummary(HashMap<String, Object> param) {
        return reportMapper.selectSalesSummary(param);
    }

    @Override
    public List<HashMap<String, Object>> selectMonthlySales(HashMap<String, Object> param) {
        if (param.get("fromDt") == null) {
            param.put("fromDt", SmsUtil.getToday("yyyy") + "-01-01");
        }
        if (param.get("toDt") == null) {
            param.put("toDt", SmsUtil.getToday("yyyy-MM-dd"));
        }
        return reportMapper.selectMonthlySales(param);
    }

    @Override
    public List<HashMap<String, Object>> selectTopCustomerList(HashMap<String, Object> param) {
        if (param.get("topN") == null) {
            param.put("topN", 10);
        }
        return reportMapper.selectTopCustomerList(param);
    }

    @Override
    public List<HashMap<String, Object>> selectProductSales(HashMap<String, Object> param) {
        return reportMapper.selectProductSales(param);
    }
}
