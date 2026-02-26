# JuanRodrigo_CamachoPerez - Users Web Service (CRUD) - Spring Boot 3 (Java 17/21)

Servicio web REST para consumir desde Postman:
- CRUD `/users`
- `GET /users?sortedBy=[email|id|name|phone|tax_id|created_at]`
- `GET /users?filter=[field]+[co|eq|sw|ew]+[value]`
- `/login` con `tax_id` como username
- H2 embebida (tablas vacías al iniciar para que metas datos desde Postman)
- Password AES-256 (se almacena cifrada) y NO se devuelve en responses
- `created_at` en TZ Madagascar `dd-MM-yyyy HH:mm`
- Validación RFC para `tax_id`, phone (10 dígitos + country code permitido + AndresFormat) y unicidad `tax_id`

## Ejecutar
```bash
mvn clean test
mvn spring-boot:run
```

## URLs
- API base: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger
- H2 Console: http://localhost:8080/h2-console  (JDBC URL: `jdbc:h2:mem:techtest`)

## Endpoints (para Postman)
- GET  http://localhost:8080/users
- GET  http://localhost:8080/users?sortedBy=email
- GET  http://localhost:8080/users?filter=name+co+user
  - Nota: si Postman te cambia '+' por espacio, también funciona: `filter=name co user`
  - Alternativa: URL encode el '+': `%2B` (ej: `filter=name%2Bco%2Buser`)
- GET  http://localhost:8080/users/{id}
- POST http://localhost:8080/users
- PATCH http://localhost:8080/users/{id}
- DELETE http://localhost:8080/users/{id}
- POST http://localhost:8080/login

## Postman
Importa (incluidos en el repo):
- `postman/JuanRodrigo_CamachoPerez.postman_collection.json`
- `postman/JuanRodrigo_CamachoPerez.postman_environment.json`
