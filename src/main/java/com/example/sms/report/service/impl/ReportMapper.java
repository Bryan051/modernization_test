package com.example.sms.report.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 매출 집계 매퍼 (mapper/report/Report_SQL.xml)
 */
@Mapper
public interface ReportMapper {

    List<HashMap<String, Object>> selectSalesSummary(HashMap<String, Object> param);

    List<HashMap<String, Object>> selectMonthlySales(HashMap<String, Object> param);

    List<HashMap<String, Object>> selectTopCustomerList(HashMap<String, Object> param);

    List<HashMap<String, Object>> selectProductSales(HashMap<String, Object> param);
}
