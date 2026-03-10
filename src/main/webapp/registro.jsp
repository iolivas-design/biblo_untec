<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
        <h1>Crear Cuenta</h1>
        
        <c:if test="${not empty error}">
            <div class="error">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <div style="max-width: 500px; margin: 0 auto;">
            <form action="registro" method="post">
                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre:</label>
                    <input type="text" id="nombre" name="nombre" required class="form-control" placeholder="Tu nombre completo">
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email:</label>
                    <input type="email" id="email" name="email" required class="form-control" placeholder="tu@email.com">
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña:</label>
                    <input type="password" id="password" name="password" required minlength="6" class="form-control" placeholder="Mínimo 6 caracteres">
                </div>
                <div class="mb-3">
                    <label for="confirmPassword" class="form-label">Confirmar Contraseña:</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required minlength="6" class="form-control" placeholder="Repite tu contraseña">
                </div>
                <button type="submit" class="btn btn-primary w-100">Registrarse</button>
            </form>

            <div class="link-container">
                ¿Ya tienes cuenta? <a href="login.jsp">Inicia Sesión</a>
            </div>
        </div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
