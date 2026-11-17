package com.example.sms.contract.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.sms.cmm.util.FileUtil;
import com.example.sms.contract.service.ContractService;
import com.example.sms.contract.service.ContractVO;
import com.example.sms.user.service.LoginVO;

/**
 * 계약 컨트롤러
 */
@Controller
public class ContractController {

    @Resource(name = "contractService")
    private ContractService contractService;

    @RequestMapping(value = "/contract/selectContractList.do")
    @ResponseBody
    public Map<String, Object> selectContractList(@RequestParam HashMap<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", contractService.selectContractList(param));
        return result;
    }

    @RequestMapping(value = "/contract/selectContract.do")
    @ResponseBody
    public ContractVO selectContract(@RequestParam("contractId") String contractId) {
        return contractService.selectContract(contractId);
    }

    @RequestMapping(value = "/contract/updateContractAmount.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateContractAmount(@RequestParam("contractId") String contractId,
                                                    @RequestParam("contractAmt") long contractAmt) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("cnt", contractService.updateContractAmount(contractId, contractAmt));
        return result;
    }

    @RequestMapping(value = "/contract/updateContractStatus.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateContractStatus(@RequestParam("contractId") String contractId,
                                                    @RequestParam("statusCd") String statusCd) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("cnt", contractService.updateContractStatus(contractId, statusCd));
        return result;
    }

    /** 계약서 파일 첨부 */
    @RequestMapping(value = "/contract/uploadContractFile.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadContractFile(HttpServletRequest request,
                                                  @RequestParam("contractId") String contractId,
                                                  @RequestParam("file") MultipartFile file) throws IOException {
        LoginVO loginVO = (LoginVO) request.getSession().getAttribute("loginVO");
        String strSaveNm = FileUtil.save(file, "contract/" + contractId);
        System.out.println("[계약서 첨부] " + loginVO.getUserId() + " / " + contractId + " / " + strSaveNm);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("saveNm", strSaveNm);
        return result;
    }
}
