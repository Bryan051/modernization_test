package com.example.sms.order.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sms.bill.service.BillService;
import com.example.sms.bill.service.BillVO;
import com.example.sms.cmm.util.SmsUtil;
import com.example.sms.contract.service.ContractService;
import com.example.sms.contract.service.ContractVO;
import com.example.sms.order.service.OrderService;
import com.example.sms.order.service.OrderVO;
import com.example.sms.product.service.impl.ProductMapper;
import com.example.sms.quote.service.impl.QuoteMapper;

/**
 * 수주 서비스 구현
 *
 * <pre>
 *   수정일        수정자      수정내용
 *  ----------   --------   ---------------------------
 *  2016.07.11   김철수      최초 생성
 *  2019.08.22   홍길동      MyBatis 로 이전
 *  2019.11.05   김철수      수주 완료 처리 추가 (고객 요청으로 급히 추가)
 *  2020.02.10   김철수      기간별 단가 적용 (기준일 = 수주일)
 * </pre>
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private QuoteMapper quoteMapper;

    @Autowired
    private ProductMapper productMapper;

    @Resource(name = "contractService")
    private ContractService contractService;

    @Resource(name = "billService")
    private BillService billService;

    @Override
    public List<OrderVO> selectOrderList(HashMap<String, Object> param) {
        if (param.get("sortCol") == null) {
            param.put("sortCol", "O.ORDER_DT");
        }
        if (param.get("sortDir") == null) {
            param.put("sortDir", "DESC");
        }
        return orderMapper.selectOrderList(param);
    }

    @Override
    public int selectOrderListCnt(HashMap<String, Object> param) {
        return orderMapper.selectOrderListCnt(param);
    }

    @Override
    public OrderVO selectOrder(String orderId) {
        OrderVO vo = orderMapper.selectOrder(orderId);
        if (vo != null) {
            vo.setItemList(orderMapper.selectOrderItemList(orderId));
        }
        return vo;
    }

    @Override
    public String insertOrder(OrderVO vo) {
        String orderId = SmsUtil.genId("OD");
        vo.setOrderId(orderId);
        vo.setOrderDt(SmsUtil.getToday("yyyy-MM-dd"));

        // 품목 단가는 제품 단가표에서 조회해서 채운다
        long lOrderAmt = 0L;
        int iSeq = 1;
        List<HashMap<String, Object>> itemList = vo.getItemList();
        if (itemList != null) {
            for (HashMap<String, Object> item : itemList) {
                String strProductId = (String) item.get("productId");
                Long unitPrice = productMapper.selectUnitPrice(strProductId, vo.getOrderDt());
                if (unitPrice == null) {
                    throw new IllegalArgumentException("단가가 없는 제품입니다 : " + strProductId);
                }
                int iQty = SmsUtil.toInt(item.get("qty"), 1);
                long lAmt = unitPrice.longValue() * iQty;
                item.put("orderId", orderId);
                item.put("itemSeq", iSeq++);
                item.put("qty", iQty);
                item.put("unitPrice", unitPrice);
                item.put("amt", lAmt);
                lOrderAmt += lAmt;
            }
        }
        vo.setOrderAmt(lOrderAmt);

        orderMapper.insertOrder(vo);
        if (itemList != null) {
            for (HashMap<String, Object> item : itemList) {
                orderMapper.insertOrderItem(item);
            }
        }
        return orderId;
    }

    /**
     * 수주 완료 : 계약 생성 -> 청구 발행 -> 수주 상태 · 견적 종결
     */
    @Override
    @Transactional
    public void completeOrder(String orderId, String userId) throws Exception {
        OrderVO order = orderMapper.selectOrder(orderId);
        if (order == null) {
            throw new IllegalArgumentException("수주 정보가 없습니다 : " + orderId);
        }
        if (!"접수".equals(order.getOrderStatus())) {
            throw new IllegalStateException("이미 처리된 수주입니다 : " + order.getOrderStatus());
        }

        // 1. 계약 생성 (기간 1년)
        ContractVO contract = new ContractVO();
        contract.setOrderId(orderId);
        contract.setCustId(order.getCustId());
        contract.setContractNm(order.getCustNm() + " 계약");
        contract.setContractAmt(order.getOrderAmt());
        contract.setStartDt(SmsUtil.getToday("yyyy-MM-dd"));
        contract.setEndDt(SmsUtil.addDays(contract.getStartDt(), 365));
        String contractId = contractService.createContract(contract);

        // 2. 청구 발행
        BillVO bill = new BillVO();
        bill.setOrderId(orderId);
        bill.setContractId(contractId);
        bill.setCustId(order.getCustId());
        bill.setSupplyAmt(order.getOrderAmt());
        billService.issueBill(bill);

        // 3. 수주 상태 변경, 견적 종결
        orderMapper.updateOrderStatus(orderId, "청구완료");
        if (order.getQuoteId() != null) {
            quoteMapper.updateQuoteStatus(order.getQuoteId(), "90");
        }
        // FIXME: 처리자(userId) 이력 남기기
    }

    @Override
    public int updateOrderAmount(String orderId, long orderAmt) {
        return orderMapper.updateOrderAmount(orderId, orderAmt);
    }
}
