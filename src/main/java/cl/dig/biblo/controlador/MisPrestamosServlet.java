package cl.dig.biblo.controlador;

import cl.dig.biblo.dao.PrestamoDAO;
import cl.dig.biblo.modelo.Prestamo;
import cl.dig.biblo.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet que muestra el historial de préstamos de un usuario.
 * Usuarios regulares ven solo su historial personal.
 * Bibliotecarios son redirigidos a la página de gestión de préstamos.
 * 
 * @author Biblioteca Digital UNTEC
 * @version 1.0
 */
@WebServlet("/mis-prestamos")
public class MisPrestamosServlet extends HttpServlet {
    private PrestamoDAO prestamoDAO = new PrestamoDAO();

    /**
     * Procesa las solicitudes GET para mostrar el historial de préstamos del usuario.
     * Requiere autenticación. Los usuarios regulares ven su historial completo.
     * Los bibliotecarios son redirigidos a la página de gestión de préstamos.
     * 
     * @param request el objeto HttpServletRequest con la sesión del usuario
     * @param response el objeto HttpServletResponse para redirigir o forward
     * @throws ServletException si hay un error en la ejecución del servlet
     * @throws IOException si hay un error de entrada/salida
     */ 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");

        if (usuario == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // Si es bibliotecario, redirigir a gestión de préstamos
        if ("bibliotecario".equals(tipoUsuario)) {
            response.sendRedirect("gestionar-prestamos");
            return;
        }

        // Para usuario general, mostrar solo su historial completo
        List<Prestamo> historial = prestamoDAO.listarHistorialCompletoPorUsuario(usuario.getIdUsuario());
        request.setAttribute("prestamos", historial);
        request.getRequestDispatcher("mis-prestamos.jsp").forward(request, response);
    }
}