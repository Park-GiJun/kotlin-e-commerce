# Docker E-Commerce Infrastructure

## Services
- MySQL 8.0
- Redis 7
- Kafka 7.5.0 with Zookeeper

## Usage

### Development
```bash
docker-compose -f docker-compose.dev.yml --env-file .env.dev up -d
```

### Production
```bash
docker-compose -f docker-compose.prd.yml --env-file .env.prd up -d
```

### Stop Services
```bash
docker-compose -f docker-compose.dev.yml down
docker-compose -f docker-compose.prd.yml down
```

### View Logs
```bash
docker-compose -f docker-compose.dev.yml logs -f
docker-compose -f docker-compose.prd.yml logs -f
```

## Ports
- MySQL: 3306
- Redis: 6379
- Kafka: 9092
- Zookeeper: 2181

## Connection Strings

### Development
- MySQL: `jdbc:mysql://localhost:3306/ecommerce_dev`
- Redis: `redis://localhost:6379`
- Kafka: `localhost:9092`

### Production
- MySQL: `jdbc:mysql://localhost:3306/ecommerce_prd`
- Redis: `redis://localhost:6379` (password required)
- Kafka: `localhost:9092`
