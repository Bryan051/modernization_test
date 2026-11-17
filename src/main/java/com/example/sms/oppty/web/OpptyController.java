package com.example.sms.oppty.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sms.oppty.service.OpptyService;
import com.example.sms.user.service.LoginVO;

/**
 * 영업기회 컨트롤러
 *
 * 2016.05.09 홍길동  최초 생성
 * 2017.05.22 김철수  화면 개편으로 AJAX 방식으로 재작성
 */
@Controller
public class OpptyController {

    @Resource(name = "opptyService")
    private OpptyService opptyService;

    @RequestMapping(value = "/oppty/selectOpptyList.do")
    @ResponseBody
    public Map<String, Object> selectOpptyList(HttpServletRequest request,
                                               @RequestParam HashMap<String, Object> param) {
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute("loginVO");
        if (!"ADMIN".equals(loginVO.getAuthCd())) {
            param.put("chargeUserId", loginVO.getUserId());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", opptyService.selectOpptyList(param));
        return result;
    }

    @RequestMapping(value = "/oppty/selectOppty.do")
    @ResponseBody
    public HashMap<String, Object> selectOppty(@RequestParam("opptyId") String opptyId) {
        return opptyService.selectOppty(opptyId);
    }

    @RequestMapping(value = "/oppty/insertOppty.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertOppty(HttpServletRequest request,
                                           @RequestParam HashMap<String, Object> param) {
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute("loginVO");
        param.put("chargeUserId", loginVO.getUserId());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("opptyId", opptyService.insertOppty(param));
        return result;
    }

    @RequestMapping(value = "/oppty/updateOpptyStage.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateOpptyStage(@RequestParam HashMap<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("cnt", opptyService.updateOpptyStage(param));
        return result;
    }
}
