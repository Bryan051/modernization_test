<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>수주 관리</title>
    <%@ include file="/WEB-INF/jsp/sms/cmm/header.jsp" %>
</head>
<body>
<p><a href="<c:url value='/report/dashboard.do'/>">대시보드</a></p>
<h2>수주 관리</h2>

<form id="frmSearch" name="frmSearch" onsubmit="return false;">
    수주일 <input type="text" name="fromDt" id="fromDt" size="10"/> ~ <input type="text" name="toDt" id="toDt" size="10"/>
    <label><input type="checkbox" name="status" value="접수" checked="checked"/>접수</label>
    <label><input type="checkbox" name="status" value="계약완료"/>계약완료</label>
    <label><input type="checkbox" name="status" value="청구완료"/>청구완료</label>
    <label><input type="checkbox" name="status" value="수금완료"/>수금완료</label>
    <button type="button" id="btnSearch">조회</button>
</form>

<table class="list" border="1">
    <thead>
        <tr>
            <th>수주번호</th>
            <th>거래처</th>
            <th>수주일</th>
            <th>수주금액</th>
            <th>상태</th>
            <th>처리</th>
        </tr>
    </thead>
    <tbody id="tbodyOrder"></tbody>
</table>
<div id="divPaging"></div>

<script type="text/javascript">
var curPage = 1;

$(document).ready(function() {
    $('#btnSearch').click(function() { fnSearch(1); });
    fnSearch(1);
});

function fnSearch(pageNo) {
    curPage = pageNo;
    $.ajax({
        url: ctx + '/order/selectOrderList.do',
        type: 'POST',
        dataType: 'json',
        data: $('#frmSearch').serialize() + '&pageNo=' + pageNo + '&pageSize=20',
        success: function(res) {
            var html = '';
            $.each(res.list, function(i, row) {
                html += '<tr>';
                html += '<td>' + row.orderId + '</td>';
                html += '<td>' + (row.custNm || '') + '</td>';
                html += '<td>' + row.orderDt + '</td>';
                html += '<td style="text-align:right">' + row.orderAmt + '</td>';
                html += '<td>' + row.orderStatus + '</td>';
                if (row.orderStatus == '접수') {
                    html += '<td><a href="javascript:fnComplete(\'' + row.orderId + '\')">수주 완료</a></td>';
                } else {
                    html += '<td></td>';
                }
                html += '</tr>';
            });
            $('#tbodyOrder').html(html);
            $('#divPaging').text('총 ' + res.totalCnt + '건');
        },
        error: function() {
            alert('수주 조회 중 오류가 발생했습니다.');
        }
    });
}

// 수주 완료 처리
function fnComplete(orderId) {
    if (!confirm('수주를 완료 처리하시겠습니까?')) {
        return;
    }
    $.ajax({
        url: ctx + '/order/completeOrder.do',
        type: 'POST',
        dataType: 'json',
        data: { orderId: orderId },
        success: function(res) {
            if (res.result == 'OK') {
                fnSearch(curPage);
            } else {
                alert('처리 실패 : ' + res.msg);
            }
        }
    });
}
</script>
</body>
</html>
