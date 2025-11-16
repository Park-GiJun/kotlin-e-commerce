package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.product

import com.gijun.kotlinecommerce.application.dto.command.product.product.CreateProductCommand
import com.gijun.kotlinecommerce.application.dto.command.product.product.UpdateProductCommand
import com.gijun.kotlinecommerce.application.dto.result.product.GetProductResult
import com.gijun.kotlinecommerce.application.port.input.product.ProductUseCase
import com.gijun.kotlinecommerce.domain.common.PageRequest
import com.gijun.kotlinecommerce.domain.common.PageResponse
import com.gijun.kotlinecommerce.domain.product.model.ProductModel
import com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "Product API")
class ProductRestAdapter(
    private val productUseCase: ProductUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create product")
    fun createProduct(@RequestBody command: CreateProductCommand): ApiResponse<ProductModel> {
        return ApiResponse.success(productUseCase.createProduct(command))
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    fun getProductById(@PathVariable id: Long): ApiResponse<ProductModel> {
        return ApiResponse.success(productUseCase.getProductById(id))
    }

    @GetMapping
    @Operation(summary = "Get all products")
    fun getAllProducts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ApiResponse<PageResponse<GetProductResult>> {
        val pageRequest = PageRequest(page, size)
        return ApiResponse.success(productUseCase.getAllProducts(pageRequest))
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get products by category")
    fun getProductsByCategory(
        @PathVariable categoryId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ApiResponse<PageResponse<GetProductResult>> {
        val pageRequest = PageRequest(page, size)
        return ApiResponse.success(productUseCase.getProductsByCategory(categoryId, pageRequest))
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody command: UpdateProductCommand
    ): ApiResponse<ProductModel> {
        return ApiResponse.success(productUseCase.updateProduct(command.copy(id = id)))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product")
    fun deleteProduct(@PathVariable id: Long): ApiResponse<ProductModel> {
        return ApiResponse.success(productUseCase.deleteProduct(id))
    }
}
