package com.gijun.kotlinecommerce.domain.product.model

data class ProductCategoryModel(
    val id: Long?,
    val parentId: Long,
    val name: String
) {
    companion object {
        fun create(name: String, parentId: Long = 0): ProductCategoryModel {
            return ProductCategoryModel(null, parentId, name)
        }

        fun of(id: Long, name: String, parentId: Long): ProductCategoryModel {
            return ProductCategoryModel(id, parentId, name)
        }
    }
}
