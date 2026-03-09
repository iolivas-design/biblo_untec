<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestionar Préstamos - Biblioteca Digital UNTEC</title>
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>📖 Gestión de Préstamos</h1>
        
        <div class="navbar">
            <a href="libros">← Volver al Catálogo</a>
            <a href="logout.jsp" class="logout">Cerrar Sesión</a>
        </div>

        <c:if test="${not empty exitoso}">
            <div class="success">${exitoso}</div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <!-- Formulario para Asignar Préstamo -->
        <div class="info-section">
            <h2>Asignar Nuevo Préstamo</h2>
            <form action="gestionar-prestamos" method="post" style="background-color: #f9f9f9; padding: 20px; border-radius: 5px; margin-bottom: 30px;">
                <input type="hidden" name="accion" value="asignar">
                
                <div style="margin-bottom: 15px;">
                    <label for="usuarioSelect" style="display: block; font-weight: bold; margin-bottom: 5px;">Seleccionar Usuario:</label>
                    <select id="usuarioSelect" name="idUsuario" required style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                        <option value="">-- Selecciona un usuario --</option>
                        <c:forEach var="u" items="${usuarios}">
                            <option value="${u.idUsuario}">${u.nombre} (${u.email})</option>
                        </c:forEach>
                    </select>
                </div>

                <div style="margin-bottom: 15px;">
                    <label for="libroSelect" style="display: block; font-weight: bold; margin-bottom: 5px;">Seleccionar Libro Disponible:</label>
                    <select id="libroSelect" name="idLibro" required style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                        <option value="">-- Selecciona un libro disponible --</option>
                        <c:forEach var="l" items="${libros}">
                            <c:if test="${l.disponible}">
                                <option value="${l.idLibro}">${l.titulo} - ${l.autor} (${l.genero})</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" style="padding: 10px 20px; background-color: #667eea; color: white; border: none; border-radius: 5px; cursor: pointer; font-weight: bold; transition: background-color 0.3s;" onmouseover="this.style.backgroundColor='#764ba2'" onmouseout="this.style.backgroundColor='#667eea'">Asignar Préstamo</button>
            </form>
        </div>

        <!-- Préstamos Activos -->
        <div class="info-section">
            <h2>Préstamos Activos del Sistema</h2>
            <c:choose>
                <c:when test="${not empty prestamosActivos}">
                    <table>
                        <thead>
                            <tr>
                                <th>Usuario</th>
                                <th>Libro</th>
                                <th>Fecha Préstamo</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${prestamosActivos}">
                                <tr>
                                    <td>${p.nombreUsuario}</td>
                                    <td>${p.tituloLibro}</td>
                                    <td>${p.fechaPrestamo}</td>
                                    <td>
                                        <div class="action-links">
                                            <form action="gestionar-prestamos" method="post">
                                                <input type="hidden" name="accion" value="devolver">
                                                <input type="hidden" name="idPrestamo" value="${p.idPrestamo}">
                                                <input type="hidden" name="idLibro" value="${p.idLibro}">
                                                <input type="hidden" name="idUsuario" value="${p.idUsuario}">
                                                <button type="submit" onclick="return confirm('¿Registrar devolución?');">Registrar Devolución</button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p class="empty-message">No hay préstamos activos en el sistema</p>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Historial de Usuario Específico -->
        <c:if test="${not empty prestamos}">
            <div class="info-section">
                <h2>Historial Completo del Usuario</h2>
                <a href="gestionar-prestamos" style="padding: 8px 15px; background-color: #95a5a6; color: white; text-decoration: none; border-radius: 5px; display: inline-block; margin-bottom: 15px;">← Ver todos los préstamos</a>
                <table>
                    <thead>
                        <tr>
                            <th>Usuario</th>
                            <th>Libro</th>
                            <th>Fecha Préstamo</th>
                            <th>Fecha Devolución</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${prestamos}">
                            <tr>
                                <td>${p.nombreUsuario}</td>
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
                                <td>
                                    <c:if test="${empty p.fechaDevolucion}">
                                        <form action="gestionar-prestamos" method="post" style="display: inline;">
                                            <input type="hidden" name="accion" value="devolver">
                                            <input type="hidden" name="idPrestamo" value="${p.idPrestamo}">
                                            <input type="hidden" name="idLibro" value="${p.idLibro}">
                                            <input type="hidden" name="idUsuario" value="${p.idUsuario}">
                                            <button type="submit" style="padding: 6px 12px; background-color: #27ae60; color: white; border: none; border-radius: 3px; cursor: pointer;" onclick="return confirm('¿Registrar devolución?');">Devolver</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</body>
</html>
