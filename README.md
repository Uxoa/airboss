# **Gestor de Aerolíneas - AirBoss**

![AirBoss Preview](https://github.com/user-attachments/assets/4570f087-d1c4-471e-a5c7-04fca486bece)

Un sistema completo para la gestión eficiente de aerolíneas, diseñado para manejar usuarios, vuelos, reservas, aeropuertos y rutas con facilidad.

## **Organización**
- [Tablero de Trabajo en Trello](https://trello.com/b/bwAVYMc1/aerolineas)

## **Características Principales**
- **Gestión de Usuarios**: Registro, roles (administrador y usuario) y autenticación segura.
- **Control de Vuelos**: Creación, actualización y visualización de vuelos disponibles.
- **Reservas**: Gestión de reservas por usuario, con control de plazas y estados.
- **Rutas y Aeropuertos**: Mantenimiento de rutas entre aeropuertos, incluyendo duración y disponibilidad.
- **Estadísticas y Reportes**: Visualización de datos clave para optimizar operaciones.
- **Comunicación Frontend-Backend**: Integración con **JavaScript** mediante `fetch` para realizar peticiones al backend.

## **Tecnologías Utilizadas**
- **Backend**: Spring Boot 3.4.1
- **Base de Datos**: MySQL
- **Seguridad**: Spring Security, JWT
- **Frontend**: Thymeleaf, Mustache, JavaScript (Fetch API)
- **Gestión de Dependencias**: Maven
- **Plantillas de Vista**: Mustache para renderizar contenido dinámico

## **Instalación y Configuración**
1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/usuario/airboss.git
   cd airboss
   ```

2. **Configurar la base de datos**:
   - Asegúrate de que MySQL esté corriendo y crea la base de datos:
   ```sql
   CREATE DATABASE airbosscms;
   ```
   - Configura las credenciales en `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/airbosscms
   spring.datasource.username=root
   spring.datasource.password=paloma
   ```

3. **Compilar y ejecutar el proyecto**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## **API Endpoint**
Todas las API están disponibles en:
```
http://localhost:8080/api
```

## **Estructura de la Base de Datos**

Las tablas principales incluyen:
- **profiles**: Información de los perfiles de usuario.
- **users**: Datos de autenticación.
- **roles & user_roles**: Roles y asignación de roles.
- **airports**: Aeropuertos disponibles.
- **flights**: Información de vuelos.
- **bookings**: Reservas de vuelos.

## **Autenticación**
El sistema usa **Spring Security con JWT** para la autenticación.
- **Login**: `/api/auth/login`
- **Registro**: `/api/auth/register`

## **Comunicación Frontend-Backend**
El frontend interactúa con el backend mediante JavaScript `fetch`, Mustache y Thymeleaf para una experiencia fluida.

Ejemplo de petición a la API:
```javascript
fetch('http://localhost:8080/api/flights')
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error('Error:', error));
```

## **Contribución**
Las contribuciones son bienvenidas. Por favor, abre un PR o reporta issues en GitHub.


