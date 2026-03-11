package cl.dig.biblo.controlador;

import cl.dig.biblo.dao.PrestamoDAO;
import cl.dig.biblo.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet que gestiona las solicitudes de préstamo de libros por parte de usuarios regulares.
 * Permite a los usuarios solicitar libros del catálogo para préstamo.
 * 
 * @author Biblioteca Digital UNTEC
 * @version 1.0
 */
@WebServlet("/solicitarPrestamo")
public class PrestamoServlet extends HttpServlet {

    private PrestamoDAO prestamoDAO = new PrestamoDAO();

    /**
     * Procesa las solicitudes GET para crear una solicitud de préstamo.
     * Requiere que el usuario esté autenticado.
     * 
     * @param request el objeto HttpServletRequest que contiene el ID del libro a solicitar
     * @param response el objeto HttpServletResponse para redirigir al usuario
     * @throws ServletException si hay un error en la ejecución del servlet
     * @throws IOException si hay un error de entrada/salida
     */ 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String idLibroStr = request.getParameter("id");
        
        if (idLibroStr != null) {
            int idLibro = Integer.parseInt(idLibroStr);
            boolean exito = prestamoDAO.solicitarPrestamo(usuario.getIdUsuario(), idLibro);

            if (exito) {
                request.setAttribute("mensaje", "Solicitud enviada. Pendiente de aprobación del bibliotecario");
            } else {
                request.setAttribute("error", "Error al procesar la solicitud");
            }
        }

        request.getRequestDispatcher("libros").forward(request, response);
    }
}