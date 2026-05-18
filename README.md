# Retrofit RENIEC Spring Boot

Proyecto de ejemplo en Spring Boot para consumir una API externa de RENIEC usando Retrofit.

Este proyecto no usa base de datos, JPA, Hibernate ni repositorios. Su objetivo es recibir un DNI desde un endpoint propio, consultar la API externa de Decolecta y devolver la informacion encontrada.

## Tecnologias usadas

- Java 17
- Spring Boot 3.3.5
- Retrofit 2.9.0
- Gson Converter
- Lombok
- Maven

## Flujo del proyecto

```text
Cliente -> Controller -> Service -> Retrofit -> API Decolecta RENIEC -> Respuesta JSON
```

El endpoint principal del proyecto es:

```http
GET /api/person/find/{dni}
```

Ejemplo:

```http
GET http://localhost:8080/api/person/find/12345678
```

Internamente, Retrofit consulta:

```text
https://api.decolecta.com/v1/reniec/dni?numero=12345678
```

## Configuracion del token

El token de Decolecta se lee desde una variable de entorno llamada `DECOLECTA_TOKEN`.

En PowerShell:

```powershell
$env:DECOLECTA_TOKEN="tu_token_aqui"
```

Luego puedes ejecutar el proyecto:

```powershell
.\mvnw.cmd spring-boot:run
```

## Ejecutar pruebas

```powershell
.\mvnw.cmd test
```

## Patrones usados

- Singleton: se usa en `ClientRetrofit` para reutilizar una sola instancia de Retrofit.
- Service Layer: `PersonService` y `PersonServiceImpl` separan la logica del controller.
- DTO: `ReniecResponse` representa la respuesta de la API externa.
- Client/Adapter: `ClientReniecService` adapta una llamada Java a una peticion HTTP externa.
- Dependency Injection: Spring inyecta el servicio dentro del controller.

## Diferencia con un proyecto con JPA

Este proyecto solo consume una API externa y devuelve la respuesta. Por eso no necesita:

- `spring-boot-starter-data-jpa`
- `@Entity`
- `JpaRepository`
- configuracion `spring.datasource`
- tablas en base de datos

Si se quisiera guardar historial de consultas o registrar personas, ahi si tendria sentido agregar JPA, una base de datos y repositorios.
