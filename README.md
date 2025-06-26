# 🏁 Formula One Betting Application

This is a Spring Boot-based backend system that allows users to place bets on Formula 1 events using external race data from the OpenF1 API. The system supports user registration, JWT-based authentication, real-time event outcomes, and asynchronous processing using RabbitMQ.

---

## 🚀 Features

- JWT-based Authentication (Login/Signup)
- View F1 Sessions & Driver Market (via OpenF1 API)
- Place bets on F1 sessions
- Manage user balances
- Process event outcomes asynchronously using RabbitMQ
- Config-driven architecture (all 3rd party configs via DB)
- Liquibase for schema and data management

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3.2.5
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- MySQL 8+
- Liquibase
- RabbitMQ
- Maven
- Lombok
- OpenF1 API (external)

---

## ⚙️ Configuration

All configuration values are defined in `application.yml` located in `src/main/resources/`.

Make sure you configure the following:

### 1. Database:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/f1_betting
    username: your_db_user
    password: your_db_password
```

### 2. JWT:
```yaml
jwt:
  secret: your_jwt_secret_key
  expiration: 86400000
```

### 3. OpenF1 API URLs (fetched from DB `config` table):
```yaml
external:
  apis:
    openf1:
      sessions-url: https://api.openf1.org/v1/sessions
      drivers-url: https://api.openf1.org/v1/drivers
```

### 4. RabbitMQ:
```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    queue:
      eventOutcome: event-outcome-queue
    exchange:
      eventOutcome: event-outcome-exchange
    routing-key:
      eventOutcome: event.outcome
```

---

## 🗃️ Database Setup

Uses Liquibase for DB migrations. The changelog files are located in:

```
src/main/resources/db/changelog/
```

You can add initial config rows and schema via `config-data.yaml`.

---

## ✅ Running Locally

1. Clone the repository
2. Set up MySQL and RabbitMQ
3. Update `application.yml` with local credentials
4. Run the Spring Boot app

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🔒 Authentication

- Use `/auth/login` to authenticate and receive a JWT token.
- Secure endpoints require the `Authorization: Bearer <token>` header.

---

## 📬 Callback API (Event Outcome)

POST `/api/f1/events/outcome`

- Headers: `X-API-KEY: my-secret-api-key`
- Body: Raw payload (JSON)

Payload is stored in DB and forwarded to a RabbitMQ queue.

---

## 📨 RabbitMQ Consumer

Listens to `event-outcome-queue` and updates:
- Bet status (WON/LOST)
- User balances

---

## 📦 API Examples

```bash

# Signup
curl --location 'http://localhost:8080/auth/signup' \
--header 'Content-Type: application/json' \
--data '{
  "userId": "user123",
  "password": "pass123"
}
'

# Login- fetch jwt token from login api
curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data '{
  "userId": "user123",
  "password": "pass123"
}
'


# fetch sessions 
curl --location 'http://localhost:8080/api/f1/sessions?year=2023&sessionType=Sprint' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTIzIiwicm9sZXMiOlt7ImF1dGhvcml0eSI6IlVTRVIifV0sImlhdCI6MTc1MDg3ODA5NywiZXhwIjoxNzUwOTE0MDk3fQ.hutagBLMPXJ9u8e2wLRPtJX5NqXq97QDg2LeWhhgTCU' \
--header 'Content-Type: application/json'



# Place a bet
curl --location 'http://localhost:8080/api/f1/bets/place-bet' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTIzIiwicm9sZXMiOlt7ImF1dGhvcml0eSI6IlVTRVIifV0sImlhdCI6MTc1MDkxMjQ1MiwiZXhwIjoxNzUwOTQ4NDUyfQ.zMTQzYV9Ljise0kf_XcMAHPwJY6plab1ft2te80OQ3o' \
--header 'Content-Type: application/json' \
--data '{
        "sessionKey": 9140,
        "driverNumber": 3,
        "amount": 150.0,
        "odds": 3
      }'

#call-back event-outcomes
curl --location 'http://localhost:8080/api/external/callback/f1/event-outcome' \
--header 'Content-Type: application/json' \
--header 'X-API-KEY: openf1-key' \
--data '{
        "sessionKey": 9140,
        "winningDriverNumber": 3
      }'

```

---

## 👤 Author

**Vibhuti Sharma**  
Contact: [GitHub @s-vibhuti](https://github.com/s-vibhuti)

---

## 📄 License

MIT License – Use and modify freely for learning and demo purposes.
