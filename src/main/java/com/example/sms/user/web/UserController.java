package com.example.sms.user.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sms.user.service.LoginVO;
import com.example.sms.user.service.UserService;

/**
 * 로그인 · 사원 컨트롤러
 */
@Controller
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping(value = "/user/login.do")
    public String login() {
        return "sms/user/login";
    }

    @RequestMapping(value = "/user/actionLogin.do", method = RequestMethod.POST)
    public String actionLogin(@RequestParam("userId") String userId,
                              @RequestParam("passwd") String passwd,
                              HttpServletRequest request, Model model) {
        LoginVO loginVO = userService.actionLogin(userId, passwd);
        if (loginVO == null) {
            model.addAttribute("errMsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "sms/user/login";
        }
        request.getSession().setAttribute("loginVO", loginVO);
        return "redirect:/report/dashboard.do";
    }

    @RequestMapping(value = "/user/logout.do")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/user/login.do";
    }

    @RequestMapping(value = "/user/selectUserList.do")
    @ResponseBody
    public Map<String, Object> selectUserList(@RequestParam HashMap<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", userService.selectUserList(param));
        result.put("deptList", userService.selectDeptList());
        return result;
    }
}
