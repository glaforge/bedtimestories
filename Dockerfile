FROM openjdk:17-slim as build
WORKDIR /workspace/app
COPY gradle gradle
COPY gradle.properties gradle.properties
COPY build.gradle settings.gradle gradlew ./
COPY src src
RUN ./gradlew shadowJar --no-daemon

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /workspace/app/build/libs/bedtimestories-0.1-all.jar /app/
EXPOSE 8080
CMD ["java", "-jar", "/app/bedtimestories-0.1-all.jar"]