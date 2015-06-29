<%@page import="org.springframework.security.core.context.SecurityContext" %>
<%@page import="com.l1j5.web.common.mvc.model.dto.L1j5User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
response.setDateHeader("Expires", 0);

if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null){
L1j5User userInfo = (L1j5User)((SecurityContext)session
		.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getPrincipal();
request.setAttribute("userInfo", userInfo);
}
%>

