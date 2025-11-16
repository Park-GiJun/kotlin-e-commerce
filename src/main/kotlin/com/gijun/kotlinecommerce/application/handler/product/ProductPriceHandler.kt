package com.gijun.kotlinecommerce.application.handler.product

import com.gijun.kotlinecommerce.application.dto.command.product.productPrice.CreateProductPriceCommand
import com.gijun.kotlinecommerce.application.dto.command.product.productPrice.UpdateProductPriceCommand
import com.gijun.kotlinecommerce.application.port.input.product.ProductPriceUseCase
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductJpaPort
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductPriceJpaPort
import com.gijun.kotlinecommerce.domain.common.validator.CommonValidators
import com.gijun.kotlinecommerce.domain.product.exception.ProductNotFoundException
import com.gijun.kotlinecommerce.domain.product.exception.ProductPriceNotFoundException
import com.gijun.kotlinecommerce.domain.product.exception.ProductPriceValidationException
import com.gijun.kotlinecommerce.domain.product.model.ProductModel
import com.gijun.kotlinecommerce.domain.product.model.ProductPriceModel
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDate

@Service
class ProductPriceHandler(
    private val productPriceJpaPort: ProductPriceJpaPort,
    private val productJpaPort: ProductJpaPort
) : ProductPriceUseCase {

    override fun createProductPrice(command: CreateProductPriceCommand): ProductPriceModel {
        validateForCreate(command.productId, command.price, command.startDate, command.endDate)

        validateProductExists(command.productId)

        val startDate = parseDate(command.startDate, "시작일")
        val endDate = parseDate(command.endDate, "종료일")

        validateDateRange(startDate, endDate)

        val newProductPrice = ProductPriceModel.create(
            productId = command.productId,
            price = BigDecimal(command.price.toString()),
            startDate = startDate,
            endDate = endDate
        )

        return productPriceJpaPort.save(newProductPrice)
    }

    override fun updateProductPrice(command: UpdateProductPriceCommand): ProductPriceModel {
        validateForUpdate(command.productPriceId, command.productId, command.price, command.startDate, command.endDate)

        validateProductPriceExists(command.productPriceId)

        validateProductExists(command.productId)

        val startDate = parseDate(command.startDate, "시작일")
        val endDate = parseDate(command.endDate, "종료일")

        validateDateRange(startDate, endDate)

        val updatedProductPrice = ProductPriceModel.of(
            id = command.productPriceId,
            productId = command.productId,
            price = BigDecimal(command.price.toString()),
            startDate = startDate,
            endDate = endDate
        )

        return productPriceJpaPort.update(updatedProductPrice)
    }

    override fun getAllProductPrices(): List<ProductPriceModel> {
        return productPriceJpaPort.findAllCurrentPrices()
    }

    override fun getProductPricesByProductId(productId: Long): List<ProductPriceModel> {
        CommonValidators.validatePositive(productId, "상품 ID")
        validateProductExists(productId)
        return productPriceJpaPort.findByProductId(productId)
    }

    override fun getProductPriceById(id: Long): ProductPriceModel {
        CommonValidators.validatePositive(id, "상품 가격 ID")
        return validateProductPriceExists(id)
    }

    override fun deleteProductPrice(id: Long): ProductPriceModel {
        CommonValidators.validatePositive(id, "상품 가격 ID")
        val productPrice = validateProductPriceExists(id)
        return productPriceJpaPort.delete(productPrice)
    }

    private fun validateProductExists(productId: Long): ProductModel {
        return productJpaPort.findById(productId)
            ?: throw ProductNotFoundException(productId)
    }

    private fun validateProductPriceExists(productPriceId: Long): ProductPriceModel {
        return productPriceJpaPort.findById(productPriceId)
            ?: throw ProductPriceNotFoundException(productPriceId)
    }

    private fun validateForCreate(productId: Long, price: BigInteger, startDate: String, endDate: String) {
        try {
            CommonValidators.validatePositive(productId, "상품 ID")
            validatePrice(price)
            CommonValidators.validateNotBlank(startDate, "시작일")
            CommonValidators.validateNotBlank(endDate, "종료일")
        } catch (e: Exception) {
            throw ProductPriceValidationException(e.message ?: "상품 가격 생성 유효성 검증 실패", e)
        }
    }

    private fun validateForUpdate(productPriceId: Long, productId: Long, price: BigInteger, startDate: String, endDate: String) {
        try {
            CommonValidators.validatePositive(productPriceId, "상품 가격 ID")
            CommonValidators.validatePositive(productId, "상품 ID")
            validatePrice(price)
            CommonValidators.validateNotBlank(startDate, "시작일")
            CommonValidators.validateNotBlank(endDate, "종료일")
        } catch (e: Exception) {
            throw ProductPriceValidationException(e.message ?: "상품 가격 수정 유효성 검증 실패", e)
        }
    }

    private fun validatePrice(price: BigInteger) {
        if (price <= BigInteger.ZERO) {
            throw ProductPriceValidationException("가격은 0보다 커야 합니다")
        }
        if (price > MAX_PRICE) {
            throw ProductPriceValidationException("가격은 ${MAX_PRICE}을 초과할 수 없습니다")
        }
    }

    private fun parseDate(dateString: String, fieldName: String): LocalDate {
        return try {
            LocalDate.parse(dateString)
        } catch (e: Exception) {
            throw ProductPriceValidationException("$fieldName 형식이 올바르지 않습니다 (YYYY-MM-DD 형식이어야 합니다)", e)
        }
    }

    private fun validateDateRange(startDate: LocalDate, endDate: LocalDate) {
        if (startDate.isAfter(endDate)) {
            throw ProductPriceValidationException("시작일은 종료일보다 이전이거나 같아야 합니다")
        }
    }

    companion object {
        private val MAX_PRICE = BigInteger("999999999999") // 1조 미만
    }
}