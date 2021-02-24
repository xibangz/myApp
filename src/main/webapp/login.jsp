<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<br><br><br><br><br>

<form action="controller" method="post">

<div class="col-md-2 offset-5">
<label for="Login">Login</label>
<input name="login" class="form-control" id="exampleInputEmail1" aria-describedby="loginHelp" placeholder="Enter login">
<small id="loginHelp" class="form-text text-muted">8-45 latin</small>
</div>

<div class="col-md-2 offset-5">
<label for="inputPassword">Password</label>
<input type="password" name="password" class="form-control" id="inputPassword" placeholder="Password">
</div><br>

<div class="col-md-2 offset-5">
<button type="submit" class="btn btn-lg btn-success">Sign in</button>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
<a href="registration.jsp" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Registration</a>
</div>
<input type = "hidden" name="command" value="login"/>
</form>
</body>
</html>