FROM maven:latest
RUN mkdir /app
WORKDIR /app
COPY . .
EXPOSE 8080

CMD ["mvn", "spring-boot:run"]