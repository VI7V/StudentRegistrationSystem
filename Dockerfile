# Use Java 17
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven build files
COPY pom.xml .
COPY src ./src

# Build the application
RUN ./mvnw clean package || mvn clean package

# Expose Render's port
EXPOSE 10000

# Run the JAR
CMD ["sh", "-c", "java -jar target/*.jar"]
