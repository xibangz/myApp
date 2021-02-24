<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container-fluid">
<div class="row">
<div class="col-md-9">
<form action="controller" method="post">
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">#</th>
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
      <th scope="row">${k+1}</th>
      <td>${item.login}</td>
      <td>${item.passport}</td>
      <td>${item.amount}</td>
      <td>${item.blocked}</td>
      <td>${role[item.roleId-1]}</td>
      <td><div class="offset-1"><button type="submit" name="unbanUser" class="btn btn-md btn-success sm-1 text-white" value="${item.id}">Unban</button></div></td>
      <td><div class="offset-1"><button type="submit" name="banUser" class="btn btn-md btn-danger sm-1 text-white" value="${item.id}">Ban</button></div></td>
      <c:set var="k" value="${k+1}"/>
    </tr>
    </c:forEach>
  </tbody>
</table>
<input type = "hidden" name="command" value="adminUsers"/>
</form>
</div>

<div class="col-md-3">
<h3 class="text-info">Add manager</h3>
<form action="controller" method="post">
<div class="text-info">
<h7>&ensp;Login</h7>
</div>
<input name="login" type="text" class="text-success font-weight-bold" required/>
<div class="text-info">
<h7>&ensp;Password</h7>
</div>
<input name="password" type="password" class="text-success font-weight-bold" value="${orderTotal.order.numbOfDrivers}" required/>
<div class="text-info">
<h7>&ensp;Passport</h7>
</div>
<input name="passport" type="text" class="text-success font-weight-bold" value="${orderTotal.order.numbOfDrivers}" required/><br></br>
<div class="offset-4"><button type="submit" name="addManager" class="btn btn-md btn-primary sm-1 text-white" value="true">Add</button></div>
</div>
</div>
<input type = "hidden" name="command" value="adminUsers"/>
</form>
</div>

</body>
</html>