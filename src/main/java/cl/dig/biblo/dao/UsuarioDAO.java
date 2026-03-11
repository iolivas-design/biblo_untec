package cl.dig.biblo.dao;

import cl.dig.biblo.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase DAO (Data Access Object) para gestionar operaciones de base de datos 
 * relacionadas con la entidad Usuario.
 * Proporciona métodos para validar, registrar y buscar usuarios.
 * 
 * @author Biblioteca Digital UNTEC
 * @version 1.0
 */
public class UsuarioDAO {

    /**
     * Valida las credenciales de un usuario consultando la base de datos.
     * Busca un usuario por email y verifica su contraseña.
     * 
     * @param email el correo electrónico del usuario
     * @param password la contraseña del usuario
     * @return un objeto Usuario si las credenciales son válidas, null en caso contrario
     */
    public Usuario validarUsuario(String email, String password) {
        Usuario usuario = null;
        String sql = "SELECT id_usuario, nombre, email, password, tipo_usuario FROM usuarios WHERE email = ? AND password = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setTipoUsuario(rs.getString("tipo_usuario"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    /**
     * Registra un nuevo usuario en la base de datos con tipo "general".
     * 
     * @param nombre el nombre completo del usuario
     * @param email el correo electrónico del usuario
     * @param password la contraseña del usuario
     * @return true si el registro fue exitoso, false en caso contrario
     */
    public boolean registrarUsuario(String nombre, String email, String password) {
        String sql = "INSERT INTO usuarios (nombre, email, password, tipo_usuario) VALUES (?, ?, ?, 'general')";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, email);
            ps.setString(3, password);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifica si un correo electrónico ya existe en la base de datos.
     * 
     * @param email el correo a verificar
     * @return true si el email ya existe, false en caso contrario
     */
    public boolean emailExiste(String email) {
        String sql = "SELECT 1 FROM usuarios WHERE email = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lista todos los usuarios de tipo "general" de la base de datos.
     * 
     * @return una lista de usuarios generales ordenados por nombre
     */
    public java.util.List<Usuario> listarTodos() {
        java.util.List<Usuario> lista = new java.util.ArrayList<>();
        String sql = "SELECT id_usuario, nombre, email, tipo_usuario FROM usuarios WHERE tipo_usuario = 'general' ORDER BY nombre";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setTipoUsuario(rs.getString("tipo_usuario"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}