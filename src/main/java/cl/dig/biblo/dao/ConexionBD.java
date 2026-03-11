package cl.dig.biblo.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase que gestiona la conexión a la base de datos MariaDB.
 * Utiliza un archivo de propiedades (db.properties) para configurar los parámetros de conexión.
 * Patrón Singleton implícito para cargar el driver y las propiedades.
 * 
 * @author Biblioteca Digital UNTEC
 * @version 1.0
 */
public class ConexionBD {
    /** Propiedades de configuración de la base de datos (URL, usuario, contraseña) */
    private static final Properties props = new Properties();

    /**
     * Bloque estático que inicializa el driver JDBC y carga las propiedades de conexión
     * desde el archivo db.properties.
     */
    static {
        try (InputStream input = ConexionBD.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.err.println("Error: No se encontró el archivo db.properties");
            } else {
                props.load(input);
                Class.forName("org.mariadb.jdbc.Driver");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene una conexión a la base de datos.
     * Utiliza la URL, usuario y contraseña configurados en db.properties.
     * 
     * @return una Connection a la base de datos, o null si hay error en la conexión
     */
    public static Connection obtenerConexion() {
        try {
            return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}