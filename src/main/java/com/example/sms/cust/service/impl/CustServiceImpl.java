package com.example.sms.cust.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.sms.cmm.util.SmsUtil;
import com.example.sms.cust.service.CustService;
import com.example.sms.cust.service.CustVO;

/**
 * 거래처 서비스 구현
 *
 * <pre>
 *   수정일        수정자      수정내용
 *  ----------   --------   ---------------------------
 *  2016.03.14   홍길동      최초 생성
 *  2019.08.22   홍길동      MyBatis 로 이전
 *  2019.11.05   김철수      고객 요청으로 신용상태 조회 급히 추가
 * </pre>
 */
@Service("custService")
public class CustServiceImpl implements CustService {

    @Resource(name = "custMapper")
    private CustMapper custMapper;

    @Override
    public List<CustVO> selectCustList(HashMap<String, Object> param) {
        if (param.get("sortCol") == null) {
            param.put("sortCol", "C.CUST_NM");
        }
        if (param.get("sortDir") == null) {
            param.put("sortDir", "ASC");
        }
        List<CustVO> list = custMapper.selectCustList(param);
        for (CustVO vo : list) {
            // 건마다 코드명을 조회한다 (목록이 길면 느림)
            vo.setCustGradeNm(SmsUtil.getCodeName("CUST_GRADE", vo.getCustGrade()));
        }
        return list;
    }

    @Override
    public int selectCustListCnt(HashMap<String, Object> param) {
        int iCnt = custMapper.selectCustListCnt(param);
        return iCnt;
    }

    @Override
    public CustVO selectCust(String custId) {
        return custMapper.selectCust(custId);
    }

    @Deprecated
    @Override
    public String selectCustNm(String custId) {
        CustVO vo = custMapper.selectCust(custId);
        String strCustNm = (vo == null) ? null : vo.getCustNm();
        return strCustNm;
    }

    @Override
    public String insertCust(CustVO vo) {
        // 2018.05 채번 방식 변경 (MAX+1 -> genId)
        // String strCustId = custMapper.selectMaxCustId();
        String custId = SmsUtil.genId("C");
        vo.setCustId(custId);
        if (vo.getCustGrade() == null) {
            vo.setCustGrade("C");
        }
        custMapper.insertCust(vo);
        return custId;
    }

    @Override
    public int updateCust(CustVO vo) {
        return custMapper.updateCust(vo);
    }

    @Override
    public int deleteCust(String custId) {
        // TODO: 미수금이 남은 거래처는 삭제 막기
        return custMapper.deleteCust(custId);
    }

    @Override
    public List<HashMap<String, Object>> selectCustContactList(String custId) {
        return custMapper.selectCustContactList(custId);
    }

    @Override
    public int insertCustContact(HashMap<String, Object> param) {
        if (param.get("mainYn") == null) {
            param.put("mainYn", "N");
        }
        return custMapper.insertCustContact(param);
    }
}
