package com.example.sms.quote.web;

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
import com.example.sms.quote.service.QuoteService;
import com.example.sms.quote.service.QuoteVO;
import com.example.sms.user.service.LoginVO;

/**
 * 견적 컨트롤러
 */
@Controller
public class QuoteController {

    @Resource(name = "quoteService")
    private QuoteService quoteService;

    @RequestMapping(value = "/quote/selectQuoteList.do")
    @ResponseBody
    public Map<String, Object> selectQuoteList(@RequestParam HashMap<String, Object> param) {
        SmsUtil.setPaging(param);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", quoteService.selectQuoteList(param));
        return result;
    }

    @RequestMapping(value = "/quote/selectQuote.do")
    @ResponseBody
    public QuoteVO selectQuote(@RequestParam("quoteId") String quoteId) {
        return quoteService.selectQuote(quoteId);
    }

    @RequestMapping(value = "/quote/insertQuote.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertQuote(HttpServletRequest request, @RequestBody QuoteVO vo) {
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute("loginVO");
        vo.setChargeUserId(loginVO.getUserId());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("quoteId", quoteService.insertQuote(vo));
        return result;
    }

    @RequestMapping(value = "/quote/updateQuoteStatus.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateQuoteStatus(@RequestParam("quoteId") String quoteId,
                                                 @RequestParam("statusCd") String statusCd) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("cnt", quoteService.updateQuoteStatus(quoteId, statusCd));
        return result;
    }
}
