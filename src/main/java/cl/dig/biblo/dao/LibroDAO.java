package cl.dig.biblo.dao;

import cl.dig.biblo.modelo.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar operaciones de base de datos 
 * relacionadas con la entidad Libro.
 * Proporciona métodos para listar, agregar, obtener y actualizar libros.
 * 
 * @author Biblioteca Digital UNTEC
 * @version 1.0
 */
public class LibroDAO {

    /**
     * Obtiene una lista de todos los libros en la base de datos.
     * 
     * @return una lista de todos los libros disponibles
     */
    public List<Libro> listarTodos() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getInt("id_libro"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("genero"),
                    rs.getBoolean("disponible")
                );
                try {
                    libro.setEstado(rs.getString("estado"));
                } catch (SQLException e) {
                    libro.setEstado(rs.getBoolean("disponible") ? "disponible" : "en_prestamo");
                }
                lista.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Agrega un nuevo libro a la base de datos.
     * El libro se crea con estado "disponible" automáticamente.
     * 
     * @param libro el objeto Libro a agregar
     * @return true si la inserción fue exitosa, false en caso contrario
     */
    public boolean agregar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, genero, disponible) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getGenero());
            ps.setBoolean(4, true);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene un libro específico de la base de datos por su ID.
     * 
     * @param idLibro el identificador del libro a obtener
     * @return el objeto Libro si existe, null en caso contrario
     */
    public Libro obtenerPorId(int idLibro) {
        Libro l = null;
        String sql = "SELECT * FROM libros WHERE id_libro = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLibro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    l = new Libro(
                        rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("genero"),
                        rs.getBoolean("disponible")
                    );
                    try {
                        l.setEstado(rs.getString("estado"));
                    } catch (SQLException e) {
                        l.setEstado(rs.getBoolean("disponible") ? "disponible" : "en_prestamo");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * Actualiza la información de un libro existente en la base de datos.
     * 
     * @param l el objeto Libro con la información actualizada
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizar(Libro l) {
        String sql = "UPDATE libros SET titulo=?, autor=?, genero=?, disponible=? WHERE id_libro=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getTitulo());
            ps.setString(2, l.getAutor());
            ps.setString(3, l.getGenero());
            ps.setBoolean(4, l.isDisponible());
            ps.setInt(5, l.getIdLibro());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un libro de la base de datos por su ID.
     * 
     * @param idLibro el identificador del libro a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean eliminar(int idLibro) {
        String sql = "DELETE FROM libros WHERE id_libro=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLibro);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza el estado del libro en la base de datos.
     * Automáticamente actualiza el flag "disponible" según el estado proporcionado.
     * 
     * @param idLibro el identificador del libro
     * @param estado el nuevo estado del libro (disponible, solicitado, en_prestamo)
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizarEstado(int idLibro, String estado) {
        String sql = "UPDATE libros SET estado = ?, disponible = ? WHERE id_libro = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setBoolean(2, "disponible".equals(estado));
            ps.setInt(3, idLibro);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca libros en la base de datos según un criterio y valor especificados.
     * Soporta búsqueda por título, autor o género usando LIKE.
     * 
     * @param criterio el campo por el que buscar (titulo, autor, genero)
     * @param valor el valor a buscar (búsqueda parcial con wildcard)
     * @return una lista de libros que coinciden con el criterio
     */
    public List<Libro> buscarPorCriterio(String criterio, String valor) {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE " + criterio + " LIKE ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + valor + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Libro libro = new Libro(
                        rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("genero"),
                        rs.getBoolean("disponible")
                    );
                    try {
                        libro.setEstado(rs.getString("estado"));
                    } catch (SQLException e) {
                        libro.setEstado(rs.getBoolean("disponible") ? "disponible" : "en_prestamo");
                    }
                    lista.add(libro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}