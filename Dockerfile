FROM maven:3-jdk-8-alpine
WORKDIR /app
COPY . /app
RUN mvn clean install -Dmaven.test.skip=true
CMD java -jar target/spring-boot-with-fcm-0.0.1.jar
