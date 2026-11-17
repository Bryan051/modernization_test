package com.example.sms.cust.service;

import java.util.HashMap;
import java.util.List;

/**
 * 거래처 서비스
 */
public interface CustService {

    List<CustVO> selectCustList(HashMap<String, Object> param);

    int selectCustListCnt(HashMap<String, Object> param);

    CustVO selectCust(String custId);

    /** @deprecated 2018.03 selectCust 로 대체 */
    @Deprecated
    String selectCustNm(String custId);

    /** 거래처 등록 (생성된 거래처ID 반환) */
    String insertCust(CustVO vo);

    int updateCust(CustVO vo);

    int deleteCust(String custId);

    List<HashMap<String, Object>> selectCustContactList(String custId);

    int insertCustContact(HashMap<String, Object> param);
}
