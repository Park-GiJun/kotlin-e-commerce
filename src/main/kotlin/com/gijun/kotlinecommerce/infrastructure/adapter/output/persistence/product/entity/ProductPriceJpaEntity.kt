package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity

import com.gijun.kotlinecommerce.domain.product.model.ProductPriceModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "product_price", uniqueConstraints = [UniqueConstraint(columnNames = ["productId", "startDate"])])
class ProductPriceJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val productId: Long,

    @Column(nullable = false)
    val price: Double,

    @Column(nullable = false)
    val startDate: LocalDate,

    @Column(nullable = false)
    val endDate: LocalDate
) : BaseEntity() {
    fun toDomain(): ProductPriceModel {
        return ProductPriceModel(
            id = id,
            productId = productId,
            price = BigDecimal.valueOf(price),
            startDate = startDate,
            endDate = endDate
        )
    }

    companion object {
        fun fromDomain(model: ProductPriceModel): ProductPriceJpaEntity {
            return ProductPriceJpaEntity(
                id = model.id,
                productId = model.productId,
                price = model.price.toDouble(),
                startDate = model.startDate,
                endDate = model.endDate
            )
        }
    }
}