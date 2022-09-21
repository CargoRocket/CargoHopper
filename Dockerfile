# build stage
FROM maven:3-openjdk-18
WORKDIR /app
COPY . .
RUN mvn -DskipTests clean install 
EXPOSE 8989
CMD ["java", "-Ddw.graphhopper.datareader.file=data/stuttgart.pbf", "-jar", "web/target/graphhopper-web-4.0-SNAPSHOT.jar", "server", "config.yml"]
