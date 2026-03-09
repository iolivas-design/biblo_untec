<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro - Biblioteca Digital UNTEC</title>
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Crear Cuenta</h1>
        
        <c:if test="${not empty error}">
            <div class="error">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <form action="registro" method="post">
            <div class="form-group">
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" required>
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="password">Contraseña:</label>
                <input type="password" id="password" name="password" required minlength="6">
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirmar Contraseña:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required minlength="6">
            </div>

            <button type="submit">Registrarse</button>
        </form>

        <div class="link-container">
            ¿Ya tienes cuenta? <a href="login.jsp">Inicia Sesión</a>
        </div>
    </div>
</body>
</html>
