# --- ETAPA 1: Compilación (Usamos Java 17 ahora) ---
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# 1. Copiamos el pom y descargamos dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# 2. Copiamos el código y generamos el JAR
COPY src ./src
RUN mvn clean package -DskipTests

# --- ETAPA 2: Ejecución (También con Java 17) ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 3. Traemos el archivo de la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Puerto ajustado a tu Swagger
EXPOSE 9898

ENTRYPOINT ["java", "-jar", "app.jar"]