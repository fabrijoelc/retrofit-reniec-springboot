# Retrofit RENIEC Spring Boot

Proyecto de ejemplo en Spring Boot para consumir una API externa de RENIEC usando Retrofit y guardar personas en PostgreSQL con JPA.

El proyecto recibe un DNI, consulta la API externa de Decolecta, transforma la respuesta a una entidad y permite guardar, listar, buscar, actualizar estado y eliminar de forma logica una persona.

## Tecnologias usadas

- Java 17
- Spring Boot 3.3.5
- Retrofit 2.9.0
- Gson Converter
- Spring Data JPA
- PostgreSQL
- Lombok
- Maven

## Flujo del proyecto

```text
Cliente -> Controller -> Service -> Retrofit -> API Decolecta RENIEC
                              -> Mapper -> Repository -> PostgreSQL
```

## Endpoints principales

```http
GET /api/person/find/{dni}
POST /api/person/save/{dni}
GET /api/person/
GET /api/person/{id}
PUT /api/person/{id}
PUT /api/person/{id}/status/{status}
DELETE /api/person/{id}
```

Ejemplo:

```http
GET http://localhost:8080/api/person/find/12345678
```

Internamente, Retrofit consulta:

```text
https://api.decolecta.com/v1/reniec/dni?numero=12345678
```

## Base de datos

El proyecto usa PostgreSQL. La configuracion actual espera una base de datos local:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/retrofit_db
spring.datasource.username=postgres
spring.datasource.password=cero
spring.jpa.hibernate.ddl-auto=update
```

Antes de ejecutar el proyecto, crea la base de datos:

```sql
CREATE DATABASE retrofit_db;
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
- Repository: `PersonRepository` encapsula el acceso a base de datos.
- Mapper: `PersonMapper` convierte `ReniecResponse` a `PersonEntity`.
- Client/Adapter: `ClientReniecService` adapta una llamada Java a una peticion HTTP externa.
- Dependency Injection: Spring inyecta el servicio dentro del controller.
