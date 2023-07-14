# Используемые технологии

Java 17
Spring Boot
Maven
Rest API
Spring Data JPA
PostgreSQL
Spring Boot Security
JWT


# Quickstart

Чтобы использовать данное приложение, необходимо вставить данные для подключения к PostgreSql в Environmental variables в виде DB_USERNAME, DB_URL, DB_PASSWORD. 

Изначально все методы недоступны, чтобы получить к ним доступ необходимо обратиться к эндпойнту /api/auth/register или /api/auth/login, которые возвращают два JWT токена. Токен действует 5 минут. Чтобы использовать остальные методы, необходимо авторизоваться