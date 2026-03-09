<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mis Préstamos - Biblioteca Digital UNTEC</title>
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>📚 Mi Historial de Préstamos</h1>
        
        <div class="navbar">
            <a href="libros">← Volver al Catálogo</a>
            <a href="logout.jsp" style="background-color: #e74c3c;">Cerrar Sesión</a>
        </div>

        <div class="info-section">
            <h2>Historial Completo de Préstamos</h2>
            <c:choose>
                <c:when test="${not empty prestamos}">
                    <table>
                        <thead>
                            <tr>
                                <th>Libro</th>
                                <th>Fecha Préstamo</th>
                                <th>Fecha Devolución</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${prestamos}">
                                <tr>
                                    <td>${p.tituloLibro}</td>
                                    <td>${p.fechaPrestamo}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty p.fechaDevolucion}">
                                                ${p.fechaDevolucion}
                                            </c:when>
                                            <c:otherwise>
                                                -
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty p.fechaDevolucion}">
                                                <span class="estado-activo">EN PRÉSTAMO</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="estado-devuelto">DEVUELTO</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p class="empty-message">No tienes préstamos registrados</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>