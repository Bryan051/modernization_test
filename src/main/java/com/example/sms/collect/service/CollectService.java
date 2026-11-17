package com.example.sms.collect.service;

import java.util.HashMap;
import java.util.List;

/**
 * 수금 · 미수 서비스
 */
public interface CollectService {

    List<HashMap<String, Object>> selectCollectList(HashMap<String, Object> param);

    /** 미수 목록 (발행된 청구 중 아직 다 받지 못한 건) */
    List<HashMap<String, Object>> selectArrearsList(HashMap<String, Object> param);

    /** 수금 등록 */
    int insertCollect(HashMap<String, Object> param);
}
