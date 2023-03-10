FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/Mukvengers-1.0.0-SNAPSHOT.jar
ENV MY_SERVER=${MY_SERVER} \
AWS_ACCESS_KEY=${AWS_ACCESS_KEY} \
AWS_SECRET_KEY=${AWS_SECRET_KEY} \
JWT_SECRET_KEY=${JWT_SECRET_KEY} \
SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL} \
SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME} \
SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD} \
KAKAO_CLIENT_ID=${KAKAO_CLIENT_ID} \
KAKAO_CLIENT_SECRET=${KAKAO_CLIENT_SECRET} \
KAKAO_REDIRECT_URI=${KAKAO_REDIRECT_URI} \
GOOGLE_CLIENT_ID=${GOOGLE_CLIENT_ID} \
GOOGLE_CLIENT_SECRET=${GOOGLE_CLIENT_SECRET} \
GOOGLE_REDIRECT_URI=${GOOGLE_REDIRECT_URI} \
REDIS_HOST=${REDIS_HOST} \
REDIS_PORT=${REDIS_PORT} \
ACTIVE_PROFILE=${ACTIVE_PROFILE}
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-jar", "/app.jar"]