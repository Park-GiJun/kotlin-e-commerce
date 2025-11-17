package com.gijun.kotlinecommerce.infrastructure.adapter.output.cache.product.util

object ProductPriceCacheConstants {
    // Cache key prefix
    const val PRODUCT_PRICE_PREFIX = "product:price"

    // Cache TTL (Time To Live) in seconds
    const val PRODUCT_PRICE_TTL = 3600L // 1 hour

    // Lock settings
    const val LOCK_WAIT_TIME = 3L // seconds
    const val LOCK_LEASE_TIME = 5L // seconds
}