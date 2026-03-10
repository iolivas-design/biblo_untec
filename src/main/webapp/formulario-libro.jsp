<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
        <h1><c:out value="${libro != null ? 'Editar' : 'Registrar'}" /> Libro</h1>
        
        <div style="max-width: 600px; margin: 0 auto;">
            <form action="libros" method="post">
                <input type="hidden" name="idLibro" value="${libro.idLibro}">
                
                <div class="mb-3">
                    <label for="titulo" class="form-label">Título:</label>
                    <input type="text" id="titulo" name="titulo" value="${libro.titulo}" required class="form-control" placeholder="Ingrese el título del libro">
                </div>
                
                <div class="mb-3">
                    <label for="autor" class="form-label">Autor:</label>
                    <input type="text" id="autor" name="autor" value="${libro.autor}" required class="form-control" placeholder="Ingrese el nombre del autor">
                </div>
                
                <div class="mb-3">
                    <label for="genero" class="form-label">Género:</label>
                    <input type="text" id="genero" name="genero" value="${libro.genero}" class="form-control" placeholder="Ingrese el género del libro">
                </div>
                
                <input type="hidden" name="disponible" value="${libro.idLibro != null ? libro.disponible : true}">
                
                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-primary">Guardar</button>
                    <a href="libros" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>