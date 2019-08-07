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
<c:forEach var="kospi" items="${KospiValue}">
    <tr>
        <td>${kospi.date}</td>
        <td>${kospi.kospi}</td>
    </tr>
</c:forEach>
</table>
</body>
</html>