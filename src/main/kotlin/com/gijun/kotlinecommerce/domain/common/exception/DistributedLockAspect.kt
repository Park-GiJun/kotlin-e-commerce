package com.gijun.kotlinecommerce.domain.common.exception

import com.gijun.kotlinecommerce.application.annotation.DistributeLock
import com.gijun.kotlinecommerce.infrastructure.aop.DistributedLockException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

/**
 * 분산 락을 처리하는 AOP Aspect
 *
 * @Transactional보다 먼저 실행되어야 하므로 Order를 낮게 설정
 * Lock 획득 -> 트랜잭션 시작 -> 비즈니스 로직 -> 트랜잭션 커밋 -> Lock 해제
 */
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
class DistributedLockAspect(
    private val redissonClient: RedissonClient
) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val spelParser = SpelExpressionParser()

    @Around("@annotation(distributeLock)")
    fun around(joinPoint: ProceedingJoinPoint, distributeLock: DistributeLock): Any? {
        val lockType = distributeLock.type
        val lockKey = parseKey(distributeLock.key, joinPoint)
        val fullLockKey = lockType.generateKey(lockKey)

        val waitTime = if (distributeLock.waitTime >= 0) distributeLock.waitTime else lockType.waitTime
        val leaseTime = if (distributeLock.leaseTime >= 0) distributeLock.leaseTime else lockType.leaseTime

        val lock = redissonClient.getLock(fullLockKey)

        log.debug("분산 락 획득 시도: key={}, waitTime={}s, leaseTime={}s", fullLockKey, waitTime, leaseTime)

        val acquired = try {
            lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            throw DistributedLockException("락 획득 중 인터럽트 발생: $fullLockKey", e)
        }

        if (!acquired) {
            log.warn("분산 락 획득 실패: key={}", fullLockKey)
            throw DistributedLockException("락 획득에 실패했습니다. 잠시 후 다시 시도해주세요: $fullLockKey")
        }

        log.debug("분산 락 획득 성공: key={}", fullLockKey)

        return try {
            joinPoint.proceed()
        } finally {
            try {
                if (lock.isHeldByCurrentThread) {
                    lock.unlock()
                    log.debug("분산 락 해제: key={}", fullLockKey)
                }
            } catch (e: Exception) {
                log.warn("분산 락 해제 실패: key={}, error={}", fullLockKey, e.message)
            }
        }
    }

    /**
     * SpEL 표현식을 파싱하여 실제 키 값을 생성
     * 예: "#command.productId" -> command 파라미터의 productId 값
     */
    private fun parseKey(keyExpression: String, joinPoint: ProceedingJoinPoint): String {
        if (!keyExpression.contains("#")) {
            return keyExpression
        }

        val signature = joinPoint.signature as MethodSignature
        val parameterNames = signature.parameterNames
        val args = joinPoint.args

        val context = StandardEvaluationContext()
        parameterNames.forEachIndexed { index, name ->
            context.setVariable(name, args[index])
        }

        return try {
            spelParser.parseExpression(keyExpression).getValue(context, String::class.java)
                ?: throw DistributedLockException("락 키 파싱 실패: $keyExpression")
        } catch (e: Exception) {
            throw DistributedLockException("락 키 파싱 중 오류 발생: $keyExpression", e)
        }
    }
}