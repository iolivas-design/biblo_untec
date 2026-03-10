package cl.dig.biblo.dao;

import cl.dig.biblo.modelo.Prestamo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    public boolean registrar(int idUsuario, int idLibro) {
        String sqlIns = "INSERT INTO prestamos (id_usuario, id_libro, fecha_prestamo) VALUES (?, ?, ?)";
        String sqlUpd = "UPDATE libros SET disponible = false WHERE id_libro = ?";
        Connection conn = null;
        try {
            conn = ConexionBD.obtenerConexion();
            conn.setAutoCommit(false);
            try (PreparedStatement psI = conn.prepareStatement(sqlIns);
                 PreparedStatement psU = conn.prepareStatement(sqlUpd)) {
                psI.setInt(1, idUsuario);
                psI.setInt(2, idLibro);
                psI.setDate(3, Date.valueOf(LocalDate.now()));
                psI.executeUpdate();
                psU.setInt(1, idLibro);
                psU.executeUpdate();
                conn.commit();
                return true;
            } catch (SQLException e) {
                if (conn != null) conn.rollback();
                return false;
            }
        } catch (SQLException e) { return false; }
    }

    public boolean devolverConValidacion(int idLibro, int idUsuario) {
        // La validación ocurre aquí: el WHERE exige que coincidan Libro, Usuario y que no esté devuelto
        String sqlPre = "UPDATE prestamos SET fecha_devolucion = CURRENT_DATE " +
                        "WHERE id_libro = ? AND id_usuario = ? AND fecha_devolucion IS NULL";
        String sqlVin = "UPDATE libros SET estado = 'disponible', disponible = true WHERE id_libro = ?";
        Connection conn = null;
        try {
            conn = ConexionBD.obtenerConexion();
            conn.setAutoCommit(false);
            try (PreparedStatement psP = conn.prepareStatement(sqlPre);
                 PreparedStatement psV = conn.prepareStatement(sqlVin)) {
                psP.setInt(1, idLibro);
                psP.setInt(2, idUsuario);
                if (psP.executeUpdate() > 0) {
                    psV.setInt(1, idLibro);
                    psV.executeUpdate();
                    conn.commit();
                    return true;
                }
                if (conn != null) conn.rollback();
                return false;
            } catch (SQLException e) {
                if (conn != null) conn.rollback();
                return false;
            }
        } catch (SQLException e) { return false; }
    }

    public List<Prestamo> listarPorUsuario(int idUsuario) {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT p.*, l.titulo FROM prestamos p " +
                     "JOIN libros l ON p.id_libro = l.id_libro " +
                     "WHERE p.id_usuario = ? AND p.fecha_devolucion IS NULL";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prestamo p = new Prestamo();
                p.setIdLibro(rs.getInt("id_libro"));
                p.setTituloLibro(rs.getString("titulo"));
                p.setFechaPrestamo(rs.getDate("fecha_prestamo"));
                lista.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Prestamo> listarHistorialCompletoPorUsuario(int idUsuario) {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT p.id_prestamo, p.id_usuario, p.id_libro, p.fecha_prestamo, p.fecha_devolucion, " +
                     "l.titulo, u.nombre FROM prestamos p " +
                     "JOIN libros l ON p.id_libro = l.id_libro " +
                     "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                     "WHERE p.id_usuario = ? " +
                     "ORDER BY p.fecha_prestamo DESC";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prestamo p = new Prestamo();
                p.setIdPrestamo(rs.getInt("id_prestamo"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setIdLibro(rs.getInt("id_libro"));
                p.setTituloLibro(rs.getString("titulo"));
                p.setNombreUsuario(rs.getString("nombre"));
                p.setFechaPrestamo(rs.getDate("fecha_prestamo"));
                p.setFechaDevolucion(rs.getDate("fecha_devolucion"));
                lista.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Prestamo> listarPrestamosActivosPorUsuario(int idUsuario) {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT p.id_prestamo, p.id_usuario, p.id_libro, p.fecha_prestamo, p.fecha_devolucion, " +
                     "l.titulo, u.nombre FROM prestamos p " +
                     "JOIN libros l ON p.id_libro = l.id_libro " +
                     "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                     "WHERE p.id_usuario = ? AND p.fecha_devolucion IS NULL " +
                     "ORDER BY p.fecha_prestamo DESC";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prestamo p = new Prestamo();
                p.setIdPrestamo(rs.getInt("id_prestamo"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setIdLibro(rs.getInt("id_libro"));
                p.setTituloLibro(rs.getString("titulo"));
                p.setNombreUsuario(rs.getString("nombre"));
                p.setFechaPrestamo(rs.getDate("fecha_prestamo"));
                p.setFechaDevolucion(rs.getDate("fecha_devolucion"));
                lista.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Prestamo> listarTodosPrestamosActivos() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT p.id_prestamo, p.id_usuario, p.id_libro, p.fecha_prestamo, p.fecha_devolucion, " +
                     "l.titulo, u.nombre FROM prestamos p " +
                     "JOIN libros l ON p.id_libro = l.id_libro " +
                     "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                     "WHERE p.fecha_devolucion IS NULL " +
                     "ORDER BY p.fecha_prestamo DESC";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prestamo p = new Prestamo();
                p.setIdPrestamo(rs.getInt("id_prestamo"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setIdLibro(rs.getInt("id_libro"));
                p.setTituloLibro(rs.getString("titulo"));
                p.setNombreUsuario(rs.getString("nombre"));
                p.setFechaPrestamo(rs.getDate("fecha_prestamo"));
                p.setFechaDevolucion(rs.getDate("fecha_devolucion"));
                lista.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public boolean asignar(int idUsuario, int idLibro) {
        String sqlCheck = "SELECT disponible FROM libros WHERE id_libro = ?";
        String sqlIns = "INSERT INTO prestamos (id_usuario, id_libro, fecha_prestamo) VALUES (?, ?, ?)";
        String sqlUpd = "UPDATE libros SET disponible = false WHERE id_libro = ?";
        Connection conn = null;
        try {
            conn = ConexionBD.obtenerConexion();
            
            // Validar que el libro existe y está disponible
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, idLibro);
            ResultSet rs = psCheck.executeQuery();
            if (!rs.next() || !rs.getBoolean("disponible")) {
                return false; // Libro no existe o no está disponible
            }
            psCheck.close();
            
            conn.setAutoCommit(false);
            try (PreparedStatement psI = conn.prepareStatement(sqlIns);
                 PreparedStatement psU = conn.prepareStatement(sqlUpd)) {
                psI.setInt(1, idUsuario);
                psI.setInt(2, idLibro);
                psI.setDate(3, Date.valueOf(LocalDate.now()));
                psI.executeUpdate();
                psU.setInt(1, idLibro);
                psU.executeUpdate();
                conn.commit();
                return true;
            } catch (SQLException e) {
                if (conn != null) conn.rollback();
                return false;
            }
        } catch (SQLException e) { 
            return false;
        }
    }

    // Método para SOLICITUDES (marca libro como solicitado)
    public boolean solicitarPrestamo(int idUsuario, int idLibro) {
        String sqlIns = "INSERT INTO prestamos (id_usuario, id_libro, fecha_prestamo, estado) VALUES (?, ?, ?, 'SOLICITUD')";
        String sqlUpd = "UPDATE libros SET estado = 'solicitado', disponible = false WHERE id_libro = ?";
        Connection conn = null;
        try {
            conn = ConexionBD.obtenerConexion();
            conn.setAutoCommit(false);
            try (PreparedStatement psIns = conn.prepareStatement(sqlIns);
                 PreparedStatement psUpd = conn.prepareStatement(sqlUpd)) {
                psIns.setInt(1, idUsuario);
                psIns.setInt(2, idLibro);
                psIns.setDate(3, Date.valueOf(LocalDate.now()));
                psIns.executeUpdate();
                psUpd.setInt(1, idLibro);
                psUpd.executeUpdate();
                conn.commit();
                return true;
            } catch (SQLException e) {
                if (conn != null) conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    // Método para APROBAR solicitud
    public boolean aprobarSolicitud(int idPrestamo, int idLibro) {
        String sqlUpd = "UPDATE prestamos SET estado = 'APROBADO' WHERE id_prestamo = ?";
        String sqlLib = "UPDATE libros SET estado = 'en_prestamo', disponible = false WHERE id_libro = ?";
        Connection conn = null;
        try {
            conn = ConexionBD.obtenerConexion();
            conn.setAutoCommit(false);
            try (PreparedStatement psUpd = conn.prepareStatement(sqlUpd);
                 PreparedStatement psLib = conn.prepareStatement(sqlLib)) {
                psUpd.setInt(1, idPrestamo);
                psUpd.executeUpdate();
                psLib.setInt(1, idLibro);
                psLib.executeUpdate();
                conn.commit();
                return true;
            } catch (SQLException e) {
                if (conn != null) conn.rollback();
                return false;
            }
        } catch (SQLException e) { 
            return false; 
        }
    }

    // Método para RECHAZAR solicitud
    public boolean rechazarSolicitud(int idPrestamo, int idLibro) {
        String sqlUpd = "UPDATE prestamos SET estado = 'RECHAZADO' WHERE id_prestamo = ?";
        String sqlLib = "UPDATE libros SET estado = 'disponible', disponible = true WHERE id_libro = ?";
        Connection conn = null;
        try {
            conn = ConexionBD.obtenerConexion();
            conn.setAutoCommit(false);
            try (PreparedStatement psUpd = conn.prepareStatement(sqlUpd);
                 PreparedStatement psLib = conn.prepareStatement(sqlLib)) {
                psUpd.setInt(1, idPrestamo);
                psUpd.executeUpdate();
                psLib.setInt(1, idLibro);
                psLib.executeUpdate();
                conn.commit();
                return true;
            } catch (SQLException e) {
                if (conn != null) conn.rollback();
                return false;
            }
        } catch (SQLException e) { 
            return false; 
        }
    }

    // Listar solicitudes PENDIENTES para el bibliotecario
    public List<Prestamo> listarSolicitudesPendientes() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT p.id_prestamo, p.id_usuario, p.id_libro, p.fecha_prestamo, " +
                     "l.titulo, l.autor, u.nombre FROM prestamos p " +
                     "JOIN libros l ON p.id_libro = l.id_libro " +
                     "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                     "WHERE p.estado = 'SOLICITUD' " +
                     "ORDER BY p.fecha_prestamo ASC";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prestamo p = new Prestamo();
                p.setIdPrestamo(rs.getInt("id_prestamo"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setIdLibro(rs.getInt("id_libro"));
                p.setNombreUsuario(rs.getString("nombre"));
                p.setTituloLibro(rs.getString("titulo"));
                p.setFechaPrestamo(rs.getDate("fecha_prestamo"));
                p.setEstado("SOLICITUD");
                lista.add(p);
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return lista;
    }
}