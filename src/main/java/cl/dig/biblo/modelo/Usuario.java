package cl.dig.biblo.modelo;

/**
 * Clase que representa un usuario de la biblioteca digital.
 * Un usuario puede ser de tipo "general" (usuario regular) o "bibliotecario" (administrador).
 * 
 * @author Biblioteca Digital UNTEC
 * @version 1.0
 */
public class Usuario {
    /** Identificador único del usuario */
    private int IdUsuario;
    
    /** Nombre completo del usuario */
    private String nombre;
    
    /** Correo electrónico del usuario */
    private String email;
    
    /** Contraseña del usuario (debe almacenarse con hash en producción) */
    private String password;
    
    /** Tipo de usuario: "bibliotecario" o "general" */
    private String tipoUsuario;

    /**
     * Constructor por defecto de la clase Usuario.
     */
    public Usuario() {}

    /**
     * Obtiene el identificador único del usuario.
     * @return el ID del usuario
     */
    public int getIdUsuario() { return IdUsuario; }
    
    /**
     * Establece el identificador único del usuario.
     * @param IdUsuario el ID del usuario a establecer
     */
    public void setIdUsuario(int IdUsuario) { this.IdUsuario = IdUsuario; }
    
    /**
     * Obtiene el nombre completo del usuario.
     * @return el nombre del usuario
     */
    public String getNombre() { return nombre; }
    
    /**
     * Establece el nombre completo del usuario.
     * @param nombre el nombre a establecer
     */
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    /**
     * Obtiene el correo electrónico del usuario.
     * @return el email del usuario
     */
    public String getEmail() { return email; }
    
    /**
     * Establece el correo electrónico del usuario.
     * @param email el email a establecer
     */
    public void setEmail(String email) { this.email = email; }
    
    /**
     * Obtiene la contraseña del usuario.
     * Nota: En producción debe almacenarse y compararse con hash.
     * @return la contraseña del usuario
     */
    public String getPassword() { return password; }
    
    /**
     * Establece la contraseña del usuario.
     * Nota: En producción debe hashearse antes de almacenarse.
     * @param password la contraseña a establecer
     */
    public void setPassword(String password) { this.password = password; }
    
    /**
     * Obtiene el tipo de usuario.
     * @return el tipo de usuario ("general" o "bibliotecario")
     */
    public String getTipoUsuario() { return tipoUsuario; }
    
    /**
     * Establece el tipo de usuario.
     * @param tipoUsuario el tipo a establecer ("general" o "bibliotecario")
     */
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
}