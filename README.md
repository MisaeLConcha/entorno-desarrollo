# Orden-Service

## Descripción

Microservicio encargado de administrar las órdenes de compra dentro de ReadyStand, permitiendo la creación, consulta, actualización y eliminación de pedidos, además de la comunicación con los microservicios de Usuarios, Eventos, Productos y Stands para validar la información asociada a cada orden.

---

## Funcionalidades

- Crear pedidos
- Listar pedidos
- Buscar pedido por ID
- Actualizar pedido
- Eliminar pedido
- Comunicación con microservicio de Usuarios
- Comunicación con microservicio de Eventos
- Comunicación con microservicio de Productos
- Comunicación con microservicio de Stands

---

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Validation
- Spring Cloud OpenFeign
- Spring Cloud Netflix Eureka Client
- Springdoc OpenAPI (Swagger)
- MySQL
- H2 Database (Testing)
- JUnit 5
- Mockito
- Maven
- Docker
- Docker Compose

---

## Ejecución del proyecto

```bash
docker compose up -d
```

---

## Ejecución de pruebas

```bash
mvn test
```

Todas las pruebas deben finalizar con:

```text
BUILD SUCCESS
```

---

## Swagger

Disponible en:

```
http://localhost:8085/doc/swagger-ui.html
```

---

## Endpoints principales

### Obtener pedidos

```
GET /api/v3/orden
```

### Obtener pedido por ID

```
GET /api/v3/orden/{id}
```

### Crear pedido

```
POST /api/v3/orden
```

### Actualizar pedido

```
PUT /api/v3/orden/{id}
```

### Eliminar pedido

```
DELETE /api/v3/orden/{id}
```

---

## Testing

El proyecto incluye pruebas unitarias para las capas:

- Modelo
- Servicio
- Repositorio
- Controlador

Las pruebas fueron desarrolladas utilizando:

- JUnit 5
- Mockito
- Base de datos H2 para pruebas

Todas las pruebas deben finalizar con:

```text
BUILD SUCCESS
```

---

## Validaciones

El microservicio implementa validaciones mediante **Bean Validation**, incluyendo:

- Validación de campos obligatorios.
- Validación del tipo de pedido.
- Validación de existencia del usuario.
- Validación de existencia del evento.
- Validación de existencia de productos.
- Validación de existencia de stands.
- Manejo global de errores mediante `GlobalExceptionHandler`.
- Respuestas HTTP estandarizadas para errores de validación y recursos no encontrados.

---

## Integración

El microservicio consume información de los siguientes servicios mediante **OpenFeign**:

- Usuario-Service
- Evento-Service
- Producto-Service
- Stand-Service

Además, se encuentra registrado en **Eureka Server**, permitiendo el descubrimiento de servicios dentro de la arquitectura de microservicios.

---
