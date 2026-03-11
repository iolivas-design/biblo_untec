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

/**
 * Servlet que gestiona la autenticación de usuarios en la biblioteca digital.
 * Valida las credenciales del usuario y establece la sesión.
 * 
 * @author Biblioteca Digital UNTEC
 * @version 1.0
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Procesa las solicitudes POST del formulario de login.
     * Valida email y contraseña, y si son correctos, inicia la sesión del usuario.
     * 
     * @param request el objeto HttpServletRequest que contiene los parámetros email y password
     * @param response el objeto HttpServletResponse para redirigir al usuario
     * @throws ServletException si hay un error en la ejecución del servlet
     * @throws IOException si hay un error de entrada/salida
     */
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