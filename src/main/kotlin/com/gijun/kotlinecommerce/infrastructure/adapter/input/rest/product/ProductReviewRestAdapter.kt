package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.product

import com.gijun.kotlinecommerce.application.dto.command.product.productReview.CreateProductReviewCommand
import com.gijun.kotlinecommerce.application.port.input.product.ProductReviewUseCase
import com.gijun.kotlinecommerce.domain.product.model.ProductReviewModel
import com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.common.ApiResponse
import com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.product.request.CreateReviewRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Product Review", description = "Product Review API")
class ProductReviewRestAdapter(
    private val productReviewUseCase: ProductReviewUseCase
) {

    @PostMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create product review")
    fun createProductReview(
        @PathVariable productId: Long,
        @RequestBody request: CreateReviewRequest
    ): ApiResponse<ProductReviewModel> {
        val command = CreateProductReviewCommand(
            productId = productId,
            reviewerId = request.reviewerId,
            rating = request.rating,
            comment = request.comment
        )
        return ApiResponse.success(productReviewUseCase.createProductReview(command))
    }
}

