# Esun Bank Backend - E-commerce (Backend Only)

Backend-only implementation for the interview task. Tech stack:
- Spring Boot 3 (Java 17), RESTful API
- Spring JDBC + JdbcTemplate (parameterized SQL to prevent SQL injection)
- MySQL **or** PostgreSQL (choose one profile)
- Transactional order creation with stored procedure `sp_reserve_stock`
- DB DDL & DML in `/DB/*`

## How to run (MySQL)
1. Create DB `esun` and run scripts in this order:
   - `DB/mysql/01_schema.sql`
   - `DB/mysql/02_procedures.sql`
   - `DB/mysql/03_seed.sql`
2. Start backend:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=mysql
   ```

## How to run (PostgreSQL)
1. Create DB `esun` and run scripts in this order:
   - `DB/postgres/01_schema.sql`
   - `DB/postgres/02_procedures.sql`
   - `DB/postgres/03_seed.sql`
2. Start backend:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=postgres
   ```

## API

### List products
```
GET /api/products?inStock=true
```

### Create product
```
POST /api/products
Content-Type: application/json
{
  "productId": "P004",
  "productName": "超好吃布丁",
  "price": 80,
  "quantity": 100
}
```

### Create order (transactional, reserves stock via stored procedure)
```
POST /api/orders
Content-Type: application/json

{
  "memberId": "55688",
  "items": [
    {"productId": "P002", "quantity": 1},
    {"productId": "P003", "quantity": 1}
  ]
}
```

### Get order
```
GET /api/orders/{orderId}
```

## Security
- All SQL uses parameter binding (prevents SQL injection)
- Use JSON-only API responses (XSS surface minimized); validate inputs with `jakarta.validation`

## Notes
- `sp_reserve_stock` is used to atomically decrement stock per item. The service method is annotated `@Transactional` so any failure rolls back.
- Port defaults to 8080. Configure DB creds in `src/main/resources/application.yml`.
- This repo intentionally omits a frontend per the assignment focus.
