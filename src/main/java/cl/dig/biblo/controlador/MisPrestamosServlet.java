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

@WebServlet("/mis-prestamos")
public class MisPrestamosServlet extends HttpServlet {
    private PrestamoDAO prestamoDAO = new PrestamoDAO();

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