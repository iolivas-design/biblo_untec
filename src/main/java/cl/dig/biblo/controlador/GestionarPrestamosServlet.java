package cl.dig.biblo.controlador;

import cl.dig.biblo.dao.LibroDAO;
import cl.dig.biblo.dao.PrestamoDAO;
import cl.dig.biblo.dao.UsuarioDAO;
import cl.dig.biblo.modelo.Libro;
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

@WebServlet("/gestionar-prestamos")
public class GestionarPrestamosServlet extends HttpServlet {
    private PrestamoDAO prestamoDAO = new PrestamoDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private LibroDAO libroDAO = new LibroDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");

        // Validar que sea bibliotecario
        if (usuario == null || !"bibliotecario".equals(tipoUsuario)) {
            response.sendRedirect("index.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        String usuarioId = request.getParameter("usuarioId");

        if ("asignar".equals(accion)) {
            // Mostrar formulario para asignar préstamo
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            List<Libro> libros = libroDAO.listarTodos();
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("libros", libros);
            request.getRequestDispatcher("gestionar-prestamos.jsp").forward(request, response);
        } else if ("por-usuario".equals(accion) && usuarioId != null) {
            // Ver préstamos de un usuario específico
            try {
                int idUsuario = Integer.parseInt(usuarioId);
                List<Prestamo> prestamos = prestamoDAO.listarHistorialCompletoPorUsuario(idUsuario);
                request.setAttribute("prestamos", prestamos);
                request.setAttribute("usuarioSeleccionado", idUsuario);
                request.getRequestDispatcher("gestionar-prestamos.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect("gestionar-prestamos");
            }
        } else {
            // Ver todos los préstamos activos y cargar formulario de asignación
            List<Prestamo> prestamosActivos = prestamoDAO.listarTodosPrestamosActivos();
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            List<Libro> libros = libroDAO.listarTodos();
            request.setAttribute("prestamosActivos", prestamosActivos);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("libros", libros);
            request.getRequestDispatcher("gestionar-prestamos.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");

        // Validar que sea bibliotecario
        if (!"bibliotecario".equals(tipoUsuario)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado");
            return;
        }

        String accion = request.getParameter("accion");

        if ("asignar".equals(accion)) {
            String idUsuarioStr = request.getParameter("idUsuario");
            String idLibroStr = request.getParameter("idLibro");

            try {
                int idUsuario = Integer.parseInt(idUsuarioStr);
                int idLibro = Integer.parseInt(idLibroStr);

                boolean resultado = prestamoDAO.asignar(idUsuario, idLibro);

                if (resultado) {
                    request.setAttribute("exitoso", "Préstamo asignado correctamente");
                } else {
                    request.setAttribute("error", "Error al asignar el préstamo. El libro puede no estar disponible.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Datos inválidos");
            }
            
            // Recargar formulario con lista de usuarios y libros
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            List<Libro> libros = libroDAO.listarTodos();
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("libros", libros);
            request.getRequestDispatcher("gestionar-prestamos.jsp").forward(request, response);
        } else if ("devolver".equals(accion)) {
            String idPrestamoStr = request.getParameter("idPrestamo");
            String idLibroStr = request.getParameter("idLibro");
            String idUsuarioStr = request.getParameter("idUsuario");

            try {
                int idLibro = Integer.parseInt(idLibroStr);
                int idUsuario = Integer.parseInt(idUsuarioStr);

                boolean resultado = prestamoDAO.devolverConValidacion(idLibro, idUsuario);

                if (resultado) {
                    request.setAttribute("exitoso", "Devolución registrada correctamente");
                } else {
                    request.setAttribute("error", "Error al registrar la devolución");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Datos inválidos");
            }

            // Redirigir de vuelta a la gestión de préstamos
            response.sendRedirect("gestionar-prestamos");
        }
    }
}
