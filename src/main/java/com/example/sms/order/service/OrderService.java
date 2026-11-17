package com.example.sms.order.service;

import java.util.HashMap;
import java.util.List;

/**
 * 수주 서비스
 */
public interface OrderService {

    List<OrderVO> selectOrderList(HashMap<String, Object> param);

    int selectOrderListCnt(HashMap<String, Object> param);

    /** 수주 + 품목 */
    OrderVO selectOrder(String orderId);

    /** 수주 등록 (생성된 수주ID 반환) */
    String insertOrder(OrderVO vo);

    /** 수주 완료 처리 : 계약 생성 -> 청구 발행 -> 견적 종결 */
    void completeOrder(String orderId, String userId) throws Exception;

    /** 수주 금액 변경 */
    int updateOrderAmount(String orderId, long orderAmt);
}
