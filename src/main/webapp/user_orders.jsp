<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>

<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<form action="controller" method="post">
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col"><fmt:message key="orders.car_value"/></th>
      <th scope="col"><fmt:message key="orders.rental_from"/></th>
      <th scope="col"><fmt:message key="orders.rental_up"/></th>
      <th scope="col"><fmt:message key="products.numb_of_cars"/></th>
      <th scope="col"><fmt:message key="products.numb_of_drivers"/></th>
      <th scope="col"><fmt:message key="orders.status"/></th>
      <th scope="col"><fmt:message key="orders.sum"/></th>
    </tr>
  </thead>

  <tbody>
  <c:set var="k" value="0"/>
  <c:forEach var="item" items="${orderTotalList}">
    <tr>
      <th scope="row">${k+1}</th>
      <td>${item.order.carTotal.brand}&ensp;${item.order.carTotal.model}</td>
      <td>${item.order.rentFrom}</td>
      <td>${item.order.rentTo}</td>
      <td>${item.order.numbOfCars}</td>
      <td>${item.order.numbOfDrivers}</td>

      <c:if test="${item.penalty}">
      <td type="text" class="text-danger font-weight-bold">${item.penalty}</td>
      </c:if>
      <c:if test="${!item.penalty}">
      <td type="text" class="text-success font-weight-bold">${item.penalty}</td>
      </c:if>

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

      <td><button type="submit" name="status" class="btn btn-md btn-success sm-1 text-white" value="${k}"
      <c:if test="${item.orderStatusId!=2}">disabled</c:if>><fmt:message key="orders.pay_in"/></button></td>

      <c:set var="k" value="${k+1}"/>
    </tr>
    </c:forEach>
  </tbody>
</table>
<input type = "hidden" name="command" value="userOrders"/>
</form>

</body>
</html>