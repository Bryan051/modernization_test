package com.example.sms.oppty.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 영업기회 매퍼 (mapper/oppty/Oppty_SQL.xml)
 */
@Mapper
public interface OpptyMapper {

    List<HashMap<String, Object>> selectOpptyList(HashMap<String, Object> param);

    HashMap<String, Object> selectOppty(String opptyId);

    int insertOppty(HashMap<String, Object> param);

    int updateOpptyStage(HashMap<String, Object> param);
}
