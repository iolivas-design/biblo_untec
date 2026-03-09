<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jspf" %>
<head>
    <meta charset="UTF-8">
    <title>Salir - Biblioteca Digital UNTEC</title>
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<div class="container">
    <h1>Sesión finalizada</h1>
    <p>Has cerrado tu sesión correctamente.</p>
    <a href="index.jsp">Volver al inicio</a>
</div>
<%
    session.invalidate();
    response.sendRedirect("index.jsp");
%>
