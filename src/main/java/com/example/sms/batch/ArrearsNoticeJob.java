package com.example.sms.batch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.sms.cmm.util.MailUtil;
import com.example.sms.cmm.util.SmsUtil;
import com.example.sms.collect.service.impl.CollectMapper;
import com.example.sms.cust.service.impl.CustMapper;

/**
 * 미수금 안내 메일 배치 (2022.05 추가)
 *
 * 평일 09:00 에 30일 이상 지난 미수 건이 있는 거래처 대표 담당자에게 메일을 보낸다.
 */
@Component
public class ArrearsNoticeJob {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private CustMapper custMapper;

    @Scheduled(cron = "0 0 9 * * MON-FRI")
    public void sendArrearsNotice() {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("overdueDays", 30);
        List<HashMap<String, Object>> arrearsList = collectMapper.selectArrearsList(param);

        Set<String> noticed = new HashSet<String>();
        int iSent = 0;
        for (HashMap<String, Object> arrears : arrearsList) {
            String strCustId = (String) arrears.get("CUST_ID");
            if (!noticed.add(strCustId)) {
                continue;   // 거래처당 하루 한 번만
            }
            HashMap<String, Object> contact = custMapper.selectCustMainContact(strCustId);
            if (contact == null || contact.get("EMAIL") == null) {
                continue;
            }

            long lArrearsAmt = ((Number) arrears.get("ARREARS_AMT")).longValue();
            String strBody = contact.get("CONTACT_NM") + " 님,\n\n"
                    + arrears.get("CUST_NM") + " 의 미수금 " + SmsUtil.formatAmt(lArrearsAmt)
                    + "원이 " + arrears.get("OVERDUE_DAYS") + "일 경과했습니다. 확인 부탁드립니다.\n";
            boolean bOk = MailUtil.send((String) contact.get("EMAIL"), "[SMS] 미수금 안내", strBody);
            if (bOk) {
                custMapper.updateLastNoticeDt(strCustId);
                custMapper.updateCreditStatus(strCustId, "03");
                iSent++;
            }
        }
        System.out.println("[ArrearsNoticeJob] 안내 메일 " + iSent + "건 발송");
    }
}
