package com.example.sms.order.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sms.cmm.util.SmsUtil;
import com.example.sms.order.service.OrderService;
import com.example.sms.order.service.OrderVO;
import com.example.sms.user.service.LoginVO;

/**
 * 수주 컨트롤러
 */
@Controller
public class OrderController {

    @Resource(name = "orderService")
    private OrderService orderService;

    @RequestMapping(value = "/order/orderList.do")
    public String orderList() {
        return "sms/order/orderList";
    }

    @RequestMapping(value = "/order/selectOrderList.do")
    @ResponseBody
    public Map<String, Object> selectOrderList(HttpServletRequest request,
                                               @RequestParam HashMap<String, Object> param) {
        String[] arrStatus = request.getParameterValues("status");
        if (arrStatus != null) {
            param.put("statusList", Arrays.asList(arrStatus));
        }
        SmsUtil.setPaging(param);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", orderService.selectOrderList(param));
        result.put("totalCnt", orderService.selectOrderListCnt(param));
        return result;
    }

    @RequestMapping(value = "/order/selectOrder.do")
    @ResponseBody
    public OrderVO selectOrder(@RequestParam("orderId") String orderId) {
        return orderService.selectOrder(orderId);
    }

    @RequestMapping(value = "/order/insertOrder.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertOrder(HttpServletRequest request, @RequestBody OrderVO vo) {
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute("loginVO");
        vo.setChargeUserId(loginVO.getUserId());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("orderId", orderService.insertOrder(vo));
        return result;
    }

    @RequestMapping(value = "/order/completeOrder.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> completeOrder(HttpServletRequest request,
                                             @RequestParam("orderId") String orderId) {
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute("loginVO");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            orderService.completeOrder(orderId, loginVO.getUserId());
            result.put("result", "OK");
        } catch (Exception e) {
            result.put("result", "FAIL");
            result.put("msg", e.getMessage());
        }
        return result;
    }
}
