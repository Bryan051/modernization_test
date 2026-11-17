package com.example.sms.cmm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sms.cmm.service.impl.CodeDAO;
import com.example.sms.cmm.service.impl.NoticeDAO;
import com.example.sms.cmm.util.SmsUtil;
import com.example.sms.user.service.LoginVO;

/**
 * 공통 컨트롤러 (공통코드 · 공지사항)
 */
@Controller
public class CmmController {

    // FIXME: 서비스 클래스를 거치도록 정리 (급해서 DAO 를 바로 씀 - 2017.10)
    @Resource(name = "codeDAO")
    private CodeDAO codeDAO;

    @Resource(name = "noticeDAO")
    private NoticeDAO noticeDAO;

    @RequestMapping(value = "/cmm/selectCodeList.do")
    @ResponseBody
    public List<HashMap<String, Object>> selectCodeList(@RequestParam("grpCd") String grpCd) {
        return SmsUtil.getCodeList(grpCd);
    }

    @RequestMapping(value = "/cmm/insertCode.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertCode(@RequestParam HashMap<String, String> param) {
        codeDAO.insertCode(param);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "OK");
        return result;
    }

    @RequestMapping(value = "/cmm/selectNoticeList.do")
    @ResponseBody
    public Map<String, Object> selectNoticeList(@RequestParam HashMap<String, Object> param) {
        if (param.get("sortCol") == null) {
            param.put("sortCol", "REG_DT DESC");
        }
        SmsUtil.setPaging(param);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", noticeDAO.selectNoticeList(param));
        return result;
    }

    @RequestMapping(value = "/cmm/selectNotice.do")
    @ResponseBody
    public HashMap<String, Object> selectNotice(@RequestParam("noticeId") Long noticeId) {
        noticeDAO.updateNoticeViewCnt(noticeId);
        return noticeDAO.selectNotice(noticeId);
    }

    @RequestMapping(value = "/cmm/insertNotice.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertNotice(HttpServletRequest request,
                                            @RequestParam HashMap<String, Object> param) {
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute("loginVO");
        param.put("writerId", loginVO.getUserId());
        if (param.get("topYn") == null) {
            param.put("topYn", "N");
        }
        noticeDAO.insertNotice(param);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "OK");
        return result;
    }
}
