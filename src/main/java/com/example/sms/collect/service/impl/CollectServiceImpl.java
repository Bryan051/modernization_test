package com.example.sms.collect.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.cmm.util.SmsUtil;
import com.example.sms.collect.service.CollectService;
import com.example.sms.cust.service.impl.CustMapper;
import com.example.sms.order.service.impl.OrderMapper;

/**
 * 수금 · 미수 서비스 구현
 *
 * <pre>
 *   수정일        수정자      수정내용
 *  ----------   --------   ---------------------------
 *  2017.03.13   홍길동      최초 생성
 *  2019.08.22   홍길동      MyBatis 로 이전
 *  2020.04.27   홍길동      수금 후 수주 상태 · 거래처 여신상태 반영 (고객 요청)
 * </pre>
 */
@Service("collectService")
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CustMapper custMapper;

    @Override
    public List<HashMap<String, Object>> selectCollectList(HashMap<String, Object> param) {
        return collectMapper.selectCollectList(param);
    }

    @Override
    public List<HashMap<String, Object>> selectArrearsList(HashMap<String, Object> param) {
        return collectMapper.selectArrearsList(param);
    }

    @Override
    public int insertCollect(HashMap<String, Object> param) {
        param.put("collectId", SmsUtil.genId("CL"));
        if (param.get("collectDt") == null) {
            param.put("collectDt", SmsUtil.getToday("yyyy-MM-dd"));
        }
        int iCnt = collectMapper.insertCollect(param);

        String strBillId = (String) param.get("billId");
        String strOrderId = (String) param.get("orderId");
        String strCustId = (String) param.get("custId");

        // 청구 건을 다 받았으면 수주 상태를 '수금완료' 로
        Long balance = collectMapper.selectBillBalance(strBillId);
        if (balance != null && balance.longValue() <= 0L) {
            orderMapper.updateOrderStatus(strOrderId, "수금완료");
        }

        // 거래처 미수 합계에 따라 여신상태 변경 (미수 없음 01 / 미수 있음 02)
        Long arrears = collectMapper.selectCustArrearsAmt(strCustId);
        String strCreditStatus = (arrears == null || arrears.longValue() <= 0L) ? "01" : "02";
        custMapper.updateCreditStatus(strCustId, strCreditStatus);
        return iCnt;
    }
}
