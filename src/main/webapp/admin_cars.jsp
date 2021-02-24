<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container-fluid">
<div class="row">
<div class="col-md-5">
<form action="controller" method="get">
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Brand</th>
      <th scope="col">Model</th>
      <th scope="col">Price</th>
      <th scope="col">Quantity</th>
      <th scope="col">Driver cat.</th>
    </tr>
  </thead>
  <tbody>
  <c:set var="k" value="0"/>
  <c:forEach var="item" items="${carTotalsList}">
    <tr>
      <th scope="row">${item.id}</th>
      <td>${item.brand}</td>
      <td>${item.model}</td>
      <td>${item.price}</td>
      <td>${item.quantity}</td>
      <td>${item.driverCat.name}</td>
      <td><div class="offset-1"><button type="submit" name="showUpdate" class="btn btn-md btn-primary sm-1 text-white" value="${k}">Update</button></div></td>
      <td><div class="offset-1"><button type="submit" name="deleteCarTotal" class="btn btn-md btn-danger sm-1 text-white" value="${item.id}">Delete</button></div></td>
      <c:set var="k" value="${k+1}"/>
    </tr>
    </c:forEach>
  </tbody>
</table>
<input type = "hidden" name="command" value="adminCars"/>
</form>
</div>

<div class="col-md-1">

<h5 class="text-info">Add total</h5>
<form action="controller" method="post">
<div class="text-info">
<h7>&ensp;Brand</h7>
</div>
<input name="totalBrand" type="text" class="text-success font-weight-bold" size="12" required/>
<div class="text-info">
<h7>&ensp;Model</h7>
</div>
<input name="totalModel" type="text" class="text-success font-weight-bold" size="12" required/>
<div class="text-info">
<h7>&ensp;Price</h7>
</div>
<input name="totalPrice" type="text" class="text-success font-weight-bold" size="12" required/>
<div class="text-info">
<h7>&ensp;Driver cat.</h7>
</div>
<input name="totalDriver" type="text" class="text-success font-weight-bold" size="12" required/>
<div class="text-info">
<h7>&ensp;Photo</h7>
</div>
<input name="totalPhoto" type="text" class="text-danger font-weight-bold" size="12" required/><br></br>
<div class="offset-4"><button type="submit" name="addCarTotal" class="btn btn-md btn-success sm-1 text-white" value="true">Add</button></div>
<input type = "hidden" name="command" value="adminCars"/>
</form>


<c:if test="${not empty updateItemTotal}">
<h5 class="text-info">Update total</h5>
<form action="controller" method="post">
<div class="text-info">
<h7>&ensp;ID</h7>
</div>
<input name="totalId" type="text" class="text-danger font-weight-bold" size="12" readonly value="${updateItemTotal.id}"/>
<div class="text-info">
<h7>&ensp;Brand</h7>
</div>
<input name="totalBrand" type="text" class="text-danger font-weight-bold" size="12" required value="${updateItemTotal.brand}"/>
<div class="text-info">
<h7>&ensp;Model</h7>
</div>
<input name="totalModel" type="text" class="text-danger font-weight-bold" size="12" required value="${updateItemTotal.model}"/>
<div class="text-info">
<h7>&ensp;Price</h7>
</div>
<input name="totalPrice" type="text" class="text-danger font-weight-bold" size="12" required value="${updateItemTotal.price}"/>
<div class="text-info">
<h7>&ensp;Driver cat.</h7>
</div>
<input name="totalDriver" type="text" class="text-danger font-weight-bold" size="12" required value="${updateItemTotal.driverCat.name}"/>
<div class="text-info">
<h7>&ensp;Photo</h7>
</div>
<input name="totalPhoto" type="text" class="text-danger font-weight-bold" size="12" required value="${updateItemTotal.photo}"/><br></br>
<div class="offset-4"><button type="submit" name="updateCarTotal" class="btn btn-md btn-success sm-1 text-white" value="true">Confirm</button></div>
<input type = "hidden" name="command" value="adminCars"/>
</form>
</c:if>

</div>


<div class="col-md-5">
<form action="controller" method="post">
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Numbers</th>
      <th scope="col">Brand</th>
      <th scope="col">Model</th>
      <th scope="col">Total ID</th>
      <th scope="col">Is OK</th>
    </tr>
  </thead>
  <tbody>
  <c:set var="k" value="0"/>
  <c:forEach var="item" items="${carsList}">
    <tr>
      <th scope="row">${item.id}</th>
      <td>${item.numbers}</td>
      <td>${item.brand}</td>
      <td>${item.model}</td>
      <td>${item.carTotal}</td>
      <td>${item.ok}</td>
      <td><div class="offset-1"><button type="submit" name="deleteCar" class="btn btn-md btn-danger sm-1 text-white" value="${item.id}">delete</button></div></td>
      <c:set var="k" value="${k+1}"/>
    </tr>
    </c:forEach>
  </tbody>
</table>
<input type = "hidden" name="command" value="adminCars"/>
</form>
</div>


<div class="col-md-1">

<h5 class="text-info">Add car</h5>
<form action="controller" method="post">
<div class="text-info">
<h7>&ensp;Brand</h7>
</div>
<input name="carBrand" type="text" class="text-success font-weight-bold" size="10" required/>
<div class="text-info">
<h7>&ensp;Model</h7>
</div>
<input name="carModel" type="text" class="text-success font-weight-bold" size="10" required/>
<div class="text-info">
<h7>&ensp;Numbers</h7>
</div>
<input name="carNumbers" type="text" class="text-success font-weight-bold" size="10" required/>
<div class="text-info">
<h7>&ensp;Total ID</h7>
</div>
<input name="carTotalId" type="text" class="text-success font-weight-bold" size="10" required/><br></br>
<div class="offset-4"><button type="submit" name="addCar" class="btn btn-md btn-success sm-1 text-white" value="true">Add</button></div>
<input type = "hidden" name="command" value="adminCars"/>
</form>
</div>




</div>
</div>



</body>
</html>