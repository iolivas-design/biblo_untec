<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
    <h1>Sesión finalizada</h1>
    <p>Has cerrado tu sesión correctamente.</p>
    <a href="index.jsp" class="btn btn-secondary mt-3">Volver al inicio</a>
</div>
<%
    session.invalidate();
    response.sendRedirect("index.jsp");
%>
