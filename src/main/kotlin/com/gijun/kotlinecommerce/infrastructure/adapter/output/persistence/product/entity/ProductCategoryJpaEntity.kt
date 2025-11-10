package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity

import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.common.BaseEntity

class ProductCategoryJpaEntity(
    val id: Long,
    val parentId: Long,
    val name: String
) : BaseEntity() {
}