package cl.dig.biblo.controlador;

import cl.dig.biblo.dao.PrestamoDAO;
import cl.dig.biblo.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet que gestiona las devoluciones de libros prestados.
 * Permite a los usuarios devolver libros que tienen en préstamo.
 * 
 * @author Biblioteca Digital UNTEC
 * @version 1.0
 */
@WebServlet("/devolverLibro")
public class DevolverServlet extends HttpServlet {
    private PrestamoDAO prestamoDAO = new PrestamoDAO();

    /**
     * Procesa las solicitudes GET para registrar la devolución de un libro.
     * Requiere que el usuario esté autenticado y proporcione el ID del libro a devolver.
     * 
     * @param request el objeto HttpServletRequest que contiene el ID del libro a devolver
     * @param response el objeto HttpServletResponse para redirigir al usuario
     * @throws ServletException si hay un error en la ejecución del servlet
     * @throws IOException si hay un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String idLibroStr = request.getParameter("idLibro");

        if (usuario != null && idLibroStr != null) {
            prestamoDAO.devolverConValidacion(Integer.parseInt(idLibroStr), usuario.getIdUsuario());
        }
        response.sendRedirect("mis-prestamos");
    }
}