<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container-fluid">
<div class="row">

<div class="col-md-5">
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Name</th>
      <th scope="col">Price</th>
      <th scope="col">Quantity</th>
    </tr>
  </thead>
  <form action="controller" method="post">
  <tbody>
  <c:set var="k" value="0"/>
  <c:forEach var="item" items="${driverCatsList}">
    <tr>
      <th scope="row">${item.id}</th>
      <td>${item.name}</td>
      <td>${item.price}</td>
      <td>${item.quantity}</td>
      <td><div class="offset-1"><button type="submit" name="showUpdate" class="btn btn-md btn-primary sm-1 text-white" value="${k}">Update</button></div></td>
      <td><div class="offset-1"><button type="submit" name="deleteDriverCat" class="btn btn-md btn-danger sm-1 text-white" value="${item.id}">Delete</button></div></td>
      <c:set var="k" value="${k+1}"/>
    </tr>
    </c:forEach>
  </tbody>
</table>
<input type = "hidden" name="command" value="adminDrivers"/>
</form>
</div>


<div class="col-md-1">

<h5 class="text-info">Add category</h5>
<form action="controller" method="post">
<div class="text-info">
<h7>&ensp;Name</h7>
</div>
<input name="driverCatName" type="text" class="text-success font-weight-bold" size="12" required/>
<div class="text-info">
<h7>&ensp;Price</h7>
</div>
<input name="driverCatPrice" type="text" class="text-success font-weight-bold" size="12" required/><br></br>
<div class="offset-4"><button id="3" type="submit" name="addDriverCat" class="btn btn-md btn-success sm-1 text-white" value="true">Add</button></div>
<input type = "hidden" name="command" value="adminDrivers"/>
</form>

<c:if test="${not empty updateItem}">

<h5 class="text-info">Update category</h5>
<form action="controller" method="get">
<div class="text-info">
<h7>&ensp;ID</h7>
</div>
<input name="driverCatId" type="text" class="text-danger font-weight-bold" size="12" readonly value="${updateItem.id}"/>
<div class="text-info">
<h7>&ensp;Price</h7>
</div>
<input name="driverCatPrice" type="text" class="text-danger font-weight-bold" size="12" required value="${updateItem.price}"/><br></br>
<div class="offset-4"><button type="submit" name="updateDriverCat" class="btn btn-md btn-success sm-1 text-white" value="${true}">Confirm</button></div>
<input type = "hidden" name="command" value="adminDrivers"/>
</form>
</c:if>

</div>



<div class="col-md-5">
<form action="controller" method="post">
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Name</th>
      <th scope="col">License</th>
      <th scope="col">Driver cat.</th>
      <th scope="col">Is OK</th>
    </tr>
  </thead>
  <tbody>
  <c:set var="k" value="0"/>
  <c:forEach var="item" items="${driversList}">
    <tr>
      <th scope="row">${item.id}</th>
      <td>${item.name}</td>
      <td>${item.driverLicense}</td>
      <td>${item.driverCat.name}</td>
      <td>${item.ok}</td>
      <td><div class="offset-1"><button id="2" type="submit" name="deleteDriver" class="btn btn-md btn-danger sm-1 text-white" value="${item.id}">Delete</button></div></td>
      <c:set var="k" value="${k+1}"/>
    </tr>
    </c:forEach>
  </tbody>
</table>
<input type = "hidden" name="command" value="adminDrivers"/>
</form>
</div>




<div class="col-md-1">

<h5 class="text-info">Add driver</h5>
<form action="controller" method="post">
<div class="text-info">
<h7>&ensp;Name</h7>
</div>
<input name="driverName" type="text" class="text-success font-weight-bold" size="10" required/>
<div class="text-info">
<h7>&ensp;License</h7>
</div>
<input name="driverLicense" type="text" class="text-success font-weight-bold" size="10" required/>
<div class="text-info">
<h7>&ensp;Driver cat.</h7>
</div>
<input name="driverCat" type="text" class="text-success font-weight-bold" size="10" required/><br></br>
<div class="offset-4"><button id="1" type="submit" name="addDriver" class="btn btn-md btn-success sm-1 text-white" value="true">Add</button></div>
<input type = "hidden" name="command" value="adminDrivers"/>
</form>
</div>




</div>
</div>



</body>
</html>