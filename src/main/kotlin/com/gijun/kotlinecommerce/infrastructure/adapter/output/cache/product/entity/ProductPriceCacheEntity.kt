package com.gijun.kotlinecommerce.infrastructure.adapter.output.cache.product.entity

import com.gijun.kotlinecommerce.domain.product.model.ProductPriceModel
import java.math.BigDecimal
import java.time.LocalDate

data class ProductPriceCacheEntity(
    val id: Long?,
    val productId: Long,
    val price: Double,
    val startDate: String,
    val endDate: String
) {

    fun toDomain(): ProductPriceModel {
        return ProductPriceModel(
            id = id,
            productId = productId,
            price = BigDecimal.valueOf(price),
            startDate = LocalDate.parse(startDate),
            endDate = LocalDate.parse(endDate)
        )
    }

    companion object {
        fun fromDomain(model: ProductPriceModel): ProductPriceCacheEntity {
            return ProductPriceCacheEntity(
                id = model.id,
                productId = model.productId,
                price = model.price.toDouble(),
                startDate = model.startDate.toString(),
                endDate = model.endDate.toString()
            )
        }
    }
}