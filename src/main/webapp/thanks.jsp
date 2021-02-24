<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<br><br><br><br><br>
<div class="col-md-2 offset-5"><h1>SUCCESS ${hello}</h1></div>
</body>
</html>

<form action="controller" method="post">
<div class="container-fluid">
<c:set var="k" value="0"/>
<car:deploy size="4">
<car_ph:photo>"http://images.vfl.ru/ii/1613014509/51da455b/33290232.jpg"</car_ph:photo>
<car_name:name><p class="text-info">&ensp;${notSorted[k].brand}&ensp;${notSorted[k].model}</p></car_name:name>
<h7>&ensp;Start date</h7>
<input id="${notSorted[k].id}" type="date" class="form-control" name="${notSorted[k].id}rentFrom" placeholder="" />
<h7>&ensp;End date</h7>
<br><input id="${notSorted[k].id}+1" type="date" class="form-control" name="${notSorted[k].id}rentTo" placeholder="" />
<div>
<br>
&ensp;<input type = "number" name="${notSorted[k].id}numbOfDrivers" placeholder="Number of drivers" min="0"/>
<br></br>
&ensp;<input type = "number" name="${notSorted[k].id}numbOfCars" placeholder="Number of cars" min="0"/>
<input type = "hidden" name="command" value="order"/>
</div>
<car_desc:desc></car_desc:desc>
<car_price:price>Price:<h4>${notSorted[k].price}uah/d</h4></car_price:price>
<c:if test="${empty user}"><a href="login.jsp" class="btn btn-success btn-lg active" role="button" aria-pressed="true">Sing in</a></c:if>
<c:if test="${not empty user}">
<button type="submit" name="carTotal" class="btn btn-warning mt-4 text-white" value="${notSorted[k].id}"><i class="icon-cart-add mr-2"></i> Order</button>
</c:if>
</div>
<c:set var="k" value="${k+1}"/>
</car:deploy>
</div>
</div>
</div>
</form>