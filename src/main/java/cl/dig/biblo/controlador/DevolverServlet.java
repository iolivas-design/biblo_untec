package cl.dig.biblo.controlador;

import cl.dig.biblo.dao.PrestamoDAO;
import cl.dig.biblo.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/devolverLibro")
public class DevolverServlet extends HttpServlet {
    private PrestamoDAO prestamoDAO = new PrestamoDAO();

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