package com.gijun.kotlinecommerce.application.port.input.product

import com.gijun.kotlinecommerce.application.dto.command.product.productReview.CreateProductReviewCommand
import com.gijun.kotlinecommerce.domain.product.model.ProductReviewModel

interface ProductReviewUseCase {
    fun createProductReview(command : CreateProductReviewCommand) : ProductReviewModel
}