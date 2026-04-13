# API Gateway

This service implements the API Gateway (edge) using Spring Cloud Gateway.

Key links and references

- Spring Cloud Gateway: https://docs.spring.io/spring-cloud-gateway/reference/
- Spring Boot Maven Plugin: https://docs.spring.io/spring-boot/docs/

Notes

- The project inherits settings from the parent POM. Some elements like `<license>` and `<developers>` may be overridden in the project POM to avoid unwanted inheritance.

Running locally

From the `api-gateway` folder:

```powershell
# run with Maven wrapper
.\mvnw.cmd spring-boot:run
```

Build

```powershell
.\mvnw.cmd clean package -DskipTests
```
