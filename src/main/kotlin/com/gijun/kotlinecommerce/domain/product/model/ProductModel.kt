package com.gijun.kotlinecommerce.domain.product.model

data class ProductModel(
    val id: Long?,
    val categoryId: Long,
    val name: String
) {
    companion object{
        fun create(categoryId: Long, name: String): ProductModel {
            return ProductModel(null, categoryId, name)
        }

        fun of(id: Long, categoryId: Long, name: String) : ProductModel {
            return ProductModel(id, categoryId, name)
        }
    }
}
