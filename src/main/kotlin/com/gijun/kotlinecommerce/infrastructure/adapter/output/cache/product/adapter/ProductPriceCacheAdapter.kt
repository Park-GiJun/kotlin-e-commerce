package com.gijun.kotlinecommerce.infrastructure.adapter.output.cache.product.adapter

import com.gijun.kotlinecommerce.application.port.output.cache.ProductPriceCachePort
import com.gijun.kotlinecommerce.domain.product.model.ProductPriceModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.cache.product.entity.ProductPriceCacheEntity
import com.gijun.kotlinecommerce.infrastructure.adapter.output.cache.product.repository.ProductPriceCacheRepository
import org.springframework.stereotype.Component

@Component
class ProductPriceCacheAdapter(
    private val productPriceCacheRepository: ProductPriceCacheRepository
) : ProductPriceCachePort {

    override fun findCurrentPriceByProductId(productId: Long): ProductPriceModel? {
        return productPriceCacheRepository
            .findByProductId(productId)
            ?.toDomainModel()
    }

    override fun findCurrentPricesByProductIds(productIds: List<Long>): Map<Long, ProductPriceModel> {
        return productPriceCacheRepository
            .findByProductIds(productIds)
            .mapValues { it.value.toDomainModel() }
    }

    override fun save(productPriceModel: ProductPriceModel): ProductPriceModel {
        val entity = ProductPriceCacheEntity.fromDomain(productPriceModel)
        productPriceCacheRepository.save(productId = productPriceModel.productId, entity = entity)
        return productPriceModel
    }

    override fun deleteByProductId(productId: Long): Boolean {
        return productPriceCacheRepository.deleteByProductId(productId)
    }
}