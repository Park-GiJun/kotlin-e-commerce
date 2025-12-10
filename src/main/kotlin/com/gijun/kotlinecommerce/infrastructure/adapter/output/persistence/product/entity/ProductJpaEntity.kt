package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity

import com.gijun.kotlinecommerce.domain.product.model.ProductModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "product")
class ProductJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "product_category_id", nullable = false)
    var productCategoryId: Long = 0L,

    @Column(name = "product_name", nullable = false, length = 200)
    var productName: String = ""
) : BaseEntity() {

    fun toDomainModel(): ProductModel {
        return ProductModel(
            id = this.id,
            categoryId = this.productCategoryId,
            name = this.productName
        )
    }

    companion object {
        fun fromDomain(model: ProductModel): ProductJpaEntity {
            return ProductJpaEntity(
                id = model.id,
                productCategoryId = model.categoryId,
                productName = model.name
            )
        }
    }
}