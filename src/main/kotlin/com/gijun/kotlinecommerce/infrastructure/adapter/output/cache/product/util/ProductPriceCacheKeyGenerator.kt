package com.gijun.kotlinecommerce.infrastructure.adapter.output.cache.product.util

import org.springframework.stereotype.Component

@Component
class ProductPriceCacheKeyGenerator {

    /**
     * 상품 현재 가격 캐시 키 생성
     * Format: product:price:{productId}
     */
    fun generateProductPriceKey(productId: Long): String {
        return "${ProductPriceCacheConstants.PRODUCT_PRICE_PREFIX}:$productId"
    }

    /**
     * 특정 상품의 가격 캐시 삭제용 패턴
     * Format: product:price:{productId}
     */
    fun generateProductPricePattern(productId: Long): String {
        return "${ProductPriceCacheConstants.PRODUCT_PRICE_PREFIX}:$productId"
    }

    /**
     * 분산 락 키 생성
     * Format: lock:product:price:{productId}
     */
    fun generateLockKey(productId: Long): String {
        return "lock:${ProductPriceCacheConstants.PRODUCT_PRICE_PREFIX}:$productId"
    }
}