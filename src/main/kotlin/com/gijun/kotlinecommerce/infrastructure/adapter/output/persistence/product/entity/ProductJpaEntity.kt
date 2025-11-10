package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity

import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.common.BaseEntity

class ProductJpaEntity(
    val id: Long,
    val productCategoryId: Long,
    val name: String
) : BaseEntity(){

}