# Etapa de build
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
RUN mvn clean install -DskipTests

# Etapa final com imagem do OpenJDK
FROM openjdk:17-jdk-slim

# Copiar o aplicativo Java para dentro do contêiner
COPY --from=build /app/target/api-back-0.0.1-SNAPSHOT.jar /app/app.jar

# Expor a porta necessária para o Spring Boot
EXPOSE 8080

# Definir o comando para rodar a aplicação Spring Boot
CMD ["java", "-jar", "/app/app.jar"]
