FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/banking-management-system-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-cp", "app.jar", "com.bank.Main"]