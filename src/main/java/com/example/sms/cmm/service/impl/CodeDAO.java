package com.example.sms.cmm.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.sms.cmm.ibatis.AbstractIbatisDAO;

/**
 * 공통코드 DAO (iBATIS)
 *
 * 2016.03.14 홍길동  최초 생성
 */
@Repository("codeDAO")
public class CodeDAO extends AbstractIbatisDAO {

    @SuppressWarnings("unchecked")
    public List<HashMap<String, Object>> selectCodeList(HashMap<String, String> param) {
        return (List<HashMap<String, Object>>) queryForList("code.selectCodeList", param);
    }

    public String selectCodeName(HashMap<String, String> param) {
        return (String) queryForObject("code.selectCodeName", param);
    }

    public void insertCode(HashMap<String, String> param) {
        insert("code.insertCode", param);
    }
}
