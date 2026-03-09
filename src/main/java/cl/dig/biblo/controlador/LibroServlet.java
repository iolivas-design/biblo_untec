package cl.dig.biblo.controlador;

import cl.dig.biblo.dao.LibroDAO;
import cl.dig.biblo.modelo.Libro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/libros")
public class LibroServlet extends HttpServlet {
    private LibroDAO libroDAO = new LibroDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String criterio = request.getParameter("criterio");
        String valor = request.getParameter("valor");

        List<Libro> lista;
        if (criterio != null && valor != null && !valor.trim().isEmpty()) {
            // Validar criterio para evitar SQL injection
            if (criterio.equals("titulo") || criterio.equals("autor") || criterio.equals("genero")) {
                lista = libroDAO.buscarPorCriterio(criterio, valor);
            } else {
                lista = libroDAO.listarTodos();
            }
        } else if (accion == null) {
            lista = libroDAO.listarTodos();
        } else if (accion.equals("eliminar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            libroDAO.eliminar(id);
            response.sendRedirect("libros");
            return;
        } else if (accion.equals("editar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Libro l = libroDAO.obtenerPorId(id);
            request.setAttribute("libro", l);
            request.getRequestDispatcher("formulario-libro.jsp").forward(request, response);
            return;
        } else {
            lista = libroDAO.listarTodos();
        }

        request.setAttribute("libros", lista);
        request.getRequestDispatcher("catalogo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("idLibro");
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String genero = request.getParameter("genero");
        String disponibleString = request.getParameter("disponible");
        boolean disponible = (disponibleString == null) || Boolean.parseBoolean(disponibleString);

        Libro l = new Libro();
        l.setTitulo(titulo);
        l.setAutor(autor);
        l.setGenero(genero);
        l.setDisponible(disponible);

        if (idStr == null || idStr.isEmpty()) {
            libroDAO.agregar(l);
        } else {
            l.setIdLibro(Integer.parseInt(idStr));
            libroDAO.actualizar(l);
        }
        response.sendRedirect("libros");
    }
}