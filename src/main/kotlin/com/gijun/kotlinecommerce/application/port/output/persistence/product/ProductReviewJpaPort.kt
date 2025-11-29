package com.gijun.kotlinecommerce.application.port.output.persistence.product

import com.gijun.kotlinecommerce.domain.product.model.ProductReviewModel

interface ProductReviewJpaPort {
    fun save(productReviewModel: ProductReviewModel): ProductReviewModel
    fun isExistByProductIdAndUserId(productId: Long, userId: Long) : Boolean
    fun getProductReviewByProductId(productId: Long) : List<ProductReviewModel>
}