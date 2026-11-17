package com.example.sms.oppty.web;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.sms.oppty.service.OpptyService;

/**
 * 영업기회 컨트롤러 (구 화면용)
 *
 * 2016.05.09 홍길동  최초 생성
 * 2017.05.22 김철수  OpptyController 로 재작성
 */
@Controller
public class OpptyOldController {

    @Resource(name = "opptyService")
    private OpptyService opptyService;

    //@RequestMapping(value = "/oppty/opptyListOld.do")
    public String opptyListOld(Model model) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("stageCd", "10");
        List<HashMap<String, Object>> list = opptyService.selectOpptyList(param);
        model.addAttribute("opptyList", list);
        return "sms/oppty/opptyListOld";
    }

    //@RequestMapping(value = "/oppty/opptyDetailOld.do")
    public String opptyDetailOld(String opptyId, Model model) {
        model.addAttribute("oppty", opptyService.selectOppty(opptyId));
        return "sms/oppty/opptyDetailOld";
    }
}
