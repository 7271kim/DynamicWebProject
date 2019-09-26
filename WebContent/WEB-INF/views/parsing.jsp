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
PARSING하자 <br/> 
ERRO : ${baseModel.error } <br/> 
KOSPI : ${baseModel.kospi } <br/> 
TODAY : ${baseModel.today } <br/> 
<c:forEach var="compay" items="${keys}">
${baseModel.comPanyData[compay].companyName }<br/>
${baseModel.comPanyData[compay].companyCode }<br/>
${baseModel.comPanyData[compay].todayPrice }<br/>
${baseModel.comPanyData[compay].todayUpdown }<br/>
${baseModel.comPanyData[compay].todayRate }<br/>
${baseModel.comPanyData[compay].forigin }<br/>
${baseModel.comPanyData[compay].forignPersent }<br/>
</c:forEach>
</body>
</html>