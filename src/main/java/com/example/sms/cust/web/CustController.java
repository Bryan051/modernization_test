package com.example.sms.cust.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sms.cmm.util.SmsUtil;
import com.example.sms.cust.service.CustService;
import com.example.sms.cust.service.CustVO;
import com.example.sms.user.service.LoginVO;

/**
 * 거래처 컨트롤러
 *
 * <pre>
 *   수정일        수정자      수정내용
 *  ----------   --------   ---------------------------
 *  2016.03.14   홍길동      최초 생성
 *  2019.11.05   김철수      신용상태 검색 조건 추가
 * </pre>
 */
@Controller
public class CustController {

    @Resource(name = "custService")
    private CustService custService;

    @RequestMapping(value = "/cust/custList.do")
    public String custList() {
        return "sms/cust/custList";
    }

    @RequestMapping(value = "/cust/selectCustList.do")
    @ResponseBody
    public Map<String, Object> selectCustList(HttpServletRequest request,
                                              @RequestParam HashMap<String, Object> param) {
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute("loginVO");
        if (!"ADMIN".equals(loginVO.getAuthCd())) {
            // 영업사원은 본인 담당 거래처만
            param.put("chargeUserId", loginVO.getUserId());
        }
        String[] arrGrade = request.getParameterValues("grade");
        if (arrGrade != null) {
            param.put("gradeList", Arrays.asList(arrGrade));
        }
        SmsUtil.setPaging(param);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", custService.selectCustList(param));
        result.put("totalCnt", custService.selectCustListCnt(param));
        return result;
    }

    @RequestMapping(value = "/cust/selectCust.do")
    @ResponseBody
    public Map<String, Object> selectCust(@RequestParam("custId") String custId) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("cust", custService.selectCust(custId));
        result.put("contactList", custService.selectCustContactList(custId));
        return result;
    }

    @RequestMapping(value = "/cust/insertCust.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertCust(HttpServletRequest request, CustVO vo) {
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute("loginVO");
        vo.setRegUserId(loginVO.getUserId());
        if (vo.getChargeUserId() == null) {
            vo.setChargeUserId(loginVO.getUserId());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("custId", custService.insertCust(vo));
        return result;
    }

    @RequestMapping(value = "/cust/updateCust.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateCust(CustVO vo) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("cnt", custService.updateCust(vo));
        return result;
    }

    @RequestMapping(value = "/cust/deleteCust.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteCust(@RequestParam("custId") String custId) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("cnt", custService.deleteCust(custId));
        return result;
    }

    @RequestMapping(value = "/cust/insertCustContact.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertCustContact(@RequestParam HashMap<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("cnt", custService.insertCustContact(param));
        return result;
    }
}
