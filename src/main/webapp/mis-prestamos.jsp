<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
        <h1>📚 Mi Historial de Préstamos</h1>
        
        <div class="navbar">
            <a href="libros">← Volver al Catálogo</a>
            <a href="logout.jsp" class="btn-logout">Cerrar Sesión</a>
        </div>

        <div class="info-section">
            <h2>Historial Completo de Préstamos</h2>
            <c:choose>
                <c:when test="${not empty prestamos}">
                    <table class="table table-striped">
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
                                                <span class="badge bg-warning">EN PRÉSTAMO</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-success">DEVUELTO</span>
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
<%@ include file="/WEB-INF/jspf/footer.jspf" %>