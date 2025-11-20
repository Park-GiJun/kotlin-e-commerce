package com.gijun.kotlinecommerce.domain.product.model

import java.math.BigDecimal
import java.time.LocalDate

data class ProductPriceModel(
    val id: Long? = null,
    val productId: Long,
    val price: BigDecimal,
    val startDate: LocalDate? = LocalDate.now(),
    val endDate: LocalDate? = LocalDate.of(9999, 12 ,31)
) {
    fun isCurrentlyValid(): Boolean {
        val today = LocalDate.now()
        return !today.isBefore(startDate) && !today.isAfter(endDate)
    }

    companion object {
        fun create(productId: Long, price: BigDecimal, startDate: LocalDate, endDate: LocalDate): ProductPriceModel {
            return ProductPriceModel(
                id = null,
                productId = productId,
                price = price,
                startDate = startDate,
                endDate = endDate
            )
        }

        fun of(id: Long, productId: Long, price: BigDecimal, startDate: LocalDate, endDate: LocalDate): ProductPriceModel {
            return ProductPriceModel(
                id = id,
                productId = productId,
                price = price,
                startDate = startDate,
                endDate = endDate
            )
        }
    }
}
