package com.example.sms.report.service;

import java.util.HashMap;
import java.util.List;

/**
 * 매출 집계 서비스 (대시보드 · 리포트)
 */
public interface SalesSummaryService {

    /** 거래처 / 수주 / 제품별 상세 매출 */
    List<HashMap<String, Object>> selectSalesSummary(HashMap<String, Object> param);

    /** 월별 수주 금액 (fromDt ~ toDt) */
    List<HashMap<String, Object>> selectMonthlySales(HashMap<String, Object> param);

    /** 수주 금액 상위 거래처 (topN 건) */
    List<HashMap<String, Object>> selectTopCustomerList(HashMap<String, Object> param);

    /** 제품별 판매 수량 · 금액 */
    List<HashMap<String, Object>> selectProductSales(HashMap<String, Object> param);
}
