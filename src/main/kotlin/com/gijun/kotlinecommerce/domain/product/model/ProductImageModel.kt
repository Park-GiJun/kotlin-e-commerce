package com.gijun.kotlinecommerce.domain.product.model

data class ProductImageModel(
    val id: Long?,
    val productId: Long,
    val order: Int,
    val isMain: Boolean,
    val imageUrl: String
) {
    companion object {
        fun create(productId: Long, order: Int, isMain: Boolean, imageUrl: String): ProductImageModel {
            return ProductImageModel(null, productId, order, isMain, imageUrl)
        }

        fun of(id: Long, productId: Long, order: Int, isMain: Boolean, imageUrl: String): ProductImageModel {
            return ProductImageModel(id, productId, order, isMain, imageUrl)
        }
    }
}
