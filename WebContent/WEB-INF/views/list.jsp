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
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(kospi);

function kospi() {
  var data = google.visualization.arrayToDataTable([
    ['날자', 'KOSPI'],
    <c:forEach  var="kospi" items="${KospiValue }" varStatus="status">
    ['${kospi.date}', ${fn:replace(kospi.kospi,',','')}]<c:if test="${!status.last}">,</c:if>
    </c:forEach >
  ]);

  var options = {
    title: 'KOSPI정보',
    curveType: 'function',
    legend: { position: 'bottom' }
  };

  var chart = new google.visualization.LineChart(document.getElementById('kospi'));

  chart.draw(data, options);
}
<c:forEach var="company" items="${companyList }">
google.charts.setOnLoadCallback(get_${company.key});
function get_${company.key}() {
    var data = google.visualization.arrayToDataTable([
        ['날자', '주가'],
        <c:forEach var="sort" items="${dateSort}" varStatus="status">
        [ '${sort}', ${fn:replace(company.value[sort].todayPrice,',','')} ]<c:if test="${!status.last}">,</c:if>
        </c:forEach>
      ]);
    var options = {
        title: '${kospiKeys[company.key]}',
        curveType: 'function',
        legend: { position: 'bottom' }
    };
    var chart = new google.visualization.LineChart(document.getElementById('${company.key}'));

    chart.draw(data, options);
}
</c:forEach>

/* 외국인 */
<c:forEach var="company" items="${companyList }">
google.charts.setOnLoadCallback(get_forigin_${company.key});
function get_forigin_${company.key}() {
    var data = google.visualization.arrayToDataTable([
        ['날자', '외국인 수급량'],
        <c:forEach var="sort" items="${dateSort}" varStatus="status">
        [ '${sort}', ${fn:replace(company.value[sort].forigin,',','')} ]<c:if test="${!status.last}">,</c:if>
        </c:forEach>
      ]);
    var options = {
        title: '${kospiKeys[company.key]} 외국인 수급량',
        curveType: 'function',
        legend: { position: 'bottom' }
    };
    var chart = new google.visualization.LineChart(document.getElementById('for_${company.key}'));

    chart.draw(data, options);
}
</c:forEach>

/* 거래량 */
google.charts.load('current', {'packages':['bar']})
<c:forEach var="company" items="${companyList }">
google.charts.setOnLoadCallback(get_volum_${company.key});
function get_volum_${company.key}() {
     var data = google.visualization.arrayToDataTable([
         ['날짜', '거래량'],
         <c:forEach var="sort" items="${dateSort}" varStatus="status">
         ['${sort}', ${fn:replace(company.value[sort].tradingVolume,',','')} ],
         </c:forEach>
         ]);
     
     var options = {
         chart: {
           title: '${kospiKeys[company.key]} 거래량',
           subtitle: '거래거래량',
         }
       };
     var chart = new google.charts.Bar(document.getElementById('volum_${company.key}'));
     chart.draw(data, google.charts.Bar.convertOptions(options));
}
</c:forEach>
</script>
</head>
<body>
<%-- 시작 <br/>
<c:forEach var="kospi" items="${KospiValue }">
${kospi.date}<br/>
${kospi.kospi}<br/>
</c:forEach>
끝
<br/>
<br/>
<c:forEach var="company" items="${companyList }">
${company.companyName}<br/>
${company.companyCode}<br/>
${company.date}<br/>
${company.todayPrice}<br/>
${company.todayUpdown}<br/>
${company.todayRate}<br/>
${company.forigin}<br/>
${company.forignPersent}<br/>
</c:forEach> --%>

</body>
월 - 금 22시 자동 데이터 수집되도록 설계
<div id="kospi" style="width: 100%; height: 500px"></div>
<c:forEach var="key" items="${keysSort }">
<div id="${key}" style="width:  100%; height: 500px"></div>
<div id="for_${key}" style="width:  100%; height: 500px"></div>
<div id="volum_${key}" style="width:  100%; height: 500px"></div>
</c:forEach>
</html>