FROM eclipse-temurin:25-jdk AS build
WORKDIR /workspace

# Copia o wrapper e o POM primeiro para aproveitar cache de dependências
COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn
RUN chmod +x mvnw
RUN ./mvnw -B -ntp dependency:go-offline

# Copia o código-fonte e faz o build do artefato
COPY src ./src
RUN ./mvnw -B -ntp package -DskipTests

FROM eclipse-temurin:25-jre
WORKDIR /app

COPY --from=build /workspace/target/ExerciseWorksheetManager-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
