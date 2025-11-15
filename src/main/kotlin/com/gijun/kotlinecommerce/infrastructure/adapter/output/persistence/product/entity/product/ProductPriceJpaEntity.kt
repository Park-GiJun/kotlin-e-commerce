package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.product

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "product_price")
class ProductPriceJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val productId: Long,
    val price: Double,
    val startDate: String,
    val endDate: String
) {
}