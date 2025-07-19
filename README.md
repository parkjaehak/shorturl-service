# Short URL Service

URL 단축 서비스입니다. Map 기반과 JPA 기반 저장소를 `@Qualifier`를 사용하여 유연하게 교체할 수 있습니다.

## 저장소 전략

### 1. Map 기반 저장소 (메모리)
- 빠른 접근 속도
- 애플리케이션 재시작 시 데이터 손실
- 개발/테스트 환경에 적합
- Qualifier: `mapRepository`

### 2. JPA 기반 저장소 (데이터베이스)
- 영구 데이터 저장
- MySQL 데이터베이스 사용
- 프로덕션 환경에 적합
- Qualifier: `jpaRepository`

## 저장소 변경 방법

### 1. 코드에서 직접 변경
`SimpleShortenUrlService` 클래스의 생성자에서 `@Qualifier` 값을 변경:

```java
// JPA 저장소 사용
SimpleShortenUrlService(@Qualifier("jpaRepository") ShortenUrlRepositoryInterface shortenUrlRepository)

// Map 저장소 사용
SimpleShortenUrlService(@Qualifier("mapRepository") ShortenUrlRepositoryInterface shortenUrlRepository)
```

### 2. 설정 클래스에서 변경
`RepositoryConfig` 클래스의 `defaultRepository` 메서드에서 반환하는 Bean을 변경:

```java
@Bean
@Primary
@Qualifier("defaultRepository")
public ShortenUrlRepositoryInterface defaultRepository(
        @Qualifier("mapRepository") ShortenUrlRepositoryInterface mapRepository) { // jpaRepository → mapRepository
    return mapRepository;
}
```

## 실행 방법

```bash
# 기본 실행 (현재 JPA 저장소로 설정됨)
java -jar shorturl-service.jar
```

## API 문서

애플리케이션 실행 후 다음 URL로 접속:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API 문서**: `http://localhost:8080/api-docs`

## API 엔드포인트

### 1. 단축 URL 생성
```
POST /shortenUrl
Content-Type: application/json

{
  "originalUrl": "https://www.google.com"
}
```

### 2. 단축 URL 리다이렉트
```
GET /{shortenUrlKey}
```

### 3. 단축 URL 정보 조회
```
GET /shortenUrl/{shortenUrlKey}
```

### 4. 헬스 체크
```
GET /health
```

## 데이터베이스 설정 (JPA 프로필 사용 시)

MySQL 데이터베이스가 필요합니다:

```sql
CREATE DATABASE shortcut_db;
```

데이터베이스 연결 정보는 `application-jpa.properties`에서 수정할 수 있습니다. 