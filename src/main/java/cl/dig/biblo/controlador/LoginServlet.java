package cl.dig.biblo.controlador;

import cl.dig.biblo.dao.UsuarioDAO;
import cl.dig.biblo.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        Usuario usuario = usuarioDAO.validarUsuario(email, pass);

        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("tipoUsuario", usuario.getTipoUsuario());
            response.sendRedirect("libros");
        } else {
            request.setAttribute("error", "Credenciales inválidas");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}