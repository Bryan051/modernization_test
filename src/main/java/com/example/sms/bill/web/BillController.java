package com.example.sms.bill.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sms.bill.service.BillService;
import com.example.sms.bill.service.BillVO;

/**
 * 청구 컨트롤러
 */
@Controller
public class BillController {

    @Resource(name = "billService")
    private BillService billService;

    @RequestMapping(value = "/bill/selectBillList.do")
    @ResponseBody
    public Map<String, Object> selectBillList(@RequestParam HashMap<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", billService.selectBillList(param));
        return result;
    }

    @RequestMapping(value = "/bill/selectBill.do")
    @ResponseBody
    public BillVO selectBill(@RequestParam("billId") String billId) {
        return billService.selectBill(billId);
    }
}
