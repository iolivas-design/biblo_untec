<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
        <h1>Catálogo de Libros</h1>
        
        <div class="user-info">
            Usuario: <span><c:out value="${sessionScope.usuario.nombre}" /></span>
            <c:if test="${sessionScope.tipoUsuario == 'bibliotecario'}">
                <span class="role-badge bibliotecario">Bibliotecario</span>
            </c:if>
            <c:if test="${sessionScope.tipoUsuario == 'general'}">
                <span class="role-badge">Usuario General</span>
            </c:if>
        </div>

        <div class="navbar">
            <c:if test="${sessionScope.tipoUsuario == 'bibliotecario'}">
                <a href="formulario-libro.jsp">+ Registrar Nuevo Libro</a>
                <a href="gestionar-prestamos">📚 Gestionar Préstamos</a>
            </c:if>
            <c:if test="${sessionScope.tipoUsuario == 'general'}">
                <a href="mis-prestamos">📚 Mis Préstamos</a>
            </c:if>
            <a href="logout.jsp" class="btn-logout">Cerrar Sesión</a>
        </div>
        
        <c:if test="${not empty error}">
            <div class="error"><c:out value="${error}"/></div>
        </c:if>

        <!-- Datalists para autocompletar -->
        <datalist id="datalist-titulo">
            <c:forEach var="l" items="${libros}">
                <option value="${l.titulo}" />
            </c:forEach>
        </datalist>
        <datalist id="datalist-autor">
            <c:forEach var="l" items="${libros}">
                <option value="${l.autor}" />
            </c:forEach>
        </datalist>
        <datalist id="datalist-genero">
            <c:forEach var="l" items="${libros}">
                <option value="${l.genero}" />
            </c:forEach>
        </datalist>
        <!-- Fin datalists -->

        <!-- Formulario de búsqueda -->
        <form action="libros" method="get" class="d-flex align-items-center mb-3 gap-2 flex-wrap">
            <label for="criterio" class="fw-bold mb-0">Buscar por:</label>
            <select name="criterio" id="criterio" class="form-select w-auto">
                <option value="titulo" ${param.criterio == 'titulo' ? 'selected' : ''}>Título</option>
                <option value="autor"  ${param.criterio == 'autor'  ? 'selected' : ''}>Autor</option>
                <option value="genero" ${param.criterio == 'genero' ? 'selected' : ''}>Género</option>
            </select>
            <input type="text" name="valor" id="busqueda" list="datalist-titulo"
                   placeholder="Ingrese búsqueda..." class="form-control w-auto"
                   style="min-width: 200px;" value="${param.valor}">
            <button type="submit" class="btn btn-primary fw-bold">Buscar</button>
            <c:if test="${not empty param.valor}">
                <a href="libros" class="btn btn-outline-secondary">✕ Limpiar</a>
            </c:if>
        </form>
        <c:if test="${not empty param.valor}">
            <p class="text-muted mb-2">
                Mostrando resultados para <strong><c:out value="${param.criterio}"/>: "<c:out value="${param.valor}"/>"</strong>
                — ${fn:length(libros)} resultado(s).
            </p>
        </c:if>
        <!-- Fin formulario de búsqueda -->

        <script>
        document.getElementById('criterio').addEventListener('change', function() {
            var input = document.getElementById('busqueda');
            if (this.value === 'titulo') {
                input.setAttribute('list', 'datalist-titulo');
            } else if (this.value === 'autor') {
                input.setAttribute('list', 'datalist-autor');
            } else if (this.value === 'genero') {
                input.setAttribute('list', 'datalist-genero');
            }
        });
        </script>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Título</th>
                    <th>Autor</th>
                    <th>Género</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="l" items="${libros}">
                    <tr>
                        <td>${l.idLibro}</td>
                        <td>${l.titulo}</td>
                        <td>${l.autor}</td>
                        <td>${l.genero}</td>
                        <td>
                            <c:choose>
                                <c:when test="${l.estado == 'disponible'}">
                                    <span class="badge bg-success">Disponible</span>
                                </c:when>
                                <c:when test="${l.estado == 'solicitado'}">
                                    <span class="badge bg-warning">Solicitado</span>
                                </c:when>
                                <c:when test="${l.estado == 'en_prestamo'}">
                                    <span class="badge bg-danger">En Préstamo</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">${l.estado}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <div class="d-flex gap-2" style="flex-wrap: wrap;">
                                <c:choose>
                                    <c:when test="${sessionScope.tipoUsuario == 'bibliotecario'}">
                                        <!-- Opciones para Bibliotecario -->
                                        <c:if test="${l.estado == 'disponible'}">
                                            <a href="libros?accion=editar&id=${l.idLibro}" class="btn btn-sm btn-warning" style="padding: 5px 10px; font-size: 0.85rem;">Editar</a>
                                            <a href="gestionar-prestamos?idLibro=${l.idLibro}" class="btn btn-sm btn-primary" style="padding: 5px 10px; font-size: 0.85rem;">Asignar</a>
                                        </c:if>
                                        <a href="libros?accion=eliminar&id=${l.idLibro}" class="btn btn-sm btn-danger" style="padding: 5px 10px; font-size: 0.85rem;" onclick="return confirm('¿Seguro que deseas eliminar este libro?');">Eliminar</a>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Opciones para Usuario General -->
                                        <c:if test="${l.estado == 'disponible'}">
                                            <a href="solicitarPrestamo?id=${l.idLibro}" class="btn btn-sm btn-primary" style="padding: 5px 10px; font-size: 0.85rem;">Solicitar</a>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>