# Sorbet

##  개요 (Overview)

Sorbet은 익명 상담 및 커뮤니티 게시판 서비스로, 따뜻한 분위기 속에서 고민을 나누고 공감할 수 있는 공간을 목표로 합니다.  
Spring Boot 기반으로 개발되었으며, 사용자 프로필, 포인트 및 등급 시스템, 캐릭터 뽑기(가챠) 기능을 포함합니다.  

<details>
<summary>日本語 (Japanese)</summary>

Sorbetは匿名相談およびコミュニティ掲示板サービスであり、温かく安心できる雰囲気の中で悩みを共有し、共感できる空間を目指しています。  
Spring Bootをベースに開発され、ユーザープロフィール、ポイントとランクシステム、キャラクターガチャ機能を含んでいます。  

</details>

<details>
<summary>English</summary>

Sorbet is an anonymous counseling and community board service designed to provide a warm and comfortable space where users can share their concerns and connect with others.  
It is developed on Spring Boot and includes user profiles, a point and ranking system, and a character gacha feature.  

</details>

---

##  목차 (Table of Contents)
- [개요 (Overview)](#-개요-overview)
- [주요 기능 (Main Features)](#-주요-기능-main-features)
- [기술 스택 (Tech Stack)](#-기술-스택-tech-stack)
- [프로젝트 구조 (Project Structure)](#-프로젝트-구조-project-structure)
- [개발 중 오류와 해결 (Errors & Solutions)](#-개발-중-오류와-해결-errors--solutions)

---

## 주요 기능 (Main Features)

- 회원가입, 로그인 (JWT 기반 인증)  
- 게시글 작성, 수정, 삭제  
- 게시글 검색 (제목, 내용, 작성자 기준)  
- 게시글 반응 (좋아요 / 별로예요)  
- 게시글 조회수 집계  
- 공지 고정 시스템  
- 마이페이지 (닉네임, 이메일, 포인트, 등급, 캐릭터, 활동 기록 확인)  
- 랜덤 캐릭터 뽑기(가챠) 기능 (중복 허용, 등급별 연출)  
- 관리자 기능 (공지 등록, 글/댓글 관리, 권한 관리)  

<details>
<summary>日本語 (Japanese)</summary>

- 会員登録、ログイン（JWT認証）  
- 投稿の作成、編集、削除  
- 投稿検索（タイトル、内容、作成者基準）  
- 投稿リアクション（いいね／よくないね）  
- 投稿閲覧数の集計  
- お知らせ固定機能  
- マイページ（ニックネーム、メール、ポイント、ランク、キャラクター、活動記録の確認）  
- キャラクターガチャ機能（重複所持可、ランク別演出あり）  
- 管理者機能（お知らせ登録、投稿／コメント管理、権限管理）  

</details>

<details>
<summary>English</summary>

- User registration and login (JWT authentication)  
- Post creation, editing, and deletion  
- Post search (by title, content, or author)  
- Post reactions (like / dislike)  
- Post view count tracking  
- Pinned announcement system  
- My Page (nickname, email, points, rank, characters, activity history)  
- Character gacha system (allows duplicates, tier-based animations)  
- Admin features (announcement posting, post/comment management, role control)  

</details>

---

##  기술 스택 (Tech Stack)

- **Backend**: Java 17, Spring Boot 3.x, Spring Security (JWT), JPA, MySQL
- **Frontend**: Thymeleaf (JSP 리팩토링 진행 중), TailwindCSS, Toast UI Editor  
- **Infra**: AWS EC2 (JAR 직접 실행), GitHub (소스 관리)  

---

##  프로젝트 구조 (Project Structure)

```bash
sorbet/
 ├── src/
 │   ├── main/
 │   │   ├── java/com/sorbet/
 │   │   │   ├── controller/
 │   │   │   ├── entity/
 │   │   │   ├── repository/
 │   │   │   ├── security/
 │   │   │   └── service/
 │   │   └── resources/
 │   │       ├── static/
 │   │       ├── templates/
 │   │       └── application.yml
 └── build.gradle






----------------------------------------------------------


## 주요 오류와 해결 (Major Issues & Solutions)

### 1. DB 연결 실패 (MySQL RDS)
```
lost connection to server at 'handshake: reading initial communication packet'
원인:

RDS 인바운드 규칙에 MySQL(3306) 포트가 열려 있지 않음

EC2 ↔ RDS 보안 그룹 연결 누락

해결:

RDS 보안 그룹 수정 → MySQL/Aurora (3306) 추가

EC2 보안 그룹을 인바운드 소스로 지정

.env 파일을 통해 DB 접속 정보 관리

env
코드 복사
DB_HOST=xxxx.rds.amazonaws.com
DB_USERNAME=admin
DB_PASSWORD=******
DB_NAME=sorbet
<details> <summary>日本語 (Japanese)</summary>
原因:

RDSのインバウンドルールでMySQL(3306)が開放されていなかった

EC2とRDSのセキュリティグループ接続が不足していた

解決策:

RDSのセキュリティグループに「MySQL/Aurora (3306)」を追加

EC2のセキュリティグループをインバウンドソースに指定

.envファイルでDB接続情報を管理

</details> <details> <summary>English</summary>
Cause:

MySQL (3306) port not open in RDS inbound rules

Missing EC2 ↔ RDS security group connection

Solution:

Added MySQL/Aurora (3306) to RDS security group

Set EC2 security group as inbound source

Managed DB connection info via .env

</details>
2. 포트 충돌 (8080 already in use)

Web server failed to start. Port 8080 was already in use.
원인:

기존 Spring Boot 프로세스가 종료되지 않음

다른 애플리케이션이 동일 포트를 사용 중

해결:

lsof -i :8080
kill -9 <PID>
또는 application.yml에서 포트 변경

server:
  port: 8081
<details> <summary>日本語 (Japanese)</summary>
原因:

Spring Bootの既存プロセスが終了していなかった

他のアプリケーションが同じポートを使用していた

解決策:

実行中のプロセスを確認して終了

または application.yml でポートを変更

</details> <details> <summary>English</summary>
Cause:

Previous Spring Boot process not terminated

Another application already using the port

Solution:

Killed the running process

Or changed the port in application.yml

</details>
3. Thymeleaf 템플릿 파싱 오류

EL1008E: Property or field 'excerpt' cannot be found on object of type 'com.sorbet.dto.PostDto'
원인: DTO에 없는 필드(excerpt)를 템플릿에서 호출

해결:

DTO에 해당 필드 추가

또는 템플릿에서 참조 제거

<details> <summary>日本語 (Japanese)</summary>
原因:

DTOに存在しないフィールド (excerpt) をテンプレートで参照した

解決策:

DTOにフィールドを追加

またはテンプレートから参照を削除

</details> <details> <summary>English</summary>
Cause:

Referenced non-existent field (excerpt) in template

Solution:

Added field to DTO

Or removed reference in template

</details>
4. JWT 만료 처리 문제

io.jsonwebtoken.ExpiredJwtException: JWT expired at 2025-08-27T08:30:00Z
원인: JWT 만료 시 JSON 에러만 반환되고, 로그인 페이지로 이동하지 않음

해결:

Spring Security에서 만료 시 401 반환 설정

프론트엔드에서 401 감지 후 /login으로 리다이렉트

필요 시 필터에서 직접 리다이렉트 처리

java
코드 복사
if (exception instanceof ExpiredJwtException) {
    response.sendRedirect("/login?expired=true");
}
<details> <summary>日本語 (Japanese)</summary>
原因:

JWT有効期限切れ時にJSONエラーのみ返却され、ログイン画面にリダイレクトされなかった

解決策:

Spring Securityで401を返却

フロントエンドで401を検知して /login へリダイレクト

フィルターで直接 response.sendRedirect() を実行

</details> <details> <summary>English</summary>
Cause:

On JWT expiration, only JSON error returned, no redirect to login

Solution:

Configured Spring Security to return 401

Frontend detects 401 and redirects to /login

Optionally handle redirect directly in filter

</details>
5. JAR 실행 오류 (EC2)
Error: Unable to access jarfile build/libs/sorbet-0.0.1-SNAPSHOT.jar
원인:

Gradle 빌드 결과물이 존재하지 않음

경로 불일치

해결:

./gradlew clean build
scp build/libs/sorbet-0.0.1-SNAPSHOT.jar ec2-user@<EC2_IP>:/home/ec2-user/sorbet/
nohup java -jar sorbet-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
<details> <summary>日本語 (Japanese)</summary>
原因:

Gradleビルド成果物が存在しなかった

パスの不一致

解決策:

ローカルで ./gradlew build を実行

EC2にアップロード後、nohup java -jar で実行

</details> <details> <summary>English</summary>
Cause:

Missing Gradle build artifact

Incorrect path

Solution:

Ran ./gradlew build locally

Uploaded to EC2 and executed with nohup java -jar

</details>



