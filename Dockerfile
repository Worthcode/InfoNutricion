# Usa una imagen oficial de Java
FROM eclipse-temurin:17-jdk

# Crea un directorio dentro del contenedor
WORKDIR /app

# Copia el archivo jar al contenedor
COPY target/*.jar app.jar

# Expone el puerto donde corre la app
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]