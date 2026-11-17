package com.example.sms.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.sms.user.service.LoginVO;

/**
 * 로그인 체크 인터셉터 : 세션에 loginVO 가 없으면 로그인 화면으로 보낸다.
 *
 * 2016.03.07 홍길동  최초 생성
 * 2018.11.12 김철수  AJAX 요청은 리다이렉트 대신 401 응답
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        LoginVO loginVO = (session == null) ? null : (LoginVO) session.getAttribute("loginVO");
        if (loginVO == null) {
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                response.sendRedirect(request.getContextPath() + "/user/login.do");
            }
            return false;
        }
        return true;
    }
}
