FROM maven:3.8.4-openjdk-17
VOLUME /tmp
COPY . /app
WORKDIR /app
RUN mvn clean install -DskipTests
ENTRYPOINT [ "mvn", "spring-boot:run" ]