mvn spring-boot:build-image

docker run -d -p 0.0.0.0:8081:8081 --name hackathon software:0.0.1-SNAPSHOT