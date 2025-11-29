package com.gijun.kotlinecommerce.application.handler.product

import com.gijun.kotlinecommerce.application.dto.command.product.productReview.CreateProductReviewCommand
import com.gijun.kotlinecommerce.application.port.input.product.ProductReviewUseCase
import com.gijun.kotlinecommerce.application.port.input.product.ProductUseCase
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductJpaPort
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductReviewJpaPort
import com.gijun.kotlinecommerce.application.port.output.persistence.user.UserJpaPort
import com.gijun.kotlinecommerce.domain.common.validator.CommonValidators
import com.gijun.kotlinecommerce.domain.product.exception.ProductNotFoundException
import com.gijun.kotlinecommerce.domain.product.exception.ProductReviewConflictException
import com.gijun.kotlinecommerce.domain.product.model.ProductModel
import com.gijun.kotlinecommerce.domain.product.model.ProductReviewModel
import com.gijun.kotlinecommerce.domain.user.exception.UserNotFoundException
import com.gijun.kotlinecommerce.domain.user.model.UserModel
import org.springframework.stereotype.Service

@Service
class ProductReviewHandler(
    private val userJpaPort: UserJpaPort,
    private val productJpaPort: ProductJpaPort,
    private val productReviewJpaPort: ProductReviewJpaPort
) : ProductReviewUseCase {
    override fun createProductReview(command: CreateProductReviewCommand): ProductReviewModel {
        validateRatingInRage(command.rating)
        validateDuplicateReview(command.productId, command.reviewerId)
        validateProductExists(command.productId)
        val user = validateUserExists(command.reviewerId)

        val productReviewModel = ProductReviewModel.create(command.productId, command.rating, command.comment, user.name, command.reviewerId)

        return productReviewJpaPort.save(productReviewModel)
    }

    private fun validateRatingInRage(rating: Int) {
        CommonValidators.validateRange(rating, "Review Rating", 0, 10)
    }

    private fun validateProductExists(productId: Long): ProductModel {
        return productJpaPort.findById(productId)
            ?: throw ProductNotFoundException(productId)
    }

    private fun validateUserExists(userId: Long): UserModel {
        return userJpaPort.findById(userId)
            ?: throw UserNotFoundException(userId)
    }

    private fun validateDuplicateReview(productId: Long, reviewerId: Long) {
        if(productReviewJpaPort.isExistByProductIdAndUserId(productId, reviewerId)){
            throw ProductReviewConflictException(productId)
        }
    }

}