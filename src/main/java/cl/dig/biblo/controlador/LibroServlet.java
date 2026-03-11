package cl.dig.biblo.controlador;

import cl.dig.biblo.dao.LibroDAO;
import cl.dig.biblo.modelo.Libro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet que gestiona las operaciones relacionadas con libros en la biblioteca digital.
 * Proporciona funcionalidad para listar, buscar, editar y eliminar libros.
 * Accesible por usuarios bibliotecarios para la administración del catálogo.
 * 
 * @author Biblioteca Digital UNTEC
 * @version 1.0
 */
@WebServlet("/libros")
public class LibroServlet extends HttpServlet {
    private LibroDAO libroDAO = new LibroDAO();

    /**
     * Procesa las solicitudes GET para listar y buscar libros.
     * Soporta búsqueda por criterio (título, autor, género), eliminación y edición de libros.
     * 
     * @param request el objeto HttpServletRequest que contiene los parámetros de búsqueda/acción
     * @param response el objeto HttpServletResponse para redirigir al usuario
     * @throws ServletException si hay un error en la ejecución del servlet
     * @throws IOException si hay un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String criterio = request.getParameter("criterio");
        String valor = request.getParameter("valor");

        List<Libro> lista;
        if (criterio != null && valor != null && !valor.trim().isEmpty()) {
            // Validar criterio para evitar SQL injection
            if (criterio.equals("titulo") || criterio.equals("autor") || criterio.equals("genero")) {
                lista = libroDAO.buscarPorCriterio(criterio, valor);
            } else {
                lista = libroDAO.listarTodos();
            }
        } else if (accion == null) {
            lista = libroDAO.listarTodos();
        } else if (accion.equals("eliminar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            libroDAO.eliminar(id);
            response.sendRedirect("libros");
            return;
        } else if (accion.equals("editar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Libro l = libroDAO.obtenerPorId(id);
            request.setAttribute("libro", l);
            request.getRequestDispatcher("formulario-libro.jsp").forward(request, response);
            return;
        } else {
            lista = libroDAO.listarTodos();
        }

        request.setAttribute("libros", lista);
        request.getRequestDispatcher("catalogo.jsp").forward(request, response);
    }

    /**
     * Procesa las solicitudes POST para crear o actualizar libros.
     * Si el ID está vacío, crea un nuevo libro. Si tiene ID, actualiza el existente.
     * 
     * @param request el objeto HttpServletRequest que contiene los datos del libro
     * @param response el objeto HttpServletResponse para redirigir al usuario
     * @throws ServletException si hay un error en la ejecución del servlet
     * @throws IOException si hay un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("idLibro");
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String genero = request.getParameter("genero");
        String disponibleString = request.getParameter("disponible");
        boolean disponible = (disponibleString == null) || Boolean.parseBoolean(disponibleString);

        Libro l = new Libro();
        l.setTitulo(titulo);
        l.setAutor(autor);
        l.setGenero(genero);
        l.setDisponible(disponible);

        if (idStr == null || idStr.isEmpty()) {
            libroDAO.agregar(l);
        } else {
            l.setIdLibro(Integer.parseInt(idStr));
            libroDAO.actualizar(l);
        }
        response.sendRedirect("libros");
    }
}