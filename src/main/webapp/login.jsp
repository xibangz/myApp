<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<br><br><br><br><br>

<form action="controller" method="post">

<div class="col-md-2 offset-5">
<label for="Login"><fmt:message key="page.login"/></label>
<input name="login" class="form-control" id="exampleInputEmail1" aria-describedby="loginHelp" placeholder='<fmt:message key="page.login"/>'>
<small id="loginHelp" class="form-text text-muted">8-45 latin</small>
</div>

<div class="col-md-2 offset-5">
<label for="inputPassword"><fmt:message key="page.password"/></label>
<input type="password" name="password" class="form-control" id="inputPassword" placeholder='<fmt:message key="page.password"/>'>
</div><br>

<div class="col-md-2 offset-5">
<button type="submit" class="btn btn-lg btn-success"><fmt:message key="page.sign_in"/></button>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
<a href="registration.jsp" class="btn btn-primary btn-lg active" role="button" aria-pressed="true"><fmt:message key="page.registration"/></a>
<br></br>
<div class="dropright">
<button class="btn btn-warning btn-sm dropdown-toggle" type="button" id="dropdownMenu3" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
<fmt:message key="page.locale"/>
</button>
<div class="dropdown-menu pre-scrollable" aria-labelledby="dropdownMenu3">
<div class="text-info">
</div>
<div class="form-check form-check-inline">
  &ensp;<input class="form-check-input" type="checkbox" name="localeName" value="en">
  <label class="form-check-label">en</label>
</div>
<div class="form-check form-check-inline">
  &ensp;<input class="form-check-input" type="checkbox" name="localeName" value="ru" >
  <label class="form-check-label">ru</label>
</div>
</div>
</div>
</div>
<input type = "hidden" name="command" value="login"/>
</form>
</body>
</html>