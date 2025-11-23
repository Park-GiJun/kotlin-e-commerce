package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.product

import com.gijun.kotlinecommerce.application.dto.command.product.productCategory.CreateProductCategoryCommand
import com.gijun.kotlinecommerce.application.dto.command.product.productCategory.UpdateProductCategoryCommand
import com.gijun.kotlinecommerce.application.dto.result.product.productCategory.GetProductCategoryHierarchyResult
import com.gijun.kotlinecommerce.application.port.input.product.ProductCategoryUseCase
import com.gijun.kotlinecommerce.domain.product.model.ProductCategoryModel
import com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/product-categories")
@Tag(name = "Product Category", description = "Product Category API")
class ProductCategoryRestAdapter(
    private val productCategoryUseCase: ProductCategoryUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create product category")
    fun createProductCategory(@RequestBody command: CreateProductCategoryCommand): ApiResponse<ProductCategoryModel> {
        return ApiResponse.success(productCategoryUseCase.createProductCategory(command))
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product category by id")
    fun getProductCategoryById(@PathVariable id: Long): ApiResponse<ProductCategoryModel> {
        return ApiResponse.success(productCategoryUseCase.getProductCategoryById(id))
    }

    @GetMapping
    @Operation(summary = "Get all product categories")
    fun getAllProductCategories(): ApiResponse<List<ProductCategoryModel>> {
        return ApiResponse.success(productCategoryUseCase.getAllProductCategories())
    }

    @GetMapping("/hierarchies")
    @Operation(summary = "Get product category hierarchies")
    fun getProductCategoryHierarchies(): ApiResponse<List<GetProductCategoryHierarchyResult>> {
        return ApiResponse.success(productCategoryUseCase.getProductCategoryHierarchies())
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product category")
    fun updateProductCategory(
        @PathVariable id: Long,
        @RequestBody command: UpdateProductCategoryCommand
    ): ApiResponse<ProductCategoryModel> {
        return ApiResponse.success(productCategoryUseCase.updateProductCategory(command.copy(id = id)))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product category")
    fun deleteProductCategory(@PathVariable id: Long): ApiResponse<ProductCategoryModel> {
        return ApiResponse.success(productCategoryUseCase.deleteProductCategory(id))
    }
}
