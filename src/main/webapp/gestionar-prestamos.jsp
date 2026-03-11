<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
        <h1>📖 Gestión de Préstamos</h1>
        
        <div class="navbar">
            <a href="libros">← Volver al Catálogo</a>
            <a href="logout.jsp" class="btn-logout">Cerrar Sesión</a>
        </div>

        <c:if test="${not empty exitoso}">
            <div class="alert alert-success">${exitoso}</div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- SOLICITUDES PENDIENTES -->
        <div class="info-section">
            <h2>⏳ Solicitudes Pendientes de Aprobación</h2>
            <c:choose>
                <c:when test="${not empty solicitudesPendientes}">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Usuario</th>
                                <th>Libro</th>
                                <th>Fecha Solicitud</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${solicitudesPendientes}">
                                <tr>
                                    <td>${s.nombreUsuario}</td>
                                    <td>${s.tituloLibro}</td>
                                    <td>${s.fechaPrestamo}</td>
                                    <td>
                                        <div class="d-flex gap-2">
                                            <form action="gestionar-prestamos" method="post" style="display: inline;">
                                                <input type="hidden" name="accion" value="aprobarsolicitud">
                                                <input type="hidden" name="idPrestamo" value="${s.idPrestamo}">
                                                <input type="hidden" name="idLibro" value="${s.idLibro}">
                                                <button type="submit" class="btn btn-success" style="padding: 5px 10px; font-size: 0.85rem;">Aprobar</button>
                                            </form>
                                            <form action="gestionar-prestamos" method="post" style="display: inline;">
                                                <input type="hidden" name="accion" value="rechazarsolicitud">
                                                <input type="hidden" name="idPrestamo" value="${s.idPrestamo}">
                                                <input type="hidden" name="idLibro" value="${s.idLibro}">
                                                <button type="submit" class="btn btn-danger" style="padding: 5px 10px; font-size: 0.85rem;" onclick="return confirm('¿Rechazar esta solicitud?');">Rechazar</button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p class="empty-message">No hay solicitudes pendientes</p>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="info-section">
            <h2>Asignar Nuevo Préstamo</h2>
            <form action="gestionar-prestamos" method="post" style="background-color: rgba(60, 60, 60, 0.5); padding: 20px; border-radius: 5px; margin-bottom: 30px; border: 1px solid #555;">
                <input type="hidden" name="accion" value="asignar">
                <div class="mb-3">
                    <label for="usuarioSelect" class="form-label fw-bold">Seleccionar Usuario:</label>
                    <select id="usuarioSelect" name="idUsuario" required class="form-select tom-select">
                        <option value="">-- Selecciona un usuario --</option>
                        <c:forEach var="u" items="${usuarios}">
                            <option value="${u.idUsuario}">${u.nombre} (${u.email})</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="libroSelect" class="form-label fw-bold">Seleccionar Libro Disponible:</label>
                    <select id="libroSelect" name="idLibro" required class="form-select tom-select">
                        <option value="">-- Selecciona un libro disponible --</option>
                        <c:forEach var="l" items="${libros}">
                            <c:if test="${l.disponible}">
                                <option value="${l.idLibro}" ${idLibroSeleccionado == l.idLibro.toString() ? 'selected' : ''}>${l.titulo} - ${l.autor} (${l.genero})</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary fw-bold">Asignar Préstamo</button>
            </form>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function() {
                if (typeof TomSelect !== 'undefined') {
                    new TomSelect('#usuarioSelect', {
                        placeholder: 'Buscar usuario...',
                        plugins: {
                            dropdown_input: {}
                        },
                        maxOptions: null
                    });
                    new TomSelect('#libroSelect', {
                        placeholder: 'Buscar libro...',
                        plugins: {
                            dropdown_input: {}
                        },
                        maxOptions: null
                    });
                }
            });
        </script>

        <!-- Préstamos Activos -->
        <div class="info-section">
            <h2>Préstamos Activos del Sistema</h2>
            <c:choose>
                <c:when test="${not empty prestamosActivos}">
                    <table class="table table-striped">
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
                                        <form action="gestionar-prestamos" method="post">
                                            <input type="hidden" name="accion" value="devolver">
                                            <input type="hidden" name="idPrestamo" value="${p.idPrestamo}">
                                            <input type="hidden" name="idLibro" value="${p.idLibro}">
                                            <input type="hidden" name="idUsuario" value="${p.idUsuario}">
                                            <button type="submit" class="btn btn-success" style="padding: 5px 10px; font-size: 0.85rem;" onclick="return confirm('¿Registrar devolución?');">Registrar Devolución</button>
                                        </form>
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
                <a href="gestionar-prestamos" class="btn btn-secondary mb-3">← Ver todos los préstamos</a>
                <table class="table table-striped">
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
                                            <span class="badge bg-warning">EN PRÉSTAMO</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-success">DEVUELTO</span>
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
                                            <button type="submit" class="btn btn-success" style="padding: 5px 10px; font-size: 0.85rem;" onclick="return confirm('¿Registrar devolución?');">Devolver</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
