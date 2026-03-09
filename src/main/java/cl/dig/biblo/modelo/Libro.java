package cl.dig.biblo.modelo;

public class Libro {
    private int idLibro;
    private String titulo;
    private String autor;
    private String genero;
    private boolean disponible;

    public Libro() {}

    public Libro(int idLibro, String titulo, String autor, String genero, boolean disponible) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.disponible = disponible;
    }

    public int getIdLibro() { return idLibro; }
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}