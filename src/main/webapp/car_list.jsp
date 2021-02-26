<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>


<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container-fluid">

<div class="row justify-content-center">

<form action="controller">
<div class="dropleft">
  <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   <fmt:message key="product.sort"/>
  </button>
  <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
    <c:if test="${pageContent.sort ne 'defaultSort'}"><button class="dropdown-item" type="submit" name="sort" value="defaultSort"><fmt:message key="product.sort.default"/></button></c:if>
    <button <c:if test="${pageContent.sort eq 'priceLowest'}">class="dropdown-item disabled"</c:if><c:if test="${pageContent.sort ne 'priceLowest'}">class="dropdown-item"</c:if> type="submit" name="sort" value="priceLowest"><fmt:message key="product.sort.lowest_price"/></button>
    <button <c:if test="${pageContent.sort eq 'priceHighest'}">class="dropdown-item disabled"</c:if><c:if test="${pageContent.sort ne 'priceHighest'}">class="dropdown-item"</c:if> type="submit" name="sort" value="priceHighest"><fmt:message key="product.sort.highest_price"/></button>
    <button <c:if test="${pageContent.sort eq 'brand'}">class="dropdown-item disabled"</c:if><c:if test="${pageContent.sort ne 'brand'}">class="dropdown-item"</c:if> type="submit" name="sort" value="brand"><fmt:message key="product.sort.brand"/></button>
  </div>
</div>
<input type = "hidden" name="command" value="carsList"/>
</form>

<form action="controller">


<div class="dropright">
<button class="btn btn-warning dropdown-toggle" type="button" id="dropdownMenu3" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
<fmt:message key="product.filter.driver_cat"/>
</button>
<div class="dropdown-menu" aria-labelledby="dropdownMenu3">
<div class="text-info">
<h7>&ensp;<fmt:message key="product.filter.category"/></h7>
</div>

<c:forEach var="item" items="${pageContent.categories}">
<div class="form-check form-check-inline">
&ensp;<input class="form-check-input" type="checkbox" name="categoryFilter" value="${item.id}">
<label class="form-check-label">Category '${item.name}'</label>
</div>
</c:forEach>
<button type="submit" class="btn btn-warning mt-4 text-white"><i class="icon-cart-add mr-2"></i><fmt:message key="page.show"/></button>
</div>
</div>



<div class="dropright">
<button class="btn btn-warning dropdown-toggle" type="button" id="dropdownMenu3" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
<fmt:message key="product.filter.brand"/>
</button>
<div class="dropdown-menu pre-scrollable" aria-labelledby="dropdownMenu3">
<div class="text-info">
<h7>&ensp;<fmt:message key="product.filter.brand"/></h7>
</div>

<c:forEach var="item" items="${pageContent.brands}">
<div class="form-check form-check-inline">
  &ensp;<input class="form-check-input" type="checkbox" name="brandFilter" value="${item}">
  <label class="form-check-label">${item}</label>
</div>
</c:forEach>
<button type="submit" class="btn btn-warning mt-4 text-white"><i class="icon-cart-add mr-2"></i><fmt:message key="page.show"/></button>
</div>
</div>




<div class="dropright">
<button class="btn btn-warning dropdown-toggle" type="button" id="dropdownMenu3" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
<fmt:message key="product.filter.price"/>
</button>
<div class="dropdown-menu" aria-labelledby="dropdownMenu3">
<input type = "text" name="priceFromFilter" placeholder='<fmt:message key="product.filter.price.from"/>' min="0"/>
<input type = "text" name="priceToFilter" placeholder='<fmt:message key="product.filter.price.to"/>' min="0"/>
<button type="submit" class="btn btn-warning mt-4 text-white"><i class="icon-cart-add mr-2"></i><fmt:message key="page.show"/></button>
</div>
</div>

<input type = "hidden" name="command" value="carsList"/>
</form>
</div>



