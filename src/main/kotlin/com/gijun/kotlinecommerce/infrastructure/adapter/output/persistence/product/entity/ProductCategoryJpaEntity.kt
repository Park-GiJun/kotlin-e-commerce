package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity

import com.gijun.kotlinecommerce.domain.product.model.ProductCategoryModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "product_category")
class ProductCategoryJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "parent_id", nullable = false)
    var parentId: Long = 0L,

    @Column(name = "category_name", nullable = false, length = 100)
    var categoryName: String = ""
) : BaseEntity() {

    fun toDomainModel(): ProductCategoryModel {
        return ProductCategoryModel(
            id = this.id,
            parentId = this.parentId,
            name = this.categoryName
        )
    }

    companion object {
        fun fromDomain(model: ProductCategoryModel): ProductCategoryJpaEntity {
            return ProductCategoryJpaEntity(
                id = model.id,
                parentId = model.parentId,
                categoryName = model.name
            )
        }
    }
}