package com.example.sms.user.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.sms.user.service.LoginVO;

/**
 * 사원 · 부서 매퍼 (mapper/user/User_SQL.xml)
 */
@Mapper
public interface UserMapper {

    LoginVO selectLoginUser(@Param("userId") String userId, @Param("passwd") String passwd);

    List<LoginVO> selectUserList(HashMap<String, Object> param);

    LoginVO selectUser(String userId);

    List<HashMap<String, Object>> selectDeptList();

    int updateLastLoginDt(String userId);

    int insertUser(LoginVO vo);

    int updateUser(LoginVO vo);
}
