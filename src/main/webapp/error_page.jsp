<%@ page isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<tr >
<td class="content">

<c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
<c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>

<c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
				
<c:if test="${not empty code}">
<h3>Error code: ${code}</h3>
</c:if>
				
<c:if test="${not empty message}">
<h3>Message: ${message}</h3>
</c:if>


<div class="container">
<c:if test="${not empty errorMessage}">
<div class="row justify-content-center">
<h1 class="text-danger">${errorMessage}</h1>
</div>
</c:if>
</div>
</td>
</tr>
</table>
</body>
</html>