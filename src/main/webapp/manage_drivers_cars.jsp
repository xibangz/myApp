<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>


<div class="container-fluid">
<div class="row">
<div class="col-md-6">
<form action="controller">
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Name</th>
      <th scope="col">License</th>
      <th scope="col">Driver cat.</th>
      <th scope="col">OK</th>
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
      <c:if test="${!item.ok}">
      <td type="text" class="text-danger font-weight-bold">${item.ok}</td>
      </c:if>
      <c:if test="${item.ok}">
      <td type="text" class="text-success font-weight-bold">${item.ok}</td>
      </c:if>
      <td><div class="offset-1"><button type="submit" name="driverNotOk" class="btn btn-md btn-danger sm-1 text-white" value="${item.id}">Not OK</button>
      <button type="submit" name="driverOk" class="btn btn-md btn-success sm-1 text-white" value="${item.id}">OK</button></div></td>
      <c:set var="k" value="${k+1}"/>
    </tr>
    </c:forEach>
  </tbody>
</table>
<input type = "hidden" name="command" value="manageDriversCars"/>
</form>
</div>

<div class="col-md-6">
<form action="controller">
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Numbers</th>
      <th scope="col">Brand</th>
      <th scope="col">Model</th>
      <th scope="col">Total ID</th>
      <th scope="col">OK</th>
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
      <c:if test="${!item.ok}">
      <td type="text" class="text-danger font-weight-bold">${item.ok}</td>
      </c:if>
      <c:if test="${item.ok}">
      <td type="text" class="text-success font-weight-bold">${item.ok}</td>
      </c:if>
      <td><div class="offset-1"><button type="submit" name="carNotOk" class="btn btn-md btn-danger sm-1 text-white" value="${k}">Not OK</button>
      <button type="submit" name="carOk" class="btn btn-md btn-success sm-1 text-white" value="${k}">OK</button></div></td>
      <c:set var="k" value="${k+1}"/>
    </tr>
    </c:forEach>
  </tbody>
</table>
<input type = "hidden" name="command" value="manageDriversCars"/>
</form>
</div>


</div>
</div>