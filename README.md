# 📚 Biblioteca Digital UNTEC

Sistema de gestión de biblioteca digital desarrollado con Java, JSP y Jakarta EE, que permite a los usuarios solicitar préstamos de libros y a los bibliotecarios gestionar el inventario.

## 🎯 Características

### Para Usuarios Generales
- 🔐 **Autenticación**: Registro e inicio de sesión seguro
- 📖 **Catálogo de Libros**: Explorar todos los libros disponibles
- 📝 **Solicitar Préstamo**: Solicitar libros disponibles para préstamo
- 📋 **Mis Préstamos**: Ver historial de préstamos personales
- 📤 **Devolver Libro**: Registrar devoluciones de libros

### Para Bibliotecarios
- ⚙️ **Gestión de Préstamos**: Aprobar/rechazar solicitudes de préstamo
- 📊 **Asignar Préstamos**: Asignar libros directamente a usuarios
- 📚 **Gestionar Libros**: Crear y administrar el catálogo
- 👥 **Ver Historial**: Acceso al historial completo del sistema
- 🔎 **Búsqueda Avanzada**: Autocomplete para seleccionar usuarios y libros

## 🏗️ Arquitectura

### Estructura del Proyecto
```
biblo_untec/
├── src/main/java/cl/dig/biblo/
│   ├── modelo/               # Entidades de dominio
│   │   ├── Libro.java
│   │   ├── Usuario.java
│   │   └── Prestamo.java
│   ├── dao/                  # Data Access Objects
│   │   ├── LibroDAO.java
│   │   ├── UsuarioDAO.java
│   │   ├── PrestamoDAO.java
│   │   └── ConexionBD.java
│   └── controlador/          # Servlets (Controllers)
│       ├── LoginServlet.java
│       ├── RegistroServlet.java
│       ├── LibroServlet.java
│       ├── PrestamoServlet.java
│       ├── MisPrestamosServlet.java
│       ├── GestionarPrestamosServlet.java
│       └── DevolverServlet.java
├── src/main/webapp/          # Vistas JSP
│   ├── index.jsp
│   ├── login.jsp
│   ├── registro.jsp
│   ├── catalogo.jsp
│   ├── mis-prestamos.jsp
│   ├── gestionar-prestamos.jsp
│   ├── formulario-libro.jsp
│   ├── logout.jsp
│   └── WEB-INF/
│       ├── web.xml
│       └── jspf/             # JSP Fragments (incluibles)
│           ├── header.jspf
│           └── footer.jspf
└── src/main/resources/
    └── db.properties         # Configuración de base de datos
```

### Patrones de Diseño
- **MVC**: Model-View-Controller con Servlets y JSP
- **DAO**: Acceso a datos mediante objetos de acceso a datos
- **Singleton**: Gestión de conexiones a base de datos

## 🛠️ Tecnologías

### Backend
- **Java 21**: Lenguaje de programación
- **Jakarta EE 6**: Framework web
- **Jakarta Servlet 5.0**: Servlets
- **Jakarta JSP/JSTL 3.0**: Vistas dinámicas

### Base de Datos
- **MariaDB**: Sistema gestor de base de datos relacionales
- **JDBC**: Acceso a base de datos

### Frontend
- **Bootstrap 5.3.2**: Framework CSS para responsive design
- **Tom Select 2.2.2**: Biblioteca para autocompletado en selects
- **HTML5 & CSS3**: Estructura y estilos

### Herramientas
- **Maven 3.8+**: Gestión de dependencias y compilación
- **Tomcat 10+**: Servidor de aplicaciones

## 📋 Requisitos Previos

- Java JDK 21 o superior
- Maven 3.8.0 o superior
- MariaDB 10.5 o superior
- Servidor Apache Tomcat 10.0 o superior

## 🚀 Instalación y Configuración

### 1. Clonar el Repositorio
```bash
git clone <URL_REPOSITORIO>
cd biblo_untec
```

### 2. Configurar Base de Datos
Edita `src/main/resources/db.properties`:
```properties
db.driver=org.mariadb.jdbc.Driver
db.url=jdbc:mariadb://localhost:3306/biblioteca
db.usuario=root
db.password=tu_password
```

### 3. Crear Base de Datos
```sql
CREATE DATABASE biblioteca DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE biblioteca;

-- Tabla de usuarios
CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    tipo_usuario ENUM('general', 'bibliotecario') DEFAULT 'general',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de libros
CREATE TABLE libros (
    id_libro INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    genero VARCHAR(100),
    disponible BOOLEAN DEFAULT TRUE,
    estado ENUM('disponible', 'solicitado', 'en_prestamo') DEFAULT 'disponible',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de préstamos
CREATE TABLE prestamos (
    id_prestamo INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_libro INT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion DATE,
    estado ENUM('SOLICITUD', 'APROBADO', 'RECHAZADO', 'COMPLETADO') DEFAULT 'SOLICITUD',
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_libro) REFERENCES libros(id_libro)
);

-- Carga de Datos Iniciales
INSERT INTO usuarios (nombre, email, password, tipo_usuario) VALUES ('Usuario Test', 'test@club.cl', '123456', 'bibliotecario');
```

### 4. Compilar el Proyecto
```bash
mvn clean compile
```

### 5. Empaquetar la Aplicación
```bash
mvn package
```

### 6. Desplegar en Tomcat
Copia el archivo `target/biblo_untec.war` a la carpeta `webapps` de Tomcat:
```bash
cp target/biblo_untec.war $CATALINA_HOME/webapps/
```

### 7. Iniciar Tomcat
```bash
$CATALINA_HOME/bin/startup.sh  # En Linux/Mac
$CATALINA_HOME/bin/startup.bat # En Windows
```

