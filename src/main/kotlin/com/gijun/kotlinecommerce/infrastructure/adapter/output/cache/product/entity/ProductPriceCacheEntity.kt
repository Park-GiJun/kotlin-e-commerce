package com.gijun.kotlinecommerce.infrastructure.adapter.output.cache.product.entity

import com.gijun.kotlinecommerce.domain.product.model.ProductPriceModel
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDate

data class ProductPriceCacheEntity(
    val id: Long?,
    val productId: Long,
    val price: Double,
    val startDate: String,
    val endDate: String
) : Serializable {

    fun toDomainModel(): ProductPriceModel {
        return ProductPriceModel(
            id = this.id,
            productId = this.productId,
            price = BigDecimal.valueOf(this.price),
            startDate = LocalDate.parse(this.startDate),
            endDate = LocalDate.parse(this.endDate)
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