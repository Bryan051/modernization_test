package com.example.sms.contract.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.sms.contract.service.ContractVO;

/**
 * 계약 매퍼 (mapper/contract/Contract_SQL.xml)
 */
@Mapper
public interface ContractMapper {

    List<ContractVO> selectContractList(HashMap<String, Object> param);

    ContractVO selectContract(String contractId);

    int insertContract(ContractVO vo);

    int updateContractAmount(@Param("contractId") String contractId, @Param("contractAmt") long contractAmt);

    int updateContractStatus(@Param("contractId") String contractId, @Param("statusCd") String statusCd);
}
