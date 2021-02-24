<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container-fluid">
<div class="row">
<div class="col-md-5">
<form action="controller">
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Login</th>
      <th scope="col">Passport</th>
      <th scope="col">Total</th>
      <th scope="col">Blocked</th>
      <th scope="col">Role</th>
    </tr>
  </thead>
  <tbody>
  <c:set var="k" value="0"/>
  <c:forEach var="item" items="${usersList}">
    <tr>
      <th scope="row">${item.id}</th>
      <td>${item.login}</td>
      <td>${item.passport}</td>
      <td>${item.amount}</td>
      <td>${item.blocked}</td>
      <td><div class="offset-1"><button type="submit" name="userOrders" class="btn btn-md btn-primary sm-1 text-white" value="${k}">Orders</button></div></td>
      <c:set var="k" value="${k+1}"/>
    </tr>
    </c:forEach>
  </tbody>
</table>
<input type = "hidden" name="command" value="manageUsers"/>
</form>
</div>

<div class="col-md-2">
<c:if test="${not empty orderId}">
<h5 class="text-info">Create penalty</h5>
<form action="controller" method="post">
<div class="text-info">
<h7>&ensp;Order ID</h7>
</div>
<input name="orderId" type="text" class="text-danger font-weight-bold" size="30" readonly value="${orderId}"/>
<div class="text-info">
<h7>&ensp;Penalty sum</h7>
</div>
<input name="orderPenaltySumSec" type="text" class="text-danger font-weight-bold" size="30" required />
<div class="text-info">
<h7>&ensp;Description</h7>
</div>
<input name="orderPenaltyDescSec" type="text" class="text-danger font-weight-bold" size="30" required /><br></br>
<button type="submit" name="addOrderPenalty" class="btn btn-md btn-success sm-1 text-white" value="${orderId}">Confirm</button>
<input type = "hidden" name="command" value="manageUsers"/>
</form>
</c:if>
</div>

<c:if test="${not empty ordersList}">
<div class="col-md-5">
<form action="controller">
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">Client ID</th>
      <th scope="col">Client</th>
      <th scope="col">Car</th>
      <th scope="col">From</th>
      <th scope="col">To</th>
      <th scope="col">Cars</th>
      <th scope="col">Drivers</th>
    </tr>
  </thead>
  <tbody>
  <c:set var="k" value="0"/>
    <c:forEach var="item" items="${ordersList}">
    <tr>
      <th scope="row">${item.client.id}</th>
      <td>${item.client.passport}</td>
      <td>${item.carTotal.brand}&ensp;${item.carTotal.model}</td>
      <td>${item.rentFrom}</td>
      <td>${item.rentTo}</td>
      <td>${item.numbOfCars}</td>
      <td>${item.numbOfDrivers}</td>
      <td><div class="offset-1"><button type="submit" name="showOrderPenalty" class="btn btn-md btn-danger sm-1 text-white" value="${item.id}">Penalty</button></div></td>
    <c:set var="k" value="${k+1}"/>
    </tr>
    </c:forEach>
  </tbody>
</table>
<input type = "hidden" name="command" value="manageUsers"/>
</form>
</div>
</c:if>


</div>
</div>