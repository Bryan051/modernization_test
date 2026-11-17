package com.example.sms.user.service;

import java.util.HashMap;
import java.util.List;

/**
 * 사원 · 부서 서비스
 */
public interface UserService {

    /** 로그인 (일치하는 사원이 없으면 null) */
    LoginVO actionLogin(String userId, String passwd);

    List<LoginVO> selectUserList(HashMap<String, Object> param);

    LoginVO selectUser(String userId);

    List<HashMap<String, Object>> selectDeptList();

    int insertUser(LoginVO vo);

    int updateUser(LoginVO vo);
}
