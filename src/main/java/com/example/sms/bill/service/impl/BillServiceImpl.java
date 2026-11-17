package com.example.sms.bill.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.bill.service.BillService;
import com.example.sms.bill.service.BillVO;
import com.example.sms.cmm.util.SmsUtil;
import com.example.sms.cust.service.impl.CustMapper;

/**
 * 청구(세금계산서) 서비스 구현
 *
 * <pre>
 *   수정일        수정자      수정내용
 *  ----------   --------   ---------------------------
 *  2016.09.05   김철수      최초 생성
 *  2019.08.22   홍길동      MyBatis 로 이전
 *  2019.11.05   김철수      전자세금계산서 발행 연동 (고객 요청으로 급히 추가)
 *  2020.04.27   홍길동      발행 후 거래처 여신상태 변경 추가
 * </pre>
 */
@Service("billService")
public class BillServiceImpl implements BillService {

    /** 전자세금계산서 발행 요청 주소 (연동 업체 제공) */
    private static final String ETAX_URL = "http://etax.example.com/api/issue";

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private CustMapper custMapper;

    @Override
    public List<BillVO> selectBillList(HashMap<String, Object> param) {
        return billMapper.selectBillList(param);
    }

    @Override
    public BillVO selectBill(String billId) {
        return billMapper.selectBill(billId);
    }

    @Override
    public String issueBill(BillVO vo) throws Exception {
        // 세금계산서에 들어갈 사업자번호 · 주소는 거래처 정보에서 읽는다
        HashMap<String, Object> cust = custMapper.selectCustBizInfo(vo.getCustId());
        if (cust == null) {
            throw new IllegalArgumentException("거래처 정보가 없습니다 : " + vo.getCustId());
        }
        String strBizNo = (String) cust.get("BIZ_NO");
        String strAddr = (String) cust.get("ADDR");

        String billId = SmsUtil.genId("BL");
        vo.setBillId(billId);
        vo.setBillDt(SmsUtil.getToday("yyyy-MM-dd"));
        vo.setTaxAmt(SmsUtil.calcVat(vo.getSupplyAmt()));
        vo.setTotalAmt(vo.getSupplyAmt() + vo.getTaxAmt());
        billMapper.insertBill(vo);

        // 전자세금계산서 발행 요청
        String strEtaxNo = requestEtaxIssue(billId, strBizNo, strAddr, vo.getSupplyAmt(), vo.getTaxAmt());
        billMapper.updateBillEtax(billId, strEtaxNo, "10");

        // 청구가 나가면 거래처 여신상태를 '미수발생(02)' 으로
        custMapper.updateCreditStatus(vo.getCustId(), "02");
        return billId;
    }

    /**
     * 외부 전자세금계산서 서버에 발행 요청 (승인번호 반환)
     */
    private String requestEtaxIssue(String billId, String bizNo, String addr, long supplyAmt, long taxAmt)
            throws IOException {
        URL url = new URL(ETAX_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // TODO: JSON 라이브러리로 교체
            String strBody = "{\"billId\":\"" + billId + "\",\"bizNo\":\"" + bizNo + "\",\"addr\":\"" + addr
                    + "\",\"supplyAmt\":" + supplyAmt + ",\"taxAmt\":" + taxAmt + "}";
            OutputStream os = conn.getOutputStream();
            os.write(strBody.getBytes("UTF-8"));
            os.close();

            int iCode = conn.getResponseCode();
            if (iCode != 200) {
                throw new IOException("전자세금계산서 발행 실패 : HTTP " + iCode);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            // 응답 예) {"etaxNo":"20191105-12345678"}
            String strRes = sb.toString();
            int iIdx = strRes.indexOf("\"etaxNo\":\"");
            if (iIdx < 0) {
                throw new IOException("승인번호가 없는 응답 : " + strRes);
            }
            int iStart = iIdx + 10;
            return strRes.substring(iStart, strRes.indexOf("\"", iStart));
        } finally {
            conn.disconnect();
        }
    }
}
