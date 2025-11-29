package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.product

import com.gijun.kotlinecommerce.application.dto.command.product.productStock.AdjustProductStockCommand
import com.gijun.kotlinecommerce.application.port.input.product.ProductStockUseCase
import com.gijun.kotlinecommerce.domain.product.model.ProductStockModel
import com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/product-stocks")
@Tag(name = "ProductStock", description = "Product Stock API")
class ProductStockRestAdapter(
    private val productStockUseCase: ProductStockUseCase
) {

    @GetMapping
    @Operation(summary = "Get all product stocks")
    fun getAllProductStocks(): ApiResponse<List<ProductStockModel>> {
        return ApiResponse.success(productStockUseCase.getAllProductStock())
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get stock by product id")
    fun getStockByProductId(@PathVariable productId: Long): ApiResponse<ProductStockModel?> {
        val stocks = productStockUseCase.getAllProductStock()
        val stock = stocks.find { it.productId == productId }
        return ApiResponse.success(stock)
    }

    @PostMapping("/adjust")
    @Operation(summary = "Adjust product stock")
    fun adjustProductStock(@RequestBody command: AdjustProductStockCommand): ApiResponse<ProductStockModel> {
        return ApiResponse.success(productStockUseCase.adjustProductStock(command))
    }
}