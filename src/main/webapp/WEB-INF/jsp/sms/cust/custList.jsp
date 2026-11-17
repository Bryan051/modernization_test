<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>거래처 관리</title>
    <%@ include file="/WEB-INF/jsp/sms/cmm/header.jsp" %>
</head>
<body>
<p><a href="<c:url value='/report/dashboard.do'/>">대시보드</a></p>
<h2>거래처 관리</h2>

<form id="frmSearch" name="frmSearch" onsubmit="return false;">
    <select name="searchType">
        <option value="">전체</option>
        <option value="BIZ">사업자번호</option>
        <option value="CEO">대표자</option>
    </select>
    <input type="text" name="searchWord" id="searchWord"/>
    <label><input type="checkbox" name="grade" value="A"/>A</label>
    <label><input type="checkbox" name="grade" value="B"/>B</label>
    <label><input type="checkbox" name="grade" value="C"/>C</label>
    <select name="creditStatus">
        <option value="">여신상태 전체</option>
        <option value="01">정상</option>
        <option value="02">미수발생</option>
        <option value="03">연체</option>
    </select>
    <button type="button" id="btnSearch">조회</button>
</form>

<table class="list" border="1">
    <thead>
        <tr>
            <th>거래처명</th>
            <th>사업자번호</th>
            <th>대표자</th>
            <th>등급</th>
            <th>여신상태</th>
            <th>관리</th>
        </tr>
    </thead>
    <tbody id="tbodyCust"></tbody>
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
        url: ctx + '/cust/selectCustList.do',
        type: 'POST',
        dataType: 'json',
        data: $('#frmSearch').serialize() + '&pageNo=' + pageNo + '&pageSize=20',
        success: function(res) {
            var html = '';
            $.each(res.list, function(i, row) {
                html += '<tr>';
                html += '<td>' + row.custNm + '</td>';
                html += '<td>' + (row.bizNo || '') + '</td>';
                html += '<td>' + (row.ceoNm || '') + '</td>';
                html += '<td>' + (row.custGradeNm || '') + '</td>';
                html += '<td>' + row.creditStatus + '</td>';
                html += '<td><a href="javascript:fnDelete(\'' + row.custId + '\')">삭제</a></td>';
                html += '</tr>';
            });
            $('#tbodyCust').html(html);
            $('#divPaging').text('총 ' + res.totalCnt + '건');
        },
        error: function() {
            alert('거래처 조회 중 오류가 발생했습니다.');
        }
    });
}

function fnDelete(custId) {
    if (!confirm('삭제하시겠습니까?')) {
        return;
    }
    $.ajax({
        url: ctx + '/cust/deleteCust.do',
        type: 'POST',
        dataType: 'json',
        data: { custId: custId },
        success: function(res) {
            fnSearch(curPage);
        }
    });
}
</script>
</body>
</html>
