# 1. JDK 이미지 기반
FROM openjdk:21-jdk

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 JAR 복사
COPY build/libs/sorbet-0.0.1-SNAPSHOT.jar app.jar

# 4. 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
