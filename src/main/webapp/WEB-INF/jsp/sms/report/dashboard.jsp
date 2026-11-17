<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.example.sms.cmm.util.SmsUtil" %>
<%@ page import="com.example.sms.report.service.SalesSummaryService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    // 2021.03 리포트 추가 : 올해 월별 수주 금액은 화면을 열 때 바로 조회한다
    WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(application);
    SalesSummaryService salesSummaryService = (SalesSummaryService) wac.getBean("salesSummaryService");

    HashMap<String, Object> param = new HashMap<String, Object>();
    String strYear = SmsUtil.getToday("yyyy");
    param.put("fromDt", strYear + "-01-01");
    param.put("toDt", strYear + "-12-31");
    List<HashMap<String, Object>> monthlyList = salesSummaryService.selectMonthlySales(param);
    request.setAttribute("monthlyList", monthlyList);
%>
<!DOCTYPE html>
<html>
<head>
    <title>영업 대시보드</title>
    <%@ include file="/WEB-INF/jsp/sms/cmm/header.jsp" %>
</head>
<body>
<h2>영업 대시보드</h2>
<p>
    <a href="<c:url value='/cust/custList.do'/>">거래처</a> |
    <a href="<c:url value='/order/orderList.do'/>">수주</a> |
    <a href="<c:url value='/collect/arrearsList.do'/>">미수</a> |
    <a href="<c:url value='/user/logout.do'/>">로그아웃</a>
</p>

<h3>월별 수주 금액 (<%= strYear %>년)</h3>
<table class="list" border="1">
    <thead>
        <tr><th>월</th><th>건수</th><th>금액</th></tr>
    </thead>
    <tbody>
        <c:forEach var="row" items="${monthlyList}">
            <tr>
                <td>${row.ORDER_YM}</td>
                <td>${row.ORDER_CNT}</td>
                <td style="text-align:right">${row.ORDER_AMT}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<h3>신규 거래처</h3>
<ul id="ulCust"></ul>

<h3>최근 수주</h3>
<ul id="ulOrder"></ul>

<h3>미수 상위</h3>
<ul id="ulArrears"></ul>

<script type="text/javascript">
$(document).ready(function() {
    loadCust();
    loadOrder();
    loadArrears();
});

// 최근 등록한 거래처 5건
function loadCust() {
    $.ajax({
        url: ctx + '/cust/selectCustList.do',
        type: 'POST',
        dataType: 'json',
        data: { pageNo: 1, pageSize: 5, sortCol: 'C.REG_DT', sortDir: 'DESC' },
        success: function(res) {
            var html = '';
            $.each(res.list, function(i, row) {
                html += '<li>' + row.custNm + ' (' + (row.custGradeNm || '') + ')</li>';
            });
            $('#ulCust').html(html);
        }
    });
}

// 최근 수주 5건
function loadOrder() {
    $.ajax({
        url: ctx + '/order/selectOrderList.do',
        type: 'POST',
        dataType: 'json',
        data: { pageNo: 1, pageSize: 5 },
        success: function(res) {
            var html = '';
            $.each(res.list, function(i, row) {
                html += '<li>' + row.orderDt + ' ' + (row.custNm || '') + ' ' + row.orderAmt + '원 [' + row.orderStatus + ']</li>';
            });
            $('#ulOrder').html(html);
        }
    });
}

// 30일 넘은 미수
function loadArrears() {
    $.ajax({
        url: ctx + '/collect/selectArrearsList.do',
        type: 'POST',
        dataType: 'json',
        data: { overdueDays: 30 },
        success: function(res) {
            var html = '';
            $.each(res.list, function(i, row) {
                if (i < 5) {
                    html += '<li>' + row.CUST_NM + ' ' + row.ARREARS_AMT + '원 (' + row.OVERDUE_DAYS + '일)</li>';
                }
            });
            $('#ulArrears').html(html);
        }
    });
}
</script>
</body>
</html>
