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



<h7>&ensp;Start date</h7>
<br>
<c:if test="${order.rentFrom != null}">
&ensp;<input type="text" name="rentFrom" class="text-success font-weight-bold" readonly value="${order.rentFrom}"/>
</c:if>
<c:if test="${order.rentFrom == null}">
<input type="date" class="form-control" name="rentFrom" placeholder="Rent from" required/>
</c:if>
<br>
<h7>&ensp;End date</h7>
<br>
<c:if test="${order.rentTo != null}">
&ensp;<input  type="text" name="rentTo" class="text-success font-weight-bold" readonly value="${order.rentTo}"/>
</c:if>
<c:if test="${order.rentTo == null}">
<br><input type="date" class="form-control" name="rentTo" placeholder="Rent to" required/>
</c:if>

<br>&ensp;Number of drivers</br>
<c:if test="${order.numbOfDrivers >= 0}">
&ensp;<input  type="text" name="numbOfDrivers" class="text-success font-weight-bold" readonly value="${order.numbOfDrivers}"/>
</c:if>
<c:if test="${order.numbOfDrivers < 0}">
&ensp;<input type = "number" name="numbOfDrivers" placeholder="Number of drivers" min="0" required/>
</c:if>

<c:if test="${order.numbOfCars >= 1}">
<br>&ensp;Number of cars</br
<div>
&ensp;<input  type="text" name="numbOfCars" class="text-success font-weight-bold" readonly value="${order.numbOfCars}"/>
</c:if>
<c:if test="${order.numbOfCars < 1}">
<br>&ensp;Number of cars</br
<div>
&ensp;<input type = "number" name="numbOfCars" placeholder="Number of cars" min="1" required/>
</c:if>
</div>

<input type = "hidden" name="command" value="order"/>

<car_desc:desc></car_desc:desc>
<car_price:price>Total sum:<h2>${orderTotal.sum}uah/d</h2></car_price:price>
<h6 class="text-danger">${orderInfo.rentFromInfo}<br>${orderInfo.rentToInfo}<br>${orderInfo.numbOfDriversInfo}<br>${orderInfo.numbOfCarsInfo}<br>${orderInfo.userPassportInfo}</h6>

<div class="row">
<c:if test="${order.client.passport != null}">
<input type="text" class="text-success text-uppercase font-weight-bold" placeholder="Passport" readonly value="${order.client.passport}">
</c:if>
<c:if test="${order.client.passport == null}">
<a href="registration.jsp" class="btn btn-primary btn-lg active" role="button" aria-pressed="true" required>Enter passport</a>
</c:if>
<div class="offset-7">
<c:if test="${empty user}"><a href="login.jsp" class="btn btn-success btn-lg active" role="button" aria-pressed="true">Sing in</a></c:if>
<c:if test="${order.client.passport!=null}">
<c:if test="${orderTotal==null}">
<button type="submit" class="btn btn-lg btn-warning mt-4 text-white"><i class="icon-cart-add mr-2"></i>Check</button>
</c:if>
<c:if test="${orderTotal!=null}">
<button type="submit" class="btn btn-lg btn-success mt-4 text-white"><i class="icon-cart-add mr-2"></i>Order</button>
</c:if>
</c:if>
</div>
<button type="submit" name="back" class="btn btn-lg btn-primary mt-4 text-white" value="true"><i class="icon-cart-add mr-2"></i>Back</button>
</div>
</car:deploy>
</form>
</div>
</div>

</body>
</html>