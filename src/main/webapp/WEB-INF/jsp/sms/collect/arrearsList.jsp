<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>미수 현황</title>
    <%@ include file="/WEB-INF/jsp/sms/cmm/header.jsp" %>
</head>
<body>
<p><a href="<c:url value='/report/dashboard.do'/>">대시보드</a></p>
<h2>미수 현황</h2>

<form id="frmSearch" name="frmSearch" action="<c:url value='/collect/downloadArrearsExcel.do'/>" method="post">
    경과일 <input type="text" name="overdueDays" id="overdueDays" size="4" value="0"/> 일 이상
    <button type="button" id="btnSearch">조회</button>
    <button type="submit" id="btnExcel">엑셀 다운로드</button>
</form>

<table class="list" border="1">
    <thead>
        <tr>
            <th>거래처</th>
            <th>청구일</th>
            <th>청구금액</th>
            <th>수금액</th>
            <th>미수금액</th>
            <th>경과일</th>
            <th>수금</th>
        </tr>
    </thead>
    <tbody id="tbodyArrears"></tbody>
</table>

<script type="text/javascript">
$(document).ready(function() {
    $('#btnSearch').click(fnSearch);
    fnSearch();
});

function fnSearch() {
    $.ajax({
        url: ctx + '/collect/selectArrearsList.do',
        type: 'POST',
        dataType: 'json',
        data: { overdueDays: $('#overdueDays').val() },
        success: function(res) {
            var html = '';
            $.each(res.list, function(i, row) {
                html += '<tr>';
                html += '<td>' + row.CUST_NM + '</td>';
                html += '<td>' + row.BILL_DT + '</td>';
                html += '<td style="text-align:right">' + row.TOTAL_AMT + '</td>';
                html += '<td style="text-align:right">' + row.PAID_AMT + '</td>';
                html += '<td style="text-align:right">' + row.ARREARS_AMT + '</td>';
                html += '<td>' + row.OVERDUE_DAYS + '</td>';
                html += '<td><a href="javascript:fnCollect(\'' + row.BILL_ID + '\',\'' + row.ORDER_ID + '\',\''
                      + row.CUST_ID + '\',' + row.ARREARS_AMT + ')">수금 등록</a></td>';
                html += '</tr>';
            });
            $('#tbodyArrears').html(html);
        }
    });
}

// 남은 미수금액을 기본값으로 수금 등록
function fnCollect(billId, orderId, custId, arrearsAmt) {
    var amt = prompt('수금액을 입력하세요.', arrearsAmt);
    if (amt == null || amt == '') {
        return;
    }
    $.ajax({
        url: ctx + '/collect/insertCollect.do',
        type: 'POST',
        dataType: 'json',
        data: { billId: billId, orderId: orderId, custId: custId, collectAmt: amt, collectMethod: '01' },
        success: function(res) {
            fnSearch();
        }
    });
}
</script>
</body>
</html>
