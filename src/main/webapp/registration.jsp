<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>

<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<br><br><br><br><br>
<form action="controller" method="post">
<c:if test="${empty user}">
<div class="col-md-2 offset-5">
    <label for="Login"><fmt:message key="page.login"/></label>
    <input name="login" class="form-control" id="exampleInputEmail1" aria-describedby="loginHelp" placeholder='<fmt:message key="page.login"/>'>
    <small id="loginHelp" class="form-text text-muted">8-45 latin</small>
  </div>
  <div class="col-md-2 offset-5">
   <label for="inputPassword"><fmt:message key="page.password"/></label>
      <input type="password" name="password" class="form-control" id="inputPassword" placeholder='<fmt:message key="page.password"/>'>
</div>
<br>
<div class="col-md-2 offset-5">
   <label for="inputPassport"><fmt:message key="page.passport"/></label>
      <input  name="passport" class="form-control"   placeholder='<fmt:message key="page.optional"/>'>
</div>
</c:if>
<c:if test="${not empty user}">
<div class="col-md-2 offset-5">
   <label for="inputPassport"><fmt:message key="page.passport"/></label>
      <input  name="passport" class="form-control"   placeholder='<fmt:message key="page.optional"/>' required>
</div>
</c:if>
<br>
<div class="col-md-2 offset-5">
<button type="submit" class="btn btn-lg btn-success"><fmt:message key="page.sign_in"/></button>
<input type = "hidden" name="command" value="registration"/>
</form>
</body>
</html>