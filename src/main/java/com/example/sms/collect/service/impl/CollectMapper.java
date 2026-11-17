package com.example.sms.collect.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 수금 · 미수 매퍼 (mapper/collect/Collect_SQL.xml)
 */
@Mapper
public interface CollectMapper {

    List<HashMap<String, Object>> selectCollectList(HashMap<String, Object> param);

    List<HashMap<String, Object>> selectArrearsList(HashMap<String, Object> param);

    int insertCollect(HashMap<String, Object> param);

    /** 청구 한 건의 남은 금액 */
    Long selectBillBalance(String billId);

    /** 거래처 전체 미수 합계 */
    Long selectCustArrearsAmt(String custId);
}