### 8. Acceder a la Aplicación
Abre tu navegador y ve a: `http://localhost:8080/biblo_untec`

## 📚 Documentación API

### Generar JavaDoc
```bash
mvn javadoc:javadoc
```

La documentación se generará en `target/site/apidocs/index.html`

### Servlets Principales

#### LoginServlet
- **URL**: `/login`
- **Método**: POST
- **Parámetros**: `email`, `password`
- **Respuesta**: Redirección a catálogo o error de autenticación

#### RegistroServlet
- **URL**: `/registro`
- **Método**: POST
- **Parámetros**: `nombre`, `email`, `password`, `confirmPassword`
- **Respuesta**: Redirección a inicio de sesión

#### LibroServlet
- **URL**: `/libros`
- **Método**: GET
- **Respuesta**: Listado de libros disponibles

#### PrestamoServlet
- **URL**: `/prestamo`
- **Método**: POST
- **Parámetros**: `idLibro`
- **Respuesta**: Solicitud de préstamo registrada

#### MisPrestamosServlet
- **URL**: `/mis-prestamos`
- **Método**: GET
- **Respuesta**: Historial de préstamos del usuario actual

#### GestionarPrestamosServlet
- **URL**: `/gestionar-prestamos`
- **Métodos**: GET, POST
- **Parámetros (POST)**: `accion`, `idUsuario`, `idLibro`, `idPrestamo`
- **Acciones**: `asignar`, `aprobarsolicitud`, `rechazarsolicitud`, `devolver`

#### DevolverServlet
- **URL**: `/devolver`
- **Método**: POST
- **Parámetros**: `idPrestamo`, `idLibro`
- **Respuesta**: Devolución registrada

## 🎨 Interfaz de Usuario

### Paleta de Colores
- **Fondo**: Gris oscuro (#1a1a1a)
- **Texto principal**: Gris claro (#d0d0d0)
- **Acentos**: Azul (#8080ff)
- **Éxito**: Verde oscuro (#3d663d)
- **Error**: Rojo oscuro (#663333)
- **Advertencia**: Amarillo oscuro (#666633)

### Temas
- Tema Oscuro: Por defecto con degradado y patrón de grilla
- Responsive Design con Bootstrap 5.3.2

## 🔐 Seguridad

- **Contraseñas**: Almacenadas con hash (implementar en DAO)
- **Sesiones**: Gestión mediante HttpSession
- **Validación**: Validación de entrada en servidor y cliente
- **JDBC Prepared Statements**: Prevención de SQL Injection

### Recomendaciones de Seguridad
1. Implementar hashing de contraseñas (BCrypt, Argon2)
2. Usar HTTPS en producción
3. Implementar CSRF tokens
4. Validación de roles y permisos (RBAC)
5. Auditoría de operaciones críticas

## 🧪 Testing

### Ejecutar Tests
```bash
mvn test
```

### Cobertura de Tests
```bash
mvn jacoco:report
```

## 📊 Diagrama de Flujo

```
Usuario No Autenticado
        ↓
    Login/Registro
        ↓
    Sesión Iniciada
        ↓
    ├─→ Ver Catálogo
    │   ├─→ Solicitar Préstamo
    │   └─→ Ver Mis Préstamos
    │
    └─→ Si es Bibliotecario:
        ├─→ Gestionar Solicitudes
        ├─→ Asignar Préstamos
        ├─→ Crear/Editar Libros
        └─→ Ver Historial Completo
```

## 🐛 Solución de Problemas

### Error de Conexión a Base de Datos
- Verificar que MariaDB esté en ejecución
- Validar credenciales en `db.properties`
- Comprobar que la base de datos existe

### ClassNotFoundException
```bash
mvn clean compile
mvn package
# Limpiar caché de Maven si persiste
rm -rf ~/.m2/repository
mvn clean package
```

### Errores de JSP
- Verificar ruta de includes: `/WEB-INF/jspf/`
- Confirmar que los archivos `.jspf` existen
- Revisar sintaxis JSTL

### Autocomplete no funciona en Gestionar Préstamos
- Verificar que Tom Select se carga desde CDN
- Abrir consola del navegador (F12) para ver errores
- Asegurar que DOM está completamente cargado antes de inicializar

## 📝 Notas de Desarrollo

### Convenciones de Código
- **Paquetes**: `cl.dig.biblo.*`
- **Nomenclatura**: camelCase para variables y métodos
- **Constantes**: UPPER_SNAKE_CASE
- **Clases DAO**: `{Entidad}DAO.java`
- **Servlets**: `{Accion}Servlet.java`

### Estructura de Sesión
```java
HttpSession session = request.getSession();
Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
Boolean esAdmin = (Boolean) session.getAttribute("esAdmin");
```

### Acceso a Base de Datos
```java
ConexionBD conexion = new ConexionBD();
Connection conn = conexion.obtenerConexion();
// ... usar conexión
conexion.cerrarConexion();
```

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver archivo `LICENSE` para más detalles.

## 👥 Autores

- **Desarrollador Principal**: Iván Oliva
- **Institución**: Talento Digital - SENCE - Alkemy

## 📞 Contacto y Soporte

Para preguntas o soporte:
- 📧 Email: soporte@untec.cl
- 🐛 Issues: [GitHub Issues](https://github.com/tu-usuario/biblo_untec/issues)
- 📖 Wiki: [Documentación](https://github.com/tu-usuario/biblo_untec/wiki)

## 🎓 Referencias y Recursos

- [Documentación Official de Jakarta EE](https://eclipse-ee.github.io/jakartaee-tutorial/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Bootstrap 5 Documentation](https://getbootstrap.com/docs/)
- [Tom Select Documentation](https://tom-select.js.org/)
- [MariaDB Documentation](https://mariadb.com/docs/)

---

**Última actualización**: Marzo 2026
