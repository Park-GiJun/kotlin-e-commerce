package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity

import com.gijun.kotlinecommerce.domain.product.model.ProductStockModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "product_stock")
class ProductStockJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "product_id", nullable = false)
    var productId: Long,

    @Column(name = "quantity", nullable = false)
    var quantity: Int
) : BaseEntity() {
    fun toDomain(): ProductStockModel {
        return ProductStockModel(
            id = id,
            productId = productId,
            quantity = quantity
        )
    }

    fun update(quantity: Int) : ProductStockJpaEntity {
        this.quantity = quantity
        return this
    }

    companion object {
        fun fromDomain(model : ProductStockModel) : ProductStockJpaEntity {
            return ProductStockJpaEntity(
                id = model.id,
                productId = model.productId,
                quantity = model.quantity
            )
        }
    }
}