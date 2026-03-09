<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inicio de Sesión - Biblioteca Digital UNTEC</title>
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Iniciar Sesión</h1>
        
        <c:if test="${not empty error}">
            <div class="error">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <form action="login" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="password">Contraseña:</label>
                <input type="password" id="password" name="password" required>
            </div>

            <button type="submit">Entrar</button>
        </form>

        <div class="link-container">
            ¿No tienes cuenta? <a href="registro.jsp">Regístrate aquí</a>
        </div>

        <div class="back-link">
            <a href="index.jsp">← Volver al inicio</a>
        </div>
    </div>
</body>
</html>
