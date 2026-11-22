FROM java:8
volume /data
ADD target/spring-boot-security-jwt-mongodb-1.0.jar app.jar
EXPOSE 8080
 
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongoservice.default.svc.cluster.local/ParkInDB","-jar","app.jar"]


