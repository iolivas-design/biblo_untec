package cl.dig.biblo.modelo;

/**
 * Clase que representa un libro en la biblioteca digital.
 * Contiene información del libro y su estado de disponibilidad.
 * 
 * @author Biblioteca Digital UNTEC
 * @version 1.0
 */
public class Libro {
    /** Identificador único del libro */
    private int idLibro;
    
    /** Título del libro */
    private String titulo;
    
    /** Autor del libro */
    private String autor;
    
    /** Género literario del libro */
    private String genero;
    
    /** Indica si el libro está disponible para préstamo */
    private boolean disponible;
    
    /** Estado del libro: disponible, solicitado, en_prestamo */
    private String estado;

    /**
     * Constructor por defecto de la clase Libro.
     */
    public Libro() {}

    /**
     * Constructor que inicializa un libro con sus atributos principales.
     * 
     * @param idLibro Identificador único del libro
     * @param titulo Título del libro
     * @param autor Nombre del autor del libro
     * @param genero Género literario del libro
     * @param disponible Indica si el libro está disponible para préstamo
     */
    public Libro(int idLibro, String titulo, String autor, String genero, boolean disponible) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.disponible = disponible;
        this.estado = disponible ? "disponible" : "en_prestamo";
    }

    /**
     * Obtiene el identificador único del libro.
     * @return el ID del libro
     */
    public int getIdLibro() { return idLibro; }
    
    /**
     * Establece el identificador único del libro.
     * @param idLibro el ID del libro a establecer
     */
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }
    
    /**
     * Obtiene el título del libro.
     * @return el título del libro
     */
    public String getTitulo() { return titulo; }
    
    /**
     * Establece el título del libro.
     * @param titulo el título a establecer
     */
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    /**
     * Obtiene el nombre del autor del libro.
     * @return el nombre del autor
     */
    public String getAutor() { return autor; }
    
    /**
     * Establece el nombre del autor del libro.
     * @param autor el nombre del autor a establecer
     */
    public void setAutor(String autor) { this.autor = autor; }
    
    /**
     * Obtiene el género literario del libro.
     * @return el género del libro
     */
    public String getGenero() { return genero; }
    
    /**
     * Establece el género literario del libro.
     * @param genero el género a establecer
     */
    public void setGenero(String genero) { this.genero = genero; }
    
    /**
     * Verifica si el libro está disponible para préstamo.
     * @return true si el libro está disponible, false en caso contrario
     */
    public boolean isDisponible() { return disponible; }
    
    /**
     * Establece la disponibilidad del libro.
     * @param disponible true si el libro está disponible, false en caso contrario
     */
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    
    /**
     * Obtiene el estado actual del libro.
     * @return el estado del libro (disponible, solicitado, en_prestamo)
     */
    public String getEstado() { return estado; }
    
    /**
     * Establece el estado actual del libro.
     * @param estado el estado a establecer (disponible, solicitado, en_prestamo)
     */
    public void setEstado(String estado) { this.estado = estado; }
}