FROM eclipse-temurin:25-jdk AS build

ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

WORKDIR /app

# Copy Maven wrapper and POM
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Generate Maven settings.xml for GitHub Packages authentication
RUN mkdir -p /root/.m2 && \
    printf '<settings><servers><server><id>github</id><username>%s</username><password>%s</password></server></servers></settings>' \
    "$GITHUB_USERNAME" "$GITHUB_TOKEN" > /root/.m2/settings.xml

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]