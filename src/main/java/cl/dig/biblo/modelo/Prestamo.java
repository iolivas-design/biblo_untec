package cl.dig.biblo.modelo;

import java.sql.Date;

public class Prestamo {
    private int idPrestamo;
    private int idUsuario;
    private int idLibro;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private String nombreUsuario;
    private String tituloLibro;

    public Prestamo() {}

    public int getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(int idPrestamo) { this.idPrestamo = idPrestamo; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public int getIdLibro() { return idLibro; }
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }
    public Date getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(Date fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public Date getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(Date fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }
}