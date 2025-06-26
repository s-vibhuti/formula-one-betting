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



## ✅ Running Locally

1. Clone the repository
2. Start Docker on your local system
3. Run following commands under root directory

```bash
mvn clean install 
docker-compose build --no-cache
docker-compose up
```

---

## 🔒 Authentication

- Use `/auth/login` to authenticate and receive a JWT token.
- Secure endpoints require the `Authorization: Bearer <token>` header.

---

## 📬 Callback API (Event Outcome)

POST `/api/f1/events/outcome`

- Headers: `X-API-KEY: openf1-key`
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

#Fetch user details
curl --location 'http://localhost:8080/api/user/details' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMiIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dLCJpYXQiOjE3NTA5NTI1MzIsImV4cCI6MTc1MDk4ODUzMn0.mBFIhi3HkU3_CJTTuWKbDGD1ycp3Q5TEITLr2-BbkTM'


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
