# MintShop - Kotlin E-Commerce

Kotlin과 Spring Boot 기반의 이커머스 플랫폼입니다.

> **Frontend Note**: 프론트엔드는 [Claude Code](https://claude.ai/claude-code)를 활용하여 개발되었습니다.

## Tech Stack

### Backend
| Category | Technology |
|----------|------------|
| Language | Kotlin 1.9 |
| Framework | Spring Boot 3.5 |
| Database | MySQL 8.0 |
| ORM | Spring Data JPA + Kotlin JDSL 3.5 |
| Cache & Lock | Redis 7 (Redisson) |
| Message Queue | Apache Kafka |
| Security | Spring Security + JWT |
| API Docs | SpringDoc OpenAPI (Swagger) |

### Infrastructure
| Category | Technology |
|----------|------------|
| Container | Docker & Docker Compose |
| Database | MySQL 8.0 |
| Cache | Redis 7 Alpine |
| Message Broker | Apache Kafka + Zookeeper |

## Architecture

### Hexagonal Architecture (Ports & Adapters)

클린 아키텍처 원칙을 기반으로 한 헥사고날 아키텍처를 적용했습니다.

```
┌─────────────────────────────────────────────────────────────────┐
│                        Infrastructure                            │
│  ┌──────────────────┐                    ┌──────────────────┐   │
│  │   Input Adapters │                    │  Output Adapters │   │
│  │  (REST, GraphQL) │                    │   (JPA, Redis)   │   │
│  └────────┬─────────┘                    └────────┬─────────┘   │
│           │                                       │              │
│  ┌────────▼─────────┐                    ┌────────▼─────────┐   │
│  │   Input Ports    │                    │   Output Ports   │   │
│  │   (Use Cases)    │                    │  (Repositories)  │   │
│  └────────┬─────────┘                    └────────┬─────────┘   │
│           │                                       │              │
│           │         ┌─────────────────┐          │              │
│           └────────►│   Application   │◄─────────┘              │
│                     │   (Handlers)    │                         │
│                     └────────┬────────┘                         │
│                              │                                  │
│                     ┌────────▼────────┐                         │
│                     │     Domain      │                         │
│                     │    (Models)     │                         │
│                     └─────────────────┘                         │
└─────────────────────────────────────────────────────────────────┘
```

### Layer 구조

```
├── domain/                    # 도메인 계층 (순수 비즈니스 로직)
│   ├── cart/model/
│   ├── product/model/
│   └── user/model/
│
├── application/               # 애플리케이션 계층
│   ├── dto/                   # Command & Query DTOs
│   │   ├── command/           # 명령 객체
│   │   └── query/             # 조회 객체
│   ├── handler/               # 비즈니스 로직 핸들러
│   └── port/                  # 포트 인터페이스
│       ├── input/             # 인바운드 포트 (Use Cases)
│       └── output/            # 아웃바운드 포트 (Repositories)
│
└── infrastructure/            # 인프라 계층
    ├── adapter/
    │   ├── input/rest/        # REST API 컨트롤러
    │   └── output/persistence/# JPA Repository 구현체
    ├── config/                # Spring 설정
    └── security/              # JWT & Security
```

## Key Features

### 분산 락 (Distributed Lock)
Redis 기반 Redisson을 사용한 분산 락 구현으로 동시성 제어

```kotlin
@DistributedLock(key = "'product:' + #productId")
fun updateProductStock(productId: Long, quantity: Int)
```

### 가격 캐싱 (Price Caching)
Redis를 활용한 상품 가격 캐싱으로 조회 성능 최적화

### 계층형 카테고리
3단계 계층 구조의 상품 카테고리 관리 (대/중/소분류)

### JWT 인증
Stateless JWT 기반 인증 시스템

## API Endpoints

### Public (인증 불필요)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users/register` | 회원가입 |
| POST | `/api/users/login` | 로그인 |
| GET | `/api/products/**` | 상품 조회 |
| GET | `/api/product-categories/**` | 카테고리 조회 |
| GET | `/api/product-prices/**` | 가격 조회 |

### Authenticated (로그인 필요)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET/POST | `/api/carts/**` | 장바구니 관리 |

### Admin Only (관리자)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST/PUT/DELETE | `/api/products/**` | 상품 CUD |
| POST/PUT/DELETE | `/api/product-categories/**` | 카테고리 CUD |
| POST/PUT/DELETE | `/api/product-prices/**` | 가격 CUD |

## Getting Started

### Prerequisites
- JDK 17+
- Docker & Docker Compose

### 1. Infrastructure
```bash
cd docker-ecommerce
docker-compose -f docker-compose.dev.yml up -d
```

### 2. Run Application
```bash
./gradlew bootRun
```

### 3. API Documentation
```
http://localhost:9832/swagger-ui.html
```

## Dependencies

```kotlin
// Spring Boot
implementation("org.springframework.boot:spring-boot-starter-web")
implementation("org.springframework.boot:spring-boot-starter-data-jpa")
implementation("org.springframework.boot:spring-boot-starter-security")

// Kotlin JDSL (Type-safe Query)
implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.5.3")
implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:3.5.3")

// Redis (Distributed Lock & Cache)
implementation("org.redisson:redisson:3.52.0")

// JWT
implementation("io.jsonwebtoken:jjwt-api:0.12.5")

// Swagger
implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.14")
```

---

# GIT Convention

## 커밋 메시지 규칙

### 커밋 메시지 구조
```
<타입>: <제목>

<본문 (선택사항)>

<꼬리말 (선택사항)>
```

### 커밋 타입
- **feat**: 새로운 기능 추가
- **fix**: 버그 수정
- **docs**: 문서 수정
- **style**: 코드 포맷팅, 세미콜론 누락 등 코드 변경이 없는 경우
- **refactor**: 코드 리팩토링
- **test**: 테스트 코드 추가 또는 수정
- **chore**: 빌드 업무 수정, 패키지 매니저 수정 등
- **perf**: 성능 개선
- **ci**: CI 설정 파일 수정
- **build**: 빌드 시스템 또는 외부 종속성에 영향을 미치는 변경

### 제목 작성 규칙
- 제목은 50자를 넘지 않도록 작성
- 제목 첫 글자는 대문자로 작성
- 제목 끝에 마침표를 붙이지 않음
- 제목은 명령문으로 작성 (과거형 사용 금지)
- 제목과 본문 사이에 한 줄 띄우기

### 본문 작성 규칙
- 본문은 한 줄당 72자 이내로 작성
- 본문은 "무엇을" 변경했는지보다 "왜" 변경했는지 작성
- 여러 줄로 작성 가능하며 "-"로 구분하여 나열

### 커밋 메시지 예시
```
feat: 사용자 로그인 기능 추가

- JWT 토큰 기반 인증 구현
- 로그인 API 엔드포인트 추가
- 사용자 세션 관리 기능 구현

Resolves: #123
```

```
fix: 상품 검색 시 null 참조 오류 수정

상품 카테고리가 없는 경우 발생하던 NullPointerException 처리

Closes: #456
```

## 브랜치 전략

### 브랜치 명명 규칙
```
<타입>/<이슈번호>-<간단한-설명>
```

### 브랜치 타입
- **feature**: 새로운 기능 개발
- **fix**: 버그 수정
- **hotfix**: 긴급 버그 수정
- **refactor**: 코드 리팩토링
- **docs**: 문서 작업
- **test**: 테스트 코드 작업

### 브랜치 예시
```
feature/123-user-authentication
fix/456-product-search-bug
hotfix/789-payment-error
refactor/321-clean-product-service
docs/654-update-api-documentation
```

## 작업 흐름

1. 이슈 생성 및 확인
2. 작업 브랜치 생성 (main/develop 브랜치에서 분기)
3. 기능 개발 및 커밋
4. 원격 저장소에 푸시
5. Pull Request 생성
6. 코드 리뷰
7. 승인 후 메인 브랜치에 병합
8. 작업 브랜치 삭제

## Pull Request 규칙

### PR 제목
- 커밋 메시지와 동일한 형식 사용
- 명확하고 간결하게 작성

### PR 본문
- 변경 사항 요약
- 변경 이유 및 배경
- 테스트 방법
- 스크린샷 (UI 변경 시)
- 관련 이슈 번호

### PR 예시
```markdown
## 변경 사항
- 사용자 로그인 기능 구현
- JWT 토큰 기반 인증 추가

## 변경 이유
사용자 인증 시스템이 필요하여 JWT 기반 로그인 기능을 추가했습니다.

## 테스트 방법
1. POST /api/auth/login 엔드포인트로 사용자 정보 전송
2. 응답으로 JWT 토큰 확인
3. 토큰을 사용하여 보호된 API 접근 테스트

## 관련 이슈
Resolves: #123
```

## 주의사항

- 커밋은 가능한 작은 단위로 자주 수행
- 하나의 커밋에는 하나의 논리적 변경사항만 포함
- 작업 중인 코드가 빌드 가능한 상태에서 커밋
- 의미 없는 커밋 메시지 사용 금지 (예: "수정", "테스트")
- 커밋 전 코드 리뷰 및 테스트 수행
- main 브랜치에 직접 커밋 금지

---

**Built with Kotlin + Spring Boot**

Frontend developed with [Claude Code](https://claude.ai/claude-code)