<form action="controller">
<div class="dropright">
  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   <fmt:message key="products.per.page"/>
  </button>
  <div class="dropdown-menu" aria-labelledby="dropdownMenu2">

    <button <c:if test="${pageContent.productsPerPage==4}">class="dropdown-item disabled"</c:if><c:if test="${pageContent.productsPerPage!=4}">class="dropdown-item"</c:if>class="dropdown-item" type="submit" name="productsPerPage" value="4">4</button>
    <button <c:if test="${pageContent.productsPerPage==8}">class="dropdown-item disabled"</c:if><c:if test="${pageContent.productsPerPage!=8}">class="dropdown-item"</c:if> type="submit" name="productsPerPage" value="8">8</button>
    <button <c:if test="${pageContent.productsPerPage==16}">class="dropdown-item disabled"</c:if><c:if test="${pageContent.productsPerPage!=16}">class="dropdown-item"</c:if> class="dropdown-item" type="submit" name="productsPerPage" value="16">16</button>
    <button <c:if test="${pageContent.productsPerPage==32}">class="dropdown-item disabled"</c:if><c:if test="${pageContent.productsPerPage!=32}">class="dropdown-item"</c:if> class="dropdown-item" type="submit" name="productsPerPage" value="32">32</button>
  </div>
</div>
<input type = "hidden" name="command" value="carsList"/>
</form>

<c:if test="${fn:length(pageContent.carsList)!=0}">
<form action="controller" method="post">
<div class="container-fluid">
<c:set var="k" value="0"/>
<car:deploy  productsPerPage="${pageContent.productsPerPage}" carListSize="${fn:length(pageContent.carsList)}">
<car_ph:photo>"${pageContent.carsList[k].photo}"</car_ph:photo>
<car_name:name><p class="text-info">&ensp;${pageContent.carsList[k].brand}&ensp;${pageContent.carsList[k].model}</p></car_name:name>
<h7 class="text font-weight-bold">&ensp;<fmt:message key="products.start_date"/></h7>
<input id="${pageContent.carsList[k].id}" type="date" class="form-control" name="${pageContent.carsList[k].id}rentFrom" placeholder=""/>
<h7 class="text font-weight-bold">&ensp;<fmt:message key="products.end_date"/></h7>
<br><input id="${pageContent.carsList[k].id}+1" type="date" class="form-control" name="${pageContent.carsList[k].id}rentTo" placeholder=""/>
<div>
<h7 class="text font-weight-bold">&ensp;<fmt:message key="products.numb_of_drivers"/></h7>
&ensp;<input type = "number" name="${pageContent.carsList[k].id}numbOfDrivers" placeholder='<fmt:message key="products.numb_of_drivers"/>' value="0" min="0"/>
<br><h7 class="text font-weight-bold">&ensp;<fmt:message key="products.numb_of_cars"/></h7>
&ensp;<input type = "number" name="${pageContent.carsList[k].id}numbOfCars" placeholder='<fmt:message key="products.numb_of_cars"/>' value="1" min="1"/>
<input type = "hidden" name="command" value="order"/>
</div>
<car_desc:desc></car_desc:desc>
<car_price:price><p class="text-success"><fmt:message key="products.price"/>:</p><h4>${pageContent.carsList[k].price} <fmt:message key="products.price.value"/></h4></car_price:price><br><br><br><br><br><br>
<c:if test="${empty user}"><a href="login.jsp" class="btn btn-success btn-lg active" role="button" aria-pressed="true"><fmt:message key="page.sign_in"/></a></c:if>
<c:if test="${not empty user}">
<button type="submit" name="carTotal" class="btn btn-warning mt-4 text-white" value="${pageContent.carsList[k].id}"><i class="icon-cart-add mr-2"></i><fmt:message key="page.to_order"/></button>
</c:if>
</div>
<c:set var="k" value="${k+1}"/>
</car:deploy>
</div>
</div>
</div>
</form>
</c:if>
<c:if test="${fn:length(pageContent.carsList)==0}"><div class="row justify-content-center"><h1 class="text-danger"><fmt:message key="page.no_products"/></h1></div></c:if>

<c:set var="k" value="${1}"/>
<pagination:pagin countProducts="${pageContent.countOfProducts}" productsPerPage="${pageContent.productsPerPage}">
<li <c:if test="${pageContent.pageId==k}"> class="page-item disabled"</c:if> <c:if test="${pageContent.pageId!=k}">class="page-item active"</c:if> ><a id="page" class="page-link" href="#">${k}</a></li>
<c:set var="k" value="${k+1}"/>
</pagination:pagin>

</body>
</html>