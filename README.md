# MintShop - Kotlin E-Commerce

Kotlin과 Spring Boot 기반의 이커머스 플랫폼입니다.

> **Frontend**: 프론트엔드는 [Claude Code](https://claude.ai/claude-code)를 활용하여 개발되었습니다.

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
│                        Infrastructure                           │
│  ┌──────────────────┐                    ┌──────────────────┐   │
│  │   Input Adapters │                    │  Output Adapters │   │
│  │ (REST, Scheduler)│                    │   (JPA, Redis)   │   │
│  └────────┬─────────┘                    └────────┬─────────┘   │
│           │                                       │             │
│  ┌────────▼─────────┐                    ┌────────▼─────────┐   │
│  │   Input Ports    │                    │   Output Ports   │   │
│  │   (Use Cases)    │                    │  (Repositories)  │   │
│  └────────┬─────────┘                    └────────┬─────────┘   │
│           │                                       │             │
│           │         ┌─────────────────┐           │             │
│           └────────►│   Application   │◄──────────┘             │
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

### 상품 리뷰 시스템
- 상품별 리뷰 작성/조회
- 평균 평점 및 리뷰 수 집계
- 중복 리뷰 방지

### 재고 관리
- 상품별 재고 수량 관리
- 품절 상태 자동 표시

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
| POST | `/api/reviews/products/{id}` | 상품 리뷰 작성 |

### Admin Only (관리자)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST/PUT/DELETE | `/api/products/**` | 상품 CUD |
| POST/PUT/DELETE | `/api/product-categories/**` | 카테고리 CUD |
| POST/PUT/DELETE | `/api/product-prices/**` | 가격 CUD |
| POST/PUT/DELETE | `/api/product-stocks/**` | 재고 관리 |

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

**Built with Kotlin + Spring Boot**

Frontend developed with [Claude Code](https://claude.ai/claude-code)
