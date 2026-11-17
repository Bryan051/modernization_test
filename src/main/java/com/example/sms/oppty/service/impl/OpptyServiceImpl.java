package com.example.sms.oppty.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.cmm.util.SmsUtil;
import com.example.sms.oppty.service.OpptyService;

/**
 * 영업기회 서비스 구현
 */
@Service("opptyService")
public class OpptyServiceImpl implements OpptyService {

    @Autowired
    private OpptyMapper opptyMapper;

    @Override
    public List<HashMap<String, Object>> selectOpptyList(HashMap<String, Object> param) {
        return opptyMapper.selectOpptyList(param);
    }

    @Override
    public HashMap<String, Object> selectOppty(String opptyId) {
        return opptyMapper.selectOppty(opptyId);
    }

    @Override
    public String insertOppty(HashMap<String, Object> param) {
        String strOpptyId = SmsUtil.genId("OP");
        param.put("opptyId", strOpptyId);
        opptyMapper.insertOppty(param);
        return strOpptyId;
    }

    @Override
    public int updateOpptyStage(HashMap<String, Object> param) {
        int iCnt = opptyMapper.updateOpptyStage(param);
        return iCnt;
    }
}
