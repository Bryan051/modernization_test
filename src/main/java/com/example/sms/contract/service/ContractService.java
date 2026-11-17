package com.example.sms.contract.service;

import java.util.HashMap;
import java.util.List;

/**
 * 계약 서비스
 */
public interface ContractService {

    List<ContractVO> selectContractList(HashMap<String, Object> param);

    ContractVO selectContract(String contractId);

    /** 계약 생성 (생성된 계약ID 반환) */
    String createContract(ContractVO vo);

    int updateContractAmount(String contractId, long contractAmt);

    /** 10 진행 / 90 종료 */
    int updateContractStatus(String contractId, String statusCd);
}
