package com.gijun.kotlinecommerce.domain.lock.model

enum class DistributedLockType(
    val prefix: String,
    val waitTime: Long = 5L,
    val leaseTime: Long = 10L
) {
    // 재고 관련 락 - 동시성 제어가 중요하므로 짧은 대기시간
    STOCK(prefix = "lock:stock:", waitTime = 3L, leaseTime = 5L),

    // 가격 관련 락
    PRICE(prefix = "lock:price:", waitTime = 5L, leaseTime = 10L),

    // 장바구니 관련 락
    CART(prefix = "lock:cart:", waitTime = 3L, leaseTime = 5L),

    // 사용자 관련 락
    USER(prefix = "lock:user:", waitTime = 5L, leaseTime = 10L),

    // 상품 관련 락
    PRODUCT(prefix = "lock:product:", waitTime = 5L, leaseTime = 10L),

    // 카테고리 관련 락
    CATEGORY(prefix = "lock:category:", waitTime = 5L, leaseTime = 10L),

    // 리뷰 관련 락
    REVIEW(prefix = "lock:review:", waitTime = 3L, leaseTime = 5L);

    fun generateKey(key: String): String = "$prefix$key"
}