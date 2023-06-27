FROM maven:3.8.7-openjdk-18

WORKDIR /app

COPY pom.xml .

COPY . .

RUN mvn clean package

CMD ["java", "-jar", "target/flight.jar"]
