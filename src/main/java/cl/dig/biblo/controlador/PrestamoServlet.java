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

@WebServlet("/solicitarPrestamo")
public class PrestamoServlet extends HttpServlet {

    private PrestamoDAO prestamoDAO = new PrestamoDAO();

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