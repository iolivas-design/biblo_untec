<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
        <div style="text-align: center; max-width: 800px; margin: 0 auto; padding: 40px 20px;">
            <h1 style="font-size: 2.5rem; margin-bottom: 20px;">Bienvenido a Biblioteca Digital UNTEC</h1>
            <p style="font-size: 1.1rem; color: #c0c0c0; margin-bottom: 40px;">Accede a nuestro catálogo de libros y gestiona tus préstamos de forma rápida y sencilla</p>
            <div class="buttons-container d-flex gap-3 justify-content-center mt-4">
                <a href="login.jsp" class="btn btn-primary" style="padding: 12px 30px; font-size: 1rem;">Iniciar Sesión</a>
                <a href="registro.jsp" class="btn btn-success" style="padding: 12px 30px; font-size: 1rem;">Crear Cuenta</a>
            </div>
        </div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>