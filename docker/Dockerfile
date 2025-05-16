# Usamos una imagen base de OpenJDK 17 (ajusta versión si usas otra)
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR generado al contenedor
COPY target/bibliotecaLuisDurandD-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto donde corre la app (ajusta si usas otro)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]