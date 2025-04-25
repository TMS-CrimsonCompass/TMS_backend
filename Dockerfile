FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/CrimsonCompass-0.0.1-SNAPSHOT.jar app.jar

# document the port you serve on
EXPOSE 8080

# allow overriding the port via $PORT (Azure injects this env var)
ENV PORT=8080

# pass that into Spring Boot as server.port  
ENTRYPOINT ["sh","-c","java -Dserver.port=$PORT -jar app.jar"]
