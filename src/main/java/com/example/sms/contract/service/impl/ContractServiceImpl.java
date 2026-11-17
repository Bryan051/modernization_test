package com.example.sms.contract.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.cmm.util.SmsUtil;
import com.example.sms.contract.service.ContractService;
import com.example.sms.contract.service.ContractVO;
import com.example.sms.order.service.OrderService;
import com.example.sms.order.service.impl.OrderMapper;

/**
 * 계약 서비스 구현
 *
 * <pre>
 *   수정일        수정자      수정내용
 *  ----------   --------   ---------------------------
 *  2016.08.01   홍길동      최초 생성
 *  2019.08.22   홍길동      MyBatis 로 이전
 *  2020.09.14   김철수      계약 금액 변경 시 수주 금액 반영
 * </pre>
 */
@Service("contractService")
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Resource(name = "orderService")
    private OrderService orderService;

    @Override
    public List<ContractVO> selectContractList(HashMap<String, Object> param) {
        return contractMapper.selectContractList(param);
    }

    @Override
    public ContractVO selectContract(String contractId) {
        return contractMapper.selectContract(contractId);
    }

    @Override
    public String createContract(ContractVO vo) {
        String contractId = SmsUtil.genId("CT");
        vo.setContractId(contractId);
        contractMapper.insertContract(vo);

        // 수주에 계약번호를 달고 상태를 '계약완료' 로 바꾼다
        orderMapper.updateOrderContractId(vo.getOrderId(), contractId);
        return contractId;
    }

    @Override
    public int updateContractAmount(String contractId, long contractAmt) {
        int iCnt = contractMapper.updateContractAmount(contractId, contractAmt);

        // 계약 금액이 바뀌면 수주 금액도 같이 맞춘다
        ContractVO vo = contractMapper.selectContract(contractId);
        if (vo != null) {
            orderService.updateOrderAmount(vo.getOrderId(), contractAmt);
        }
        return iCnt;
    }

    @Override
    public int updateContractStatus(String contractId, String statusCd) {
        return contractMapper.updateContractStatus(contractId, statusCd);
    }
}
