# Imagen base: Java 17 versión liviana (Alpine)
FROM eclipse-temurin:17-jre-alpine

# Carpeta interna donde vivirá la app dentro del contenedor
WORKDIR /app

# Copia el .jar compilado al contenedor (renómbrenlo si es necesario)
COPY target/pporden-0.0.1-SNAPSHOT.jar /app/app.jar

# Documenta el puerto que la app escucha
EXPOSE 8085

# Comando que se ejecuta al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]