package com.example.sms.user.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.user.service.LoginVO;
import com.example.sms.user.service.UserService;

/**
 * 사원 · 부서 서비스 구현
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public LoginVO actionLogin(String userId, String passwd) {
        LoginVO loginVO = userMapper.selectLoginUser(userId, passwd);
        if (loginVO != null) {
            userMapper.updateLastLoginDt(userId);
        }
        return loginVO;
    }

    @Override
    public List<LoginVO> selectUserList(HashMap<String, Object> param) {
        return userMapper.selectUserList(param);
    }

    @Override
    public LoginVO selectUser(String userId) {
        return userMapper.selectUser(userId);
    }

    @Override
    public List<HashMap<String, Object>> selectDeptList() {
        return userMapper.selectDeptList();
    }

    @Override
    public int insertUser(LoginVO vo) {
        // TODO: 비밀번호 규칙 검증 (8자 이상) 추가
        return userMapper.insertUser(vo);
    }

    @Override
    public int updateUser(LoginVO vo) {
        return userMapper.updateUser(vo);
    }
}
