# 클라우드 기반 도커 컨테이너 배포 자동화 서비스
- 클라우드를 이용하지 않아도 사용자의 애플리케이션을 실제 웹 서버에 배포해주는 시스템
- 간단한 웹애플리케이션을 배포하고자 할 때 유용한 시스템 제공 목적


## Services API Docs

### User Service
| Request     | HTTP Method | URI                 |
|-------------|-------------|---------------------|
| 회원가입        | POST        | /api/users          |
| 회원 개인 조회    | GET         | /api/users/{userId} |
| 회원 정보 수정    | PUT         | /api/users/{userId} |
| 회원 비밀번호 초기화 | PATCH       | /api/users          |
| 회원 탈퇴       | DELETE      | /api/users/{userId} |
| 로그인         | POST        | /api/login          |
| 이메일 인증번호 받기 | POST        | /api/mails          |
| 인증번호 인증     | PUT         | /api/mails          |

### Database Service
| Request               | HTTP Method | URI                     |
|-----------------------|-------------|-------------------------|
| 데이터베이스 유저 및 데이터베이스 생성 | POST        | /api/databases          |
| 데이터베이스 정보 조회          | GET         | /api/databases/{userId} |
| 데이터베이스 유저 및 데이터베이스 삭제 | DELETE      | /api/databases/{userId} |

### AppOrder Service
| Request               | HTTP Method | URI                         |
|-----------------------|-------------|-----------------------------|
| 유저 애플리케이션 정보 등록       | POST        | /api/apporders              |
| 유저 애플리케이션 단건 정보 조회    | GET         | /api/apporders/{apporderId} |
| 유저 애플리케이션 등록 정보 전체 조회 | GET         | /api/apporders/all          |
| 유저 애플리케이션 삭제          | DELETE      | /api/apporders/{apporderId} |

### Storage Service
| Request          | HTTP Method | URI                        |
|------------------|-------------|----------------------------|
| 유저 애플리케이션 파일 업로드 | POST        | /api/storages/{appOrderId} |
| 유저 애플리케이션 파일 삭제  | DELETE      | /api/storages/{appOrderId} |

### Container Service
| Request                  | HTTP Method | URI                                 |
|--------------------------|-------------|-------------------------------------|
| 도커 속 유저 애플리케이션 서비스 상태 조회 | GET         | /api/containers/{containerId}       |
| 도커 속 유저 애플리케이션 서비스 로그 조회 | GET         | /api/containers/logs/{containerId}  |
| 도커 속 유저 애플리케이션 서비스 정지    | GET         | /api/containers/stop/{containerId}  |
| 도커 속 유저 애플리케이션 서비스 시작    | GET         | /api/containers/start/{containerId} |

## 사용 Point

#### 1. 유저 생성

```java
public class UserCreateCommand {
    private String email;
    private String username;
    private String password;
}
```
#### 2. 유저 이메일 인증번호 발급
```java
public class EmailCodeGenerationCommand {
    private String email;
}
```
#### 3. 유저 이메일 인증번호 인증
```java
public class EmailCodeVerificationCommand {
    private final String email;
    private final Integer emailCode;
}
```

#### 4. 데이터베이스 유저 및 데이터베이스 생성
```java
public class DatabaseCreateCommand {
    private UUID userId;
    private final String databaseName;
    private final String databaseUsername;
    private final String databasePassword;
}
```

#### 5. 웹애플리케이션 정보 등록
```java
public class AppOrderCreateCommand {
    private UUID userId;
    private final String applicationName;
    private final Integer serverPort;
    private final Integer javaVersion;
}
```
#### 6. 웹애플리케이션 파일 업로드
```java
MultipartFile file;
```

#### 7. 웹애플리케이션 업로드 최종 상태 및 엔드포인트 확인
```java
public class TrackAppOrderResponse {
    private final String userId;
    private final String endPoint;
    private final String applicationName;
    private final Integer serverPort;
    private final Integer javaVersion;
    private final String error;
    private final String containerId;
    private final String appOrderStatus;
}
```
