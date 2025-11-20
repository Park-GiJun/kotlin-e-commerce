package com.gijun.kotlinecommerce.application.handler.product

import com.gijun.kotlinecommerce.application.dto.command.product.product.CreateProductCommand
import com.gijun.kotlinecommerce.application.dto.command.product.product.UpdateProductCommand
import com.gijun.kotlinecommerce.application.dto.result.product.GetProductResult
import com.gijun.kotlinecommerce.application.port.input.product.ProductUseCase
import com.gijun.kotlinecommerce.application.port.output.cache.ProductPriceCachePort
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductCategoryJpaPort
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductJpaPort
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductPriceJpaPort
import com.gijun.kotlinecommerce.domain.common.PageRequest
import com.gijun.kotlinecommerce.domain.common.PageResponse
import com.gijun.kotlinecommerce.domain.common.validator.CommonValidators
import com.gijun.kotlinecommerce.domain.product.exception.ProductCategoryNotFoundException
import com.gijun.kotlinecommerce.domain.product.exception.ProductNotFoundException
import com.gijun.kotlinecommerce.domain.product.exception.ProductValidationException
import com.gijun.kotlinecommerce.domain.product.model.ProductCategoryModel
import com.gijun.kotlinecommerce.domain.product.model.ProductModel
import com.gijun.kotlinecommerce.domain.product.model.ProductPriceModel
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.time.LocalDate

@Service
class ProductHandler(
    private val productJpaPort: ProductJpaPort,
    private val productCategoryJpaPort: ProductCategoryJpaPort,
    private val productPriceJpaPort: ProductPriceJpaPort,
    private val productPriceCachePort: ProductPriceCachePort
) : ProductUseCase {

    override fun createProduct(command: CreateProductCommand): ProductModel {
        validateForCreate(command.categoryId, command.name)
        validateCategoryExists(command.categoryId)

        val newProduct = ProductModel.create(command.categoryId, command.name)
        return productJpaPort.save(newProduct)
    }

    override fun getProductById(id: Long): GetProductResult {
        val product = validateProductExists(id)
        val allCategories = productCategoryJpaPort.findAll().associateBy { it.id!! }

        val price = productPriceCachePort.findCurrentPriceByProductId(product.id!!)
            ?: productPriceJpaPort.findCurrentPricesByProductIds(listOf(product.id))
                .find { it.productId == product.id }
                ?.also { productPriceCachePort.save(it) }

        return buildProductListResult(product, allCategories, price)
    }

    override fun getAllProducts(pageRequest: PageRequest): PageResponse<GetProductResult> {
        val (products, totalElements) = productJpaPort.findAllWithPaging(pageRequest)

        val allCategories = productCategoryJpaPort.findAll().associateBy { it.id!! }

        val productIds = products.mapNotNull { it.id }

        val priceList = productPriceCachePort.findCurrentPricesByProductIds(productIds)
            .takeIf { it.isNotEmpty() }
            ?: productPriceJpaPort.findCurrentPricesByProductIds(productIds)
                .also { productPriceCachePort.saveAll(it) }

        val prices = priceList.associateBy { it.productId }

        val results = products.map { product ->
            buildProductListResult(product, allCategories, prices[product.id])
        }

        return PageResponse.of(results, pageRequest, totalElements)
    }

    override fun getProductsByCategory(categoryId: Long, pageRequest: PageRequest): PageResponse<GetProductResult> {
        validateCategoryExists(categoryId)

        val (products, totalElements) = productJpaPort.findByCategoryWithPaging(categoryId, pageRequest)

        val allCategories = productCategoryJpaPort.findAll().associateBy { it.id!! }

        val productIds = products.mapNotNull { it.id }

        val priceList = productPriceCachePort.findCurrentPricesByProductIds(productIds)
            .takeIf { it.isNotEmpty() }
            ?: productPriceJpaPort.findCurrentPricesByProductIds(productIds)
                .also { productPriceCachePort.saveAll(it) }

        val prices = priceList.associateBy { it.productId }

        val results = products.map { product ->
            buildProductListResult(product, allCategories, prices[product.id])
        }

        return PageResponse.of(results, pageRequest, totalElements)
    }

    private fun buildProductListResult(
        product: ProductModel,
        allCategories: Map<Long, ProductCategoryModel>,
        price: ProductPriceModel?
    ): GetProductResult {
        val categoryChain = mutableListOf<ProductCategoryModel>()
        var currentCategory = allCategories[product.categoryId]

        while (currentCategory != null) {
            categoryChain.add(0, currentCategory)
            currentCategory = if (currentCategory.parentId != 0L) {
                allCategories[currentCategory.parentId]
            } else {
                null
            }
        }

        val depth = categoryChain.size

        return GetProductResult(
            productId = product.id!!,
            productName = product.name,
            largeClassId = categoryChain.getOrNull(0)?.id ?: 0L,
            largeClassNAme = categoryChain.getOrNull(0)?.name ?: "",
            mediumClassId = categoryChain.getOrNull(1)?.id,
            mediumClassName = categoryChain.getOrNull(1)?.name,
            smallClassId = categoryChain.getOrNull(2)?.id,
            smallClassName = categoryChain.getOrNull(2)?.name,
            categoryDepth = depth,
            productPrice = price?.price?.toBigInteger() ?: BigInteger.ZERO,
            priceStartDate = price?.startDate ?: LocalDate.now(),
            priceEndDate = price?.endDate ?: LocalDate.parse("9999-12-31")
        )
    }

    override fun deleteProduct(id: Long): ProductModel {
        val product = validateProductExists(id)
        return productJpaPort.delete(product)
    }

    override fun updateProduct(command: UpdateProductCommand): ProductModel {
        validateForUpdate(command.id, command.categoryId, command.name)
        validateCategoryExists(command.categoryId)
        validateProductExists(command.id)

        val product = ProductModel.of(command.id, command.categoryId, command.name)
        return productJpaPort.update(product)
    }

    private fun validateProductExists(productId: Long): ProductModel {
        return productJpaPort.findById(productId)
            ?: throw ProductNotFoundException(productId)
    }

    private fun validateProductName(name: String) {
        try {
            CommonValidators.validateNotBlank(name, "Product name")
            CommonValidators.validateLength(name, "Product name", PRODUCT_NAME_MIN_LENGTH, PRODUCT_NAME_MAX_LENGTH)
        } catch (e: Exception) {
            throw ProductValidationException(e.message ?: "Product name validation failed", e)
        }
    }

    private fun validateForCreate(categoryId: Long, name: String) {
        validateProductName(name)
        CommonValidators.validatePositive(categoryId, "Category ID")
    }

    private fun validateForUpdate(id: Long, categoryId: Long, name: String) {
        CommonValidators.validatePositive(id, "Product ID")
        validateProductName(name)
        CommonValidators.validatePositive(categoryId, "Category ID")
    }

    private fun validateCategoryExists(categoryId: Long): ProductCategoryModel {
        return productCategoryJpaPort.findById(categoryId)
            ?: throw ProductCategoryNotFoundException(categoryId)
    }

    companion object {
        private const val PRODUCT_NAME_MIN_LENGTH = 1
        private const val PRODUCT_NAME_MAX_LENGTH = 200
    }
}