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
      <th scope="col">Order date</th>
      <th scope="col">Total sum</th>
      <th scope="col">Order status</th>
      <th scope="col">Penalty</th>
    </tr>
  </thead>

  <tbody>
  <c:set var="k" value="0"/>
  <c:forEach var="item" items="${orderTotalsList}">
    <tr>
      <th scope="row">${item.id}</th>
      <td>${item.orderDate}</td>
      <td>${item.sum}</td>

      <c:if test="${item.orderStatusId==3}">
      <td type="text" class="text-danger font-weight-bold">${orderStatus[item.orderStatusId-1]}</td>
      </c:if>
      <c:if test="${item.orderStatusId==4}">
      <td type="text" class="text-success font-weight-bold">${orderStatus[item.orderStatusId-1]}</td>
      </c:if>
      <c:if test="${item.orderStatusId==1}">
      <td type="text" class="text-warning font-weight-bold">${orderStatus[item.orderStatusId-1]}</td>
      </c:if>
      <c:if test="${item.orderStatusId==2}">
      <td type="text" class="text-info font-weight-bold">${orderStatus[item.orderStatusId-1]}</td>
      </c:if>

      <c:if test="${item.penalty}">
      <td type="text" class="text-danger font-weight-bold">${item.penalty}</td>
      </c:if>
      <c:if test="${!item.penalty}">
      <td type="text" class="text-success font-weight-bold">${item.penalty}</td>
      </c:if>
      <td><div class="offset-1"><button type="submit" name="showReject" class="btn btn-md btn-danger sm-1 text-white" value="${k}">Reject</button>
      <button type="submit" name="confirmStatus" class="btn btn-md btn-success sm-1 text-white" value="${item.id}">Confirm</button><br></br>
      <button type="submit" name="showUpdate" class="btn btn-md btn-primary sm-1 text-white" value="${item.id}">Details</button></div></td>
      <c:set var="k" value="${k+1}"/>
    </tr>
    </c:forEach>
  </tbody>
</table>
<input type = "hidden" name="command" value="manageOrders"/>
</form>
</div>

<div class="col-md-2">
<c:if test="${not empty updateReject}">
<h5 class="text-info">Update category</h5>
<form action="controller" method="post">
<div class="text-info">
<h7>&ensp;ID</h7>
</div>
<input name="orderTotalId" type="text" class="text-danger font-weight-bold" size="30" readonly value="${updateReject.id}"/>
<div class="text-info">
<h7>&ensp;Order date</h7>
</div>
<input name="orderTotalDate" type="text" class="text-danger font-weight-bold" size="30" readonly value="${updateReject.orderDate}"/>
<div class="text-info">
<h7>&ensp;Order sum</h7>
</div>
<input name="orderTotalSum" type="text" class="text-danger font-weight-bold" size="30" readonly value="${updateReject.sum}"/>
<div class="text-info">
<h7>&ensp;Penalty</h7>
</div>
<input name="orderTotalPenalty" type="text" class="text-danger font-weight-bold" size="30" readonly value="${updateReject.penalty}"/>
<div class="text-info">
<h7>&ensp;Description</h7>
</div>
<input name="orderTotalDesc" type="text" class="text-danger font-weight-bold" size="30" required value="${updateReject.description}"/><br></br>
<button type="submit" name="rejectStatus" class="btn btn-md btn-success sm-1 text-white" value="${updateReject.id}">Confirm</button>
<input type = "hidden" name="command" value="manageOrders"/>
</form>
</c:if>


<c:if test="${not empty showPenalty}">
<h5 class="text-info">Create penalty</h5>
<form action="controller" method="post">
<div class="text-info">
<h7>&ensp;Order ID</h7>
</div>
<input name="orderId" type="text" class="text-danger font-weight-bold" size="30" readonly value="${updateItem.order.id}"/>
<div class="text-info">
<h7>&ensp;Penalty sum</h7>
</div>
<input name="orderPenaltySum" type="text" class="text-danger font-weight-bold" size="30" required />
<div class="text-info">
<h7>&ensp;Description</h7>
</div>
<input name="orderPenaltyDesc" type="text" class="text-danger font-weight-bold" size="30" required /><br></br>
<button type="submit" name="addPenalty" class="btn btn-md btn-success sm-1 text-white" value="${updateItem.order.id}">Confirm</button>
<input type = "hidden" name="command" value="manageOrders"/>
</form>
</c:if>



</div>


<c:if test="${not empty updateItem}">
<div class="col-md-5">
<form action="controller" method="post">
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Client</th>
      <th scope="col">Car</th>
      <th scope="col">From</th>
      <th scope="col">To</th>
      <th scope="col">Cars</th>
      <th scope="col">Drivers</th>
    </tr>
  </thead>

  <tbody>
    <tr>
      <th scope="row">${updateItem.order.id}</th>
      <td>${updateItem.order.client.passport}</td>
      <td>${updateItem.order.carTotal.brand}&ensp;${updateItem.order.carTotal.model}</td>
      <td>${updateItem.order.rentFrom}</td>
      <td>${updateItem.order.rentTo}</td>
      <td>${updateItem.order.numbOfCars}</td>
      <td>${updateItem.order.numbOfDrivers}</td>
      <td><div class="offset-1"><button type="submit" name="showPenalty" class="btn btn-md btn-danger sm-1 text-white" value="true">Penalty</button></div></td>
    </tr>
  </tbody>
</table>
<input type = "hidden" name="command" value="manageOrders"/>
</form>
</div>
</c:if>

</div>
</div>

</body>
</html>