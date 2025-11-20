package com.gijun.kotlinecommerce.application.port.output.persistence.product

import com.gijun.kotlinecommerce.domain.product.model.ProductPriceModel

interface ProductPriceJpaPort {
    fun findCurrentPricesByProductIds(productIds: List<Long>): List<ProductPriceModel>
    fun findAllCurrentPrices(): List<ProductPriceModel>
    fun findByProductId(productId: Long): List<ProductPriceModel>
    fun save(productPriceModel: ProductPriceModel): ProductPriceModel
    fun findById(id: Long): ProductPriceModel?
    fun update(productPriceModel: ProductPriceModel): ProductPriceModel
    fun delete(productPriceModel: ProductPriceModel): ProductPriceModel
}