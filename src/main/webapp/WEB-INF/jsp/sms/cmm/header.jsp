<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    공통 머리 : 각 화면의 <head> 안에서 include 한다.
    2016.03.07 홍길동  최초 작성
    2018.11.12 김철수  AJAX 401 응답 시 로그인 화면으로 이동
--%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/sms.css'/>"/>
<script type="text/javascript" src="<c:url value='/js/jquery-1.12.4.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/sms.common.js'/>"></script>
<script type="text/javascript">
    var ctx = '${pageContext.request.contextPath}';

    // 세션이 끊긴 상태에서 AJAX 를 부르면 로그인 화면으로 보낸다
    $(document).ajaxError(function(event, xhr) {
        if (xhr.status == 401) {
            location.href = ctx + '/user/login.do';
        }
    });
</script>
