package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.product

import com.gijun.kotlinecommerce.application.dto.command.product.productPrice.CreateProductPriceCommand
import com.gijun.kotlinecommerce.application.dto.command.product.productPrice.UpdateProductPriceCommand
import com.gijun.kotlinecommerce.application.port.input.product.ProductPriceUseCase
import com.gijun.kotlinecommerce.domain.product.model.ProductPriceModel
import com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/product-prices")
@Tag(name = "ProductPrice", description = "Product Price API")
class ProductPriceRestAdapter(
    private val productPriceUseCase: ProductPriceUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create product price")
    fun createProductPrice(@RequestBody command: CreateProductPriceCommand): ApiResponse<ProductPriceModel> {
        return ApiResponse.success(productPriceUseCase.createProductPrice(command))
    }

    @GetMapping
    @Operation(summary = "Get all current product prices")
    fun getAllProductPrices(): ApiResponse<List<ProductPriceModel>> {
        return ApiResponse.success(productPriceUseCase.getAllProductPrices())
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product price by id")
    fun getProductPriceById(@PathVariable id: Long): ApiResponse<ProductPriceModel> {
        return ApiResponse.success(productPriceUseCase.getProductPriceById(id))
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get product prices by product id")
    fun getProductPricesByProductId(@PathVariable productId: Long): ApiResponse<List<ProductPriceModel>> {
        return ApiResponse.success(productPriceUseCase.getProductPricesByProductId(productId))
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product price")
    fun updateProductPrice(
        @PathVariable id: Long,
        @RequestBody command: UpdateProductPriceCommand
    ): ApiResponse<ProductPriceModel> {
        return ApiResponse.success(productPriceUseCase.updateProductPrice(command.copy(productPriceId = id)))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product price")
    fun deleteProductPrice(@PathVariable id: Long): ApiResponse<ProductPriceModel> {
        return ApiResponse.success(productPriceUseCase.deleteProductPrice(id))
    }
}
