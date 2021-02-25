<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>


<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="row">
<div class="col-md-12 offset-3">

<form action="controller" method="post">
<car:deploy  productsPerPage="1" carListSize="1">
<car_ph:photo>"http://images.vfl.ru/ii/1613014509/51da455b/33290232.jpg"</car_ph:photo>
<car_name:name><p class="text-info">&ensp;${order.carTotal.brand}&ensp;${order.carTotal.model}</p></car_name:name>



<h7>&ensp;<fmt:message key="products.start_date"/></h7>
<br>
<c:if test="${order.rentFrom != null}">
&ensp;<input type="text" name="rentFrom" class="text-success font-weight-bold" readonly value="${order.rentFrom}"/>
</c:if>
<c:if test="${order.rentFrom == null}">
<input type="date" class="form-control" name="rentFrom" placeholder='<fmt:message key="orders.rental_from"/>' required/>
</c:if>
<br>
<h7>&ensp;<fmt:message key="products.end_date"/></h7>
<br>
<c:if test="${order.rentTo != null}">
&ensp;<input  type="text" name="rentTo" class="text-success font-weight-bold" readonly value="${order.rentTo}"/>
</c:if>
<c:if test="${order.rentTo == null}">
<br><input type="date" class="form-control" name="rentTo" placeholder='<fmt:message key="orders.rental_up"/>' required/>
</c:if>

<br>&ensp;<fmt:message key="products.numb_of_drivers"/></br>
<c:if test="${order.numbOfDrivers >= 0}">
&ensp;<input  type="text" name="numbOfDrivers" class="text-success font-weight-bold" readonly value="${order.numbOfDrivers}"/>
</c:if>
<c:if test="${order.numbOfDrivers < 0}">
&ensp;<input type = "number" name="numbOfDrivers" placeholder='<fmt:message key="products.numb_of_drivers"/>' min="0" required/>
</c:if>

<c:if test="${order.numbOfCars >= 1}">
<br>&ensp;<fmt:message key="products.numb_of_cars"/></br
<div>
&ensp;<input  type="text" name="numbOfCars" class="text-success font-weight-bold" readonly value="${order.numbOfCars}"/>
</c:if>
<c:if test="${order.numbOfCars < 1}">
<br>&ensp;Number of cars</br
<div>
&ensp;<input type = "number" name="numbOfCars" placeholder='<fmt:message key="products.numb_of_cars"/>' min="1" required/>
</c:if>
</div>

<input type = "hidden" name="command" value="order"/>

<car_desc:desc></car_desc:desc>
<car_price:price><fmt:message key="orders.sum"/>:<h2>${orderTotal.sum}<fmt:message key="products.price.value"/></h2></car_price:price>
<h6 class="text-danger">${orderInfo.rentFromInfo}<br>${orderInfo.rentToInfo}<br>${orderInfo.numbOfDriversInfo}<br>${orderInfo.numbOfCarsInfo}<br>${orderInfo.userPassportInfo}</h6>

<div class="row">
<c:if test="${order.client.passport != null}">
<input type="text" class="text-success text-uppercase font-weight-bold" placeholder='<fmt:message key="page.passport"/>' readonly value="${order.client.passport}">
</c:if>
<c:if test="${order.client.passport == null}">
<a href="registration.jsp" class="btn btn-primary btn-lg active" role="button" aria-pressed="true" required><fmt:message key="page.enter_passport"/></a>
</c:if>
<div class="offset-7">
<c:if test="${empty user}"><a href="login.jsp" class="btn btn-success btn-lg active" role="button" aria-pressed="true"><fmt:message key="page.sign_in"/></a></c:if>
<c:if test="${order.client.passport!=null}">
<c:if test="${orderTotal==null}">
<button type="submit" class="btn btn-lg btn-warning mt-4 text-white"><i class="icon-cart-add mr-2"></i><fmt:message key="page.check"/></button>
</c:if>
<c:if test="${orderTotal!=null}">
<button type="submit" class="btn btn-lg btn-success mt-4 text-white"><i class="icon-cart-add mr-2"></i><fmt:message key="page.confirm"/></button>
</c:if>
</c:if>
</div>
<button type="submit" name="back" class="btn btn-lg btn-primary mt-4 text-white" value="true"><i class="icon-cart-add mr-2"></i><fmt:message key="page.back"/></button>
</div>
</car:deploy>
</form>
</div>
</div>

</body>
</html>