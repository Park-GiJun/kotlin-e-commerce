package com.gijun.kotlinecommerce.domain.product.model

data class ProductImageModel(
    val id: Long?,
    val productId: Long,
    val imageOrder: Int,
    val isMain: Boolean,
    val imageUrl: String
) {
    companion object {
        fun create(productId: Long, imageOrder: Int, isMain: Boolean, imageUrl: String): ProductImageModel {
            return ProductImageModel(null, productId, imageOrder, isMain, imageUrl)
        }

        fun of(id: Long, productId: Long, imageOrder: Int, isMain: Boolean, imageUrl: String): ProductImageModel {
            return ProductImageModel(id, productId, imageOrder, isMain, imageUrl)
        }
    }
}
