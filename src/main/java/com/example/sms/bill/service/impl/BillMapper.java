package com.example.sms.bill.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.sms.bill.service.BillVO;

/**
 * 청구 매퍼 (mapper/bill/Bill_SQL.xml)
 */
@Mapper
public interface BillMapper {

    List<BillVO> selectBillList(HashMap<String, Object> param);

    BillVO selectBill(String billId);

    int insertBill(BillVO vo);

    /** 승인번호 저장 (issueStatus : 00 대기 / 10 발행 / 90 실패) */
    int updateBillEtax(@Param("billId") String billId, @Param("etaxNo") String etaxNo,
                       @Param("issueStatus") String issueStatus);

    /** 월마감 (closeYm = YYYYMM) */
    int updateCloseYn(HashMap<String, Object> param);
}
