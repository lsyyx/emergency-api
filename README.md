# 📢 Emergency Api

![Kotlin](https://img.shields.io/badge/kotlin-2.0.10-orange.svg)
![Apache 2](https://img.shields.io/badge/license-Apache2-green.svg?style=flat)

## 📓 Description
Emergency API is a Ktor-based server application designed for managing zones and incidents related to emergency services. The server provides endpoints for client applications to interact with zone and incident data. It also includes functionality to calculate the appropriate zone for each new incident based on its geographical coordinates.

## 💡 Usage
num | type | endpoint | description
----| ------------ | ------------ | -------------------
1 | GET | /zones | Returns a list of all zones.
2 | GET | /zones/{zoneId} | Returns a specific zone by its ID.
3 | GET | /incidents | Returns a list of all incidents.
4 | GET | /incidents/{zoneId} | Returns incidents associated with a specific zone.
5 | POST | /incidents/create | Creates a new incident with the provided data.
6 | POST | /incidents/update | Updates an existing incident.

## 🏁 Start

1. Clone the repository:
```bash
git clone https://github.com/yourusername/emergency-service-server.git
```
2. Open the project in IntelliJ IDEA or another Kotlin-compatible IDE.
3. Sync the project with Gradle files.
4. Build and run the server application.
5. Access the server endpoints via http://localhost:8080. You can use Postman or any other API testing tool to interact with the API.

## 🏠 Architecture
- Kotlin: The primary programming language used for the project.
- Ktor Framework: The server is built using the Ktor framework for handling HTTP requests and routing.
- SQL Exposed: For interacting with the H2 database and mapping data to entities.
- H2 Database: The database stores the zones and incidents, and H2 is used for local storage during development.
- Clean Architecture:  The project follows Clean Architecture principles for maintainability, scalability, and testability, ensuring a clear separation of concerns between the server's layers.

## License
This project is licensed under the Apache License 2.0.
