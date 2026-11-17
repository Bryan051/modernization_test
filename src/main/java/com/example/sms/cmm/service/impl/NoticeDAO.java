package com.example.sms.cmm.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.sms.cmm.ibatis.AbstractIbatisDAO;

/**
 * 공지사항 DAO (iBATIS)
 *
 * 2016.04.01 홍길동  최초 생성
 */
@Repository("noticeDAO")
public class NoticeDAO extends AbstractIbatisDAO {

    @SuppressWarnings("unchecked")
    public List<HashMap<String, Object>> selectNoticeList(HashMap<String, Object> param) {
        return (List<HashMap<String, Object>>) queryForList("notice.selectNoticeList", param);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Object> selectNotice(Long noticeId) {
        return (HashMap<String, Object>) queryForObject("notice.selectNotice", noticeId);
    }

    public void insertNotice(HashMap<String, Object> param) {
        insert("notice.insertNotice", param);
    }

    public int updateNoticeViewCnt(Long noticeId) {
        return update("notice.updateNoticeViewCnt", noticeId);
    }
}
