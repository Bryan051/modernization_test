package com.example.sms.collect.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sms.cmm.util.ExcelUtil;
import com.example.sms.collect.service.CollectService;
import com.example.sms.user.service.LoginVO;

/**
 * 수금 · 미수 컨트롤러
 */
@Controller
public class CollectController {

    @Resource(name = "collectService")
    private CollectService collectService;

    @RequestMapping(value = "/collect/arrearsList.do")
    public String arrearsList() {
        return "sms/collect/arrearsList";
    }

    @RequestMapping(value = "/collect/selectCollectList.do")
    @ResponseBody
    public Map<String, Object> selectCollectList(@RequestParam HashMap<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", collectService.selectCollectList(param));
        return result;
    }

    @RequestMapping(value = "/collect/selectArrearsList.do")
    @ResponseBody
    public Map<String, Object> selectArrearsList(@RequestParam HashMap<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", collectService.selectArrearsList(param));
        return result;
    }

    @RequestMapping(value = "/collect/insertCollect.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertCollect(HttpServletRequest request,
                                             @RequestParam HashMap<String, Object> param) {
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute("loginVO");
        param.put("regUserId", loginVO.getUserId());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("cnt", collectService.insertCollect(param));
        return result;
    }

    @RequestMapping(value = "/collect/downloadArrearsExcel.do", method = RequestMethod.POST)
    public void downloadArrearsExcel(@RequestParam HashMap<String, Object> param,
                                     HttpServletResponse response) throws IOException {
        List<HashMap<String, Object>> list = collectService.selectArrearsList(param);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=arrears.xlsx");
        ExcelUtil.write("미수현황",
                new String[] {"거래처", "청구일", "청구금액", "수금액", "미수금액", "경과일"},
                new String[] {"CUST_NM", "BILL_DT", "TOTAL_AMT", "PAID_AMT", "ARREARS_AMT", "OVERDUE_DAYS"},
                list, response.getOutputStream());
    }
}
