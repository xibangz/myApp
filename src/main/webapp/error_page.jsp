<%@ page isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>


<c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
<c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>
<c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
<div class="container">
<c:if test="${not empty code}">
<div class="row justify-content-center">
<h1>Error code: ${code}</h1>
</div>
</c:if>
				
<c:if test="${not empty message}">
<div class="row justify-content-center">
<h1>Message: ${message}</h1>
</div>
</c:if>


<c:if test="${not empty errorMessage}">
<div class="row justify-content-center">
<h1 class="text-danger">${errorMessage}</h1>
</div>
</c:if>
</div>
</body>
</html>