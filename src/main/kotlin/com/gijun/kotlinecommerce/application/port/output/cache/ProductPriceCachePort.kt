package com.gijun.kotlinecommerce.application.port.output.cache

import com.gijun.kotlinecommerce.domain.product.model.ProductPriceModel

interface ProductPriceCachePort {
    fun findCurrentPriceByProductId(productId: Long): ProductPriceModel?

    fun findCurrentPricesByProductIds(productIds: List<Long>): Map<Long, ProductPriceModel>

    fun save(productPriceModel: ProductPriceModel): ProductPriceModel

    fun deleteByProductId(productId: Long): Boolean
}