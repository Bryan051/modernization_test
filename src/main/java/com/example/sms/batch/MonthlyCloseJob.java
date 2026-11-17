package com.example.sms.batch;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.sms.cmm.util.SmsUtil;

/**
 * 월마감 배치 (2022.05 추가)
 *
 * 매월 1일 01:30 에 전월까지의 수주 · 청구를 마감 처리한다.
 */
@Component
public class MonthlyCloseJob {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Scheduled(cron = "0 30 1 1 * *")
    public void closeMonth() {
        String strCloseYm = SmsUtil.getPrevYearMonth();
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("closeYm", strCloseYm);

        int iOrder = sqlSessionTemplate.update("com.example.sms.order.service.impl.OrderMapper.updateCloseYn", param);
        int iBill = sqlSessionTemplate.update("com.example.sms.bill.service.impl.BillMapper.updateCloseYn", param);
        // 2022.06 로그 파일로 남기기 (TODO)
        System.out.println("[MonthlyCloseJob] " + strCloseYm + " 마감 : 수주 " + iOrder + "건 / 청구 " + iBill + "건");
    }
}
