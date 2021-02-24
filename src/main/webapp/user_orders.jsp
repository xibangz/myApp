<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>

<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Car</th>
      <th scope="col">Rental from</th>
      <th scope="col">Rental up to</th>
      <th scope="col">Number of cars</th>
      <th scope="col">Number of drivers</th>
      <th scope="col">Total sum</th>
      <th scope="col">Order status</th>
      <td><div class="offset-1"><button type="submit" name="carTotal" class="btn btn-md btn-primary sm-1 text-white" value="${notSorted[k].id}">Refresh</button></div></td>
    </tr>
  </thead>

  <tbody>
  <c:set var="k" value="0"/>
  <c:forEach var="item" items="${orderTotalList}">
    <tr>
      <th scope="row">${k+1}</th>
      <td>${carTotalMap[item.order.carTotal.id].brand}&ensp;${carTotalMap[item.order.carTotal.id].model}</td>
      <td>${item.order.rentFrom}</td>
      <td>${item.order.rentTo}</td>
      <td>${item.order.numbOfCars}</td>
      <td>${item.order.numbOfDrivers}</td>
      <td>${item.sum}</td>
      <td>${orderStatus[item.orderStatusId-1]}</td>
      <td><div class="offset-1"><button type="submit" name="carTotal" class="btn btn-md btn-success sm-1 text-white" value="${item.id}">Pay in</button></div></td>
      <c:set var="k" value="${k+1}"/>
    </tr>
    </c:forEach>
  </tbody>
</table>
</form>

</body>
</html>