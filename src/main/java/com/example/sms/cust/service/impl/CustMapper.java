package com.example.sms.cust.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.sms.cust.service.CustVO;

/**
 * 거래처 매퍼 (mapper/cust/Cust_SQL.xml)
 */
@Mapper
public interface CustMapper {

    List<CustVO> selectCustList(HashMap<String, Object> param);

    int selectCustListCnt(HashMap<String, Object> param);

    CustVO selectCust(String custId);

    HashMap<String, Object> selectCustBizInfo(String custId);

    List<HashMap<String, Object>> selectCustContactList(String custId);

    HashMap<String, Object> selectCustMainContact(String custId);

    int insertCust(CustVO vo);

    int insertCustContact(HashMap<String, Object> param);

    int updateCust(CustVO vo);

    int updateCreditStatus(@Param("custId") String custId, @Param("creditStatus") String creditStatus);

    int updateLastNoticeDt(String custId);

    int deleteCust(String custId);
}
