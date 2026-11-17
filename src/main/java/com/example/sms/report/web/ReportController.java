package com.example.sms.report.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sms.cmm.util.ExcelUtil;
import com.example.sms.report.service.SalesSummaryService;

/**
 * 대시보드 · 매출 리포트 컨트롤러 (2021.03 추가)
 */
@Controller
public class ReportController {

    @Resource(name = "salesSummaryService")
    private SalesSummaryService salesSummaryService;

    @RequestMapping(value = "/report/dashboard.do")
    public String dashboard() {
        return "sms/report/dashboard";
    }

    @RequestMapping(value = "/report/selectSalesSummary.do")
    @ResponseBody
    public Map<String, Object> selectSalesSummary(@RequestParam HashMap<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", salesSummaryService.selectSalesSummary(param));
        return result;
    }

    @RequestMapping(value = "/report/selectTopCustomerList.do")
    @ResponseBody
    public Map<String, Object> selectTopCustomerList(@RequestParam HashMap<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", salesSummaryService.selectTopCustomerList(param));
        return result;
    }

    @RequestMapping(value = "/report/selectProductSales.do")
    @ResponseBody
    public Map<String, Object> selectProductSales(@RequestParam HashMap<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", salesSummaryService.selectProductSales(param));
        return result;
    }

    @RequestMapping(value = "/report/downloadSalesExcel.do")
    public void downloadSalesExcel(@RequestParam HashMap<String, Object> param,
                                   HttpServletResponse response) throws IOException {
        List<HashMap<String, Object>> list = salesSummaryService.selectSalesSummary(param);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=sales_summary.xlsx");
        ExcelUtil.write("매출집계",
                new String[] {"거래처", "수주일", "수주금액", "제품", "수량", "청구금액", "수금액"},
                new String[] {"CUST_NM", "ORDER_DT", "ORDER_AMT", "PRODUCT_NM", "QTY", "BILL_AMT", "COLLECT_AMT"},
                list, response.getOutputStream());
    }
}
