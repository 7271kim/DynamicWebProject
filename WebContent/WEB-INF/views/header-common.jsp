<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
function test(e){
  return new Promise(function (resolve, reject) {
    window.setTimeout(function(){
        var data = 100;
        resolve(data+e);
    }, 2000);
    
  });
}
function test2(e){
    console.log('ddddddd + ' + e);
}


test(1).then(function (resolvedData) {
test2(resolvedData)
})
</script>
</head>
<body>
헤이 베이비 ss
 <c:forEach var="base" items="${list }">
asada ${base.name}ss
 </c:forEach>
 dd
</body>
</html>