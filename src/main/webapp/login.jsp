<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

        <h1>Iniciar Sesión</h1>
        
        <c:if test="${not empty error}">
            <div class="error">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <div style="max-width: 500px; margin: 0 auto;">
            <form action="login" method="post">
                <div class="mb-3">
                    <label for="email" class="form-label">Email:</label>
                    <input type="email" id="email" name="email" required class="form-control" placeholder="tu@email.com">
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña:</label>
                    <input type="password" id="password" name="password" required class="form-control" placeholder="Tu contraseña">
                </div>
                <button type="submit" class="btn btn-primary w-100">Entrar</button>
            </form>

            <div class="link-container">
                ¿No tienes cuenta? <a href="registro.jsp">Regístrate aquí</a>
            </div>

            <div class="back-link">
                <a href="index.jsp">← Volver al inicio</a>
            </div>
        </div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>