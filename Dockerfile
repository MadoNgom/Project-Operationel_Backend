# Dockerfile multi-stage pour application Spring Boot
FROM maven:3.9.5-openjdk-17 AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de configuration Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copier le code source
COPY src src

# Compiler l'application
RUN mvn clean package -DskipTests

# Deuxième stage : Runtime
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Exposer le port de l'application
EXPOSE 8080

# Créer un utilisateur non-root pour la sécurité
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]