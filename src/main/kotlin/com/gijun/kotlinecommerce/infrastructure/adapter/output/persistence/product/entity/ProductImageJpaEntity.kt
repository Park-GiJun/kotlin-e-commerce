package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity

import com.gijun.kotlinecommerce.domain.product.model.ProductImageModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "product_image")
class ProductImageJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "product_id", nullable = false)
    var productId: Long,

    @Column(name = "image_order", nullable = false)
    var imageOrder: Int = 0,

    @Column(name = "is_main", nullable = false)
    var isMain: Boolean = false,

    @Column(name = "image_url", nullable = false)
    var imageUrl: String = ""
) : BaseEntity() {

    fun toDomainModel(): ProductImageModel {
        return ProductImageModel(
            id = id,
            productId = productId,
            imageOrder = imageOrder,
            isMain = isMain,
            imageUrl = imageUrl
        )
    }

    companion object {
        fun fromDomain(model: ProductImageModel): ProductImageJpaEntity {
            return ProductImageJpaEntity(
                id = model.id,
                productId = model.productId,
                imageOrder = model.imageOrder,
                isMain = model.isMain,
                imageUrl = model.imageUrl
            )
        }
    }

}