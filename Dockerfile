# Java 21 JDK 기반 이미지 사용
FROM eclipse-temurin:21-jdk

# 작업 디렉토리 설정
WORKDIR /app

# 현재 디렉토리의 모든 파일 복사
COPY . .

# Gradle로 프로젝트 빌드
RUN ./gradlew clean bootJar -x test

# JAR 파일 실행 (파일명 정확히 확인!)
CMD ["java", "-jar", "build/libs/sorbet-0.0.1-SNAPSHOT.jar"]
