<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<link rel="stylesheet" href="css/ca_fr.css" type="text/css">
<link rel="stylesheet" href="css/ca_fr22.css" type="text/css">
</head>
<body>
<h1>로또 번호 뽑기</h1>
<h3>Base</h3>
<p><b>최근 회차의 나온 번호와, 연관된 번호중 같이 나올 확률이 높은 번호 제외 후 뽑기</b></p>
<p>최근 회차 번호 : ${lastLotto }</p>
<p>과거 ${noDate}동안 나오지 않은 번호</p>
<c:forEach  var="histroryPickItem" items="${histroryPick }" varStatus="status">
${histroryPickItem.key}
</c:forEach >
<p>기본 제외된 번호 : 
<c:forEach  var="noPickItem" items="${totalNopick }" varStatus="status">
${noPickItem}
</c:forEach >
</p>
<form action="lotto.jin">
<h3 class='join-title'> <label for="checkOneStep">0-10번대 뽑을 갯수</label> <input type="number" id="checkOneStep" name="checkOneStep" value="${checkOneStep}"></h3>
<h3 class='join-title'> <label for="checkTwoStep">11-20번대 뽑을 갯수</label> <input type="number" id="checkTwoStep" name="checkTwoStep" value="${checkTwoStep}"></h3>
<h3 class='join-title'> <label for="checkThreeStep">21-30번대 뽑을 갯수</label> <input type="number" id="checkThreeStep" name="checkThreeStep" value="${checkThreeStep}"></h3>
<h3 class='join-title'> <label for="checkFourStep">31-40번대 뽑을 갯수</label> <input type="number" id="checkFourStep" name="checkFourStep" value="${checkFourStep}"></h3>
<h3 class='join-title'> <label for="checkFiveStep">41-45번대 뽑을 갯수</label> <input type="number" id="checkFiveStep" name="checkFiveStep" value="${checkFiveStep}"></h3>
<h3 class='join-title'> <label for="noPick">뽑지 않을 번호 (",로 구분해주세요")</label> <input type="text" id="noPick" name="noPick" value="${noPick}"></h3>
<h3 class='join-title'> <label for="pickBefore">미리 뽑아놓을 번호(",로 구분해주세요")</label> <input type="text" id="pickBefore" name="pickBefore" value="${pickBefore}"></h3>
<h3 class='join-title'> <label for="totalGetLotto">구매 횟수</label> <input type="number" id="totalGetLotto" name="totalGetLotto" value="${totalGetLotto}"></h3>
<input type="submit" value="로또번호 뽑기" >
</form>
<c:forEach  var="lotto" items="${totalLotto }" varStatus="status">
 ${lotto} <br/>
</c:forEach >
${error }
</body>
</html>
