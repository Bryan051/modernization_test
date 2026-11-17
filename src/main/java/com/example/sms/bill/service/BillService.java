package com.example.sms.bill.service;

import java.util.HashMap;
import java.util.List;

/**
 * 청구(세금계산서) 서비스
 */
public interface BillService {

    List<BillVO> selectBillList(HashMap<String, Object> param);

    BillVO selectBill(String billId);

    /**
     * 청구 발행 : 청구 저장 -> 전자세금계산서 발행 요청 -> 거래처 여신상태 변경
     * (생성된 청구ID 반환)
     */
    String issueBill(BillVO vo) throws Exception;
}
