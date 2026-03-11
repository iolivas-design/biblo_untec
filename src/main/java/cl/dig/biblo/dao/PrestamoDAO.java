package cl.dig.biblo.dao;

import cl.dig.biblo.modelo.Prestamo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar operaciones de base de datos 
 * relacionadas con la entidad Prestamo.
 * Proporciona métodos para registrar, devolver, aprobar, rechazar solicitudes de préstamo
 * y listar préstamos en diversos estados.
 * 
 * @author Biblioteca Digital UNTEC
 * @version 1.0
 */
public class PrestamoDAO {

    /**
     * Registra un nuevo préstamo en la base de datos.
     * Crea un registro de préstamo y marca el libro como no disponible.
     * Utiliza transacción para asegurar consistencia.
     * 
     * @param idUsuario el ID del usuario que realiza el préstamo
     * @param idLibro el ID del libro a prestar
     * @return true si el registro fue exitoso, false en caso contrario
     */
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

    /**
     * Devuelve un préstamo con validación de que el usuario y libro coincidan.
     * Actualiza la fecha de devolución y marca el libro como disponible.
     * Utiliza transacción para asegurar consistencia.
     * 
     * @param idLibro el ID del libro a devolver
     * @param idUsuario el ID del usuario que devuelve el libro
     * @return true si la devolución fue exitosa, false en caso contrario
     */
    public boolean devolverConValidacion(int idLibro, int idUsuario) {
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

    /**
     * Lista los préstamos activos (no devueltos) de un usuario específico.
     * Retorna solo préstamos sin fecha de devolución.
     * 
     * @param idUsuario el ID del usuario
     * @return una lista de préstamos activos del usuario
     */
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

    /**
     * Lista el historial completo de préstamos de un usuario.
     * Incluye préstamos activos y completados, ordenados por fecha descendente.
     * 
     * @param idUsuario el ID del usuario
     * @return una lista de todos los préstamos del usuario ordenados por fecha
     */
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

    /**
     * Lista solo los préstamos activos (sin devolución) de un usuario específico.
     * Útil para mostrar los libros que el usuario actualmente tiene en préstamo.
     * 
     * @param idUsuario el ID del usuario
     * @return una lista de préstamos activos del usuario
     */
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

    /**
     * Lista todos los préstamos actuales (sin devolver) de todos los usuarios.
     * Usada por el bibliotecario para gestionar los préstamos activos.
     * 
     * @return una lista de todos los préstamos activos del sistema
     */
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

    /**
     * Asigna un préstamo de un libro a un usuario.
     * Valida que el libro exista y esté disponible antes de asignar.
     * Utiliza transacción para asegurar consistencia.
     * 
     * @param idUsuario el ID del usuario al que se asigna el préstamo
     * @param idLibro el ID del libro a prestar
     * @return true si la asignación fue exitosa, false si el libro no está disponible
     */
    public boolean asignar(int idUsuario, int idLibro) {
        String sqlCheck = "SELECT disponible FROM libros WHERE id_libro = ?";
        String sqlIns = "INSERT INTO prestamos (id_usuario, id_libro, fecha_prestamo) VALUES (?, ?, ?)";
        String sqlUpd = "UPDATE libros SET disponible = false WHERE id_libro = ?";
        Connection conn = null;
        try {
            conn = ConexionBD.obtenerConexion();
            
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, idLibro);
            ResultSet rs = psCheck.executeQuery();
            if (!rs.next() || !rs.getBoolean("disponible")) {
                return false;
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

    /**
     * Registra una solicitud de préstamo de un usuario.
     * Marca el libro como "solicitado" y crea un registro con estado SOLICITUD.
     * El bibliotecario debe aprobar o rechazar posteriormente.
     * 
     * @param idUsuario el ID del usuario que solicita el préstamo
     * @param idLibro el ID del libro solicitado
     * @return true si la solicitud fue registrada exitosamente, false en caso contrario
     */
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

    /**
     * Aprueba una solicitud de préstamo.
     * Cambia el estado de SOLICITUD a APROBADO y actualiza el estado del libro.
     * Utiliza transacción para asegurar consistencia.
     * 
     * @param idPrestamo el ID del préstamo a aprobar
     * @param idLibro el ID del libro asociado al préstamo
     * @return true si la aprobación fue exitosa, false en caso contrario
     */
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

    /**
     * Rechaza una solicitud de préstamo.
     * Cambia el estado de SOLICITUD a RECHAZADO y marca el libro como disponible.
     * Utiliza transacción para asegurar consistencia.
     * 
     * @param idPrestamo el ID del préstamo a rechazar
     * @param idLibro el ID del libro asociado al préstamo
     * @return true si el rechazo fue exitoso, false en caso contrario
     */
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

    /**
     * Lista todas las solicitudes de préstamo pendientes de aprobación.
     * Son préstamos con estado SOLICITUD, ordenados por fecha ascendente.
     * 
     * @return una lista de solicitudes pendientes
     */
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
