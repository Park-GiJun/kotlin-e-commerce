package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.cart.entity

import com.gijun.kotlinecommerce.domain.cart.model.CartModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "cart")
class CartJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "product_id", nullable = false)
    val productId: Long,

    @Column(name = "quantity", nullable = false)
    val quantity: Int

) : BaseEntity() {
    fun toDomain(): CartModel {
        return CartModel(
            id = id,
            userId = userId,
            productId = productId,
            quantity = quantity
        )
    }

    companion object {
        fun fromDomain(model: CartModel): CartJpaEntity {
            return CartJpaEntity(
                id = model.id,
                userId = model.userId,
                productId = model.productId,
                quantity = model.quantity
            )
        }
    }
}