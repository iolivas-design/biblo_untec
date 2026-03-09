<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catálogo - Biblioteca Digital UNTEC</title>
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
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
            <p class="error"><c:out value="${error}"/></p>
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
        <form action="catalogo" method="get" style="margin-bottom: 25px; display: flex; gap: 10px; align-items: center;">
            <label for="criterio" style="font-weight: bold;">Buscar por:</label>
            <select name="criterio" id="criterio" style="padding: 8px; border-radius: 4px;">
                <option value="titulo">Título</option>
                <option value="autor">Autor</option>
                <option value="genero">Género</option>
            </select>
            <input type="text" name="valor" id="busqueda" list="datalist-titulo" placeholder="Ingrese búsqueda..." style="padding: 8px; border-radius: 4px; border: 1px solid #ddd; min-width: 200px;">
            <button type="submit" style="padding: 8px 16px; background-color: #667eea; color: white; border: none; border-radius: 5px; cursor: pointer; font-weight: bold;">Buscar</button>
        </form>
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

        <table>
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
                                <c:when test="${l.disponible}">
                                    <span class="available">Disponible</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="unavailable">En préstamo</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <div class="action-links">
                                <c:choose>
                                    <c:when test="${sessionScope.tipoUsuario == 'bibliotecario'}">
                                        <!-- Opciones para Bibliotecario -->
                                        <c:if test="${l.disponible}">
                                            <a href="libros?accion=editar&id=${l.idLibro}">Editar</a>
                                        </c:if>
                                        <a href="libros?accion=eliminar&id=${l.idLibro}" class="btn-delete" onclick="return confirm('¿Seguro que deseas eliminar este libro?');">Eliminar</a>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Opciones para Usuario General -->
                                        <c:if test="${l.disponible}">
                                            <a href="solicitarPrestamo?id=${l.idLibro}">Solicitar Préstamo</a>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>