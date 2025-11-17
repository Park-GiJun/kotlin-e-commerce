package com.gijun.kotlinecommerce.infrastructure.adapter.output.cache.product.repository

import com.gijun.kotlinecommerce.infrastructure.adapter.output.cache.product.entity.ProductPriceCacheEntity
import com.gijun.kotlinecommerce.infrastructure.adapter.output.cache.product.util.ProductPriceCacheConstants
import com.gijun.kotlinecommerce.infrastructure.adapter.output.cache.product.util.ProductPriceCacheKeyGenerator
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class ProductPriceCacheRepository(
    private val redissonClient: RedissonClient,
    private val productPriceCacheKeyGenerator: ProductPriceCacheKeyGenerator
) {

    fun findByProductId(productId: Long): ProductPriceCacheEntity? {
        val key = productPriceCacheKeyGenerator.generateProductPriceKey(productId)
        val bucket = redissonClient.getBucket<ProductPriceCacheEntity>(key)
        return bucket.get()
    }

    fun findByProductIds(productIds: List<Long>): Map<Long, ProductPriceCacheEntity> {
        if (productIds.isEmpty()) return emptyMap()

        val keys = productIds.map { productPriceCacheKeyGenerator.generateProductPriceKey(it) }
        val buckets = redissonClient.buckets
        val result = buckets.get<ProductPriceCacheEntity>(*keys.toTypedArray())

        return result
            .filterValues { it != null }
            .mapKeys { entry ->
                entry.key.substringAfterLast(":").toLong()
            }
    }

    fun save(
        productId: Long,
        entity: ProductPriceCacheEntity,
        ttlSeconds: Long = ProductPriceCacheConstants.PRODUCT_PRICE_TTL
    ): ProductPriceCacheEntity {
        val key = productPriceCacheKeyGenerator.generateProductPriceKey(productId)
        val bucket = redissonClient.getBucket<ProductPriceCacheEntity>(key)
        bucket.set(entity, ttlSeconds, TimeUnit.SECONDS)
        return entity
    }

    fun deleteByProductId(productId: Long): Boolean {
        val key = productPriceCacheKeyGenerator.generateProductPriceKey(productId)
        val bucket = redissonClient.getBucket<ProductPriceCacheEntity>(key)
        return bucket.delete()
    }

    /**
     * 캐시 존재 여부 확인
     */
    fun exists(productId: Long): Boolean {
        val key = productPriceCacheKeyGenerator.generateProductPriceKey(productId)
        val bucket = redissonClient.getBucket<ProductPriceCacheEntity>(key)
        return bucket.isExists
    }
}