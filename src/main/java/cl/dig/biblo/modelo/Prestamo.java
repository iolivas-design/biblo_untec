package cl.dig.biblo.modelo;

import java.sql.Date;

/**
 * Clase que representa un préstamo de libro en la biblioteca digital.
 * Un préstamo vincula un usuario con un libro durante un período de tiempo.
 * 
 * @author Biblioteca Digital UNTEC
 * @version 1.0
 */
public class Prestamo {
    /** Identificador único del préstamo */
    private int idPrestamo;
    
    /** ID del usuario que realiza el préstamo */
    private int idUsuario;
    
    /** ID del libro que se presta */
    private int idLibro;
    
    /** Fecha en que se realiza el préstamo */
    private Date fechaPrestamo;
    
    /** Fecha en que se devuelve el préstamo (null si aún no se ha devuelto) */
    private Date fechaDevolucion;
    
    /** Nombre del usuario que realiza el préstamo */
    private String nombreUsuario;
    
    /** Título del libro que se presta */
    private String tituloLibro;
    
    /** Estado del préstamo: SOLICITUD, APROBADO, RECHAZADO, COMPLETADO */
    private String estado;

    /**
     * Constructor por defecto de la clase Prestamo.
     */
    public Prestamo() {}

    /**
     * Obtiene el identificador único del préstamo.
     * @return el ID del préstamo
     */
    public int getIdPrestamo() { return idPrestamo; }
    
    /**
     * Establece el identificador único del préstamo.
     * @param idPrestamo el ID del préstamo a establecer
     */
    public void setIdPrestamo(int idPrestamo) { this.idPrestamo = idPrestamo; }
    
    /**
     * Obtiene el ID del usuario que realiza el préstamo.
     * @return el ID del usuario
     */
    public int getIdUsuario() { return idUsuario; }
    
    /**
     * Establece el ID del usuario que realiza el préstamo.
     * @param idUsuario el ID del usuario a establecer
     */
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    /**
     * Obtiene el ID del libro que se presta.
     * @return el ID del libro
     */
    public int getIdLibro() { return idLibro; }
    
    /**
     * Establece el ID del libro que se presta.
     * @param idLibro el ID del libro a establecer
     */
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }
    
    /**
     * Obtiene la fecha en que se realiza el préstamo.
     * @return la fecha del préstamo
     */
    public Date getFechaPrestamo() { return fechaPrestamo; }
    
    /**
     * Establece la fecha en que se realiza el préstamo.
     * @param fechaPrestamo la fecha a establecer
     */
    public void setFechaPrestamo(Date fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    
    /**
     * Obtiene la fecha en que se devuelve el libro.
     * @return la fecha de devolución (null si aún no se ha devuelto)
     */
    public Date getFechaDevolucion() { return fechaDevolucion; }
    
    /**
     * Establece la fecha en que se devuelve el libro.
     * @param fechaDevolucion la fecha a establecer
     */
    public void setFechaDevolucion(Date fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    
    /**
     * Obtiene el nombre del usuario que realiza el préstamo.
     * @return el nombre del usuario
     */
    public String getNombreUsuario() { return nombreUsuario; }
    
    /**
     * Establece el nombre del usuario que realiza el préstamo.
     * @param nombreUsuario el nombre a establecer
     */
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    
    /**
     * Obtiene el título del libro que se presta.
     * @return el título del libro
     */
    public String getTituloLibro() { return tituloLibro; }
    
    /**
     * Establece el título del libro que se presta.
     * @param tituloLibro el título a establecer
     */
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }
    
    /**
     * Obtiene el estado actual del préstamo.
     * @return el estado del préstamo (SOLICITUD, APROBADO, RECHAZADO, COMPLETADO)
     */
    public String getEstado() { return estado; }
    
    /**
     * Establece el estado actual del préstamo.
     * @param estado el estado a establecer (SOLICITUD, APROBADO, RECHAZADO, COMPLETADO)
     */
    public void setEstado(String estado) { this.estado = estado; }
}