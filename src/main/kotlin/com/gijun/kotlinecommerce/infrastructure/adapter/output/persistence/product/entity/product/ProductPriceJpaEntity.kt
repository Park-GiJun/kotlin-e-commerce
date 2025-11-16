package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.product

import com.gijun.kotlinecommerce.domain.product.model.ProductPriceModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "product_price")
class ProductPriceJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val productId: Long = 0,
    val price: Double = 0.0,
    val startDate: String = "",
    val endDate: String = ""
) : BaseEntity() {
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
        fun fromDomain(model: ProductPriceModel): ProductPriceJpaEntity {
            return ProductPriceJpaEntity(
                id = model.id ?: 0,
                productId = model.productId,
                price = model.price.toDouble(),
                startDate = model.startDate.toString(),
                endDate = model.endDate.toString()
            )
        }
    }
}