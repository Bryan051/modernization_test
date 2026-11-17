package com.example.sms.order.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.sms.order.service.OrderVO;

/**
 * 수주 매퍼 (mapper/order/Order_SQL.xml)
 */
@Mapper
public interface OrderMapper {

    List<OrderVO> selectOrderList(HashMap<String, Object> param);

    int selectOrderListCnt(HashMap<String, Object> param);

    OrderVO selectOrder(String orderId);

    List<HashMap<String, Object>> selectOrderItemList(String orderId);

    int insertOrder(OrderVO vo);

    int insertOrderItem(HashMap<String, Object> item);

    /** 접수 / 계약완료 / 청구완료 / 수금완료 / 취소 */
    int updateOrderStatus(@Param("orderId") String orderId, @Param("orderStatus") String orderStatus);

    int updateOrderContractId(@Param("orderId") String orderId, @Param("contractId") String contractId);

    int updateOrderAmount(@Param("orderId") String orderId, @Param("orderAmt") long orderAmt);

    /** 월마감 (closeYm = YYYYMM) */
    int updateCloseYn(HashMap<String, Object> param);
}
