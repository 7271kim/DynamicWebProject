<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body>
Kospi 지수 
<table>
	<tr>
	    <td>회사이름</td>
	    <td>코드</td>
	    <td>날자</td>
	    <td>금일 가격</td>
	    <td>변동</td>
	    <td>변동률</td>
	    <td>외국인 자본</td>
	    <td>외국인 퍼센트</td>
	</tr>
<c:forEach var="company" items="${list}">
    <tr>
        <td>${company.companyName}</td>
        <td>${company.companyCode}</td>
        <td>${company.date}</td>
        <td>${company.todayPrice}</td>
        <td>${company.todayUpdown}</td>
        <td>${company.todayRate}</td>
        <td>${company.forigin}</td>
        <td>${company.forignPersent}</td>
    </tr>
</c:forEach>
</table>
</body>
</html>