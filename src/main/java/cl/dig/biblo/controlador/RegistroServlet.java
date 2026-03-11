package cl.dig.biblo.controlador;

import cl.dig.biblo.dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet que gestiona el registro de nuevos usuarios en la biblioteca digital.
 * Valida la información del usuario y crea una nueva cuenta de tipo "general".
 * 
 * @author Biblioteca Digital UNTEC
 * @version 1.0
 */
@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Procesa las solicitudes POST del formulario de registro.
     * Valida que los datos sean correctos, que el email no esté registrado,
     * y que las contraseñas coincidan. Si todo es válido, crea una nueva cuenta.
     * 
     * @param request el objeto HttpServletRequest que contiene los datos de registro
     * @param response el objeto HttpServletResponse para redirigir al usuario
     * @throws ServletException si hay un error en la ejecución del servlet
     * @throws IOException si hay un error de entrada/salida
     */ 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validación básica
        if (nombre == null || nombre.trim().isEmpty()) {
            request.setAttribute("error", "El nombre es requerido");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "El email es requerido");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        if (password == null || password.isEmpty()) {
            request.setAttribute("error", "La contraseña es requerida");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        if (password.length() < 6) {
            request.setAttribute("error", "La contraseña debe tener al menos 6 caracteres");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Las contraseñas no coinciden");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // Validar que el email no exista
        if (usuarioDAO.emailExiste(email)) {
            request.setAttribute("error", "El email ya está registrado");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // Registrar el usuario
        boolean registroExitoso = usuarioDAO.registrarUsuario(nombre, email, password);

        if (registroExitoso) {
            request.setAttribute("exitoso", "Usuario registrado correctamente. Por favor, inicia sesión.");
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("error", "Error al registrar el usuario. Intenta nuevamente.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }
}
