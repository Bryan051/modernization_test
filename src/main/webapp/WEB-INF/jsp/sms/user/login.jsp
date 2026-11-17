<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>영업관리시스템 로그인</title>
    <%@ include file="/WEB-INF/jsp/sms/cmm/header.jsp" %>
</head>
<body>
<div class="login-box">
    <h2>영업관리시스템 (SMS)</h2>
    <form id="frmLogin" name="frmLogin" action="<c:url value='/user/actionLogin.do'/>" method="post">
        <table>
            <tr>
                <th>아이디</th>
                <td><input type="text" name="userId" id="userId" maxlength="20"/></td>
            </tr>
            <tr>
                <th>비밀번호</th>
                <td><input type="password" name="passwd" id="passwd" maxlength="30"/></td>
            </tr>
        </table>
        <c:if test="${not empty errMsg}">
            <p class="err">${errMsg}</p>
        </c:if>
        <button type="button" id="btnLogin">로그인</button>
    </form>
</div>

<script type="text/javascript">
$(document).ready(function() {
    $('#userId').focus();
    $('#btnLogin').click(fnLogin);
    $('#passwd').keydown(function(e) {
        if (e.keyCode == 13) {
            fnLogin();
        }
    });
});

function fnLogin() {
    if ($.trim($('#userId').val()) == '') {
        alert('아이디를 입력하세요.');
        return;
    }
    if ($.trim($('#passwd').val()) == '') {
        alert('비밀번호를 입력하세요.');
        return;
    }
    $('#frmLogin').submit();
}
</script>
</body>
</html>
