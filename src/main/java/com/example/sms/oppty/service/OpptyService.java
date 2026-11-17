package com.example.sms.oppty.service;

import java.util.HashMap;
import java.util.List;

/**
 * 영업기회 서비스
 */
public interface OpptyService {

    List<HashMap<String, Object>> selectOpptyList(HashMap<String, Object> param);

    HashMap<String, Object> selectOppty(String opptyId);

    /** 영업기회 등록 (생성된 영업기회ID 반환) */
    String insertOppty(HashMap<String, Object> param);

    int updateOpptyStage(HashMap<String, Object> param);
}
