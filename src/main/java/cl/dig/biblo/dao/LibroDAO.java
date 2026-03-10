package cl.dig.biblo.dao;

import cl.dig.biblo.modelo.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

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