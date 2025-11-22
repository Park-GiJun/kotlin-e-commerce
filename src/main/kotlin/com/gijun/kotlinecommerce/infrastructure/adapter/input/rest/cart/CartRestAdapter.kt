package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.cart

import com.gijun.kotlinecommerce.application.port.input.cart.CartUseCase
import com.gijun.kotlinecommerce.domain.cart.model.CartModel
import com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.cart.request.CartUpdateRequest
import com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.common.ApiResponse
import com.gijun.kotlinecommerce.infrastructure.security.SecurityUser
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/carts")
@Tag(name = "Cart", description = "Cart API")
class CartRestAdapter(
    private val cartUseCase: CartUseCase
) {

    @GetMapping
    @Operation(summary = "Get my cart")
    fun getMyCart(@AuthenticationPrincipal user: SecurityUser): ApiResponse<List<CartModel>> {
        return ApiResponse.success(cartUseCase.getCart(user.getUserModel().id!!))
    }

    @PostMapping
    @Operation(summary = "Update cart item")
    fun updateCart(
        @AuthenticationPrincipal user: SecurityUser,
        @RequestBody request: CartUpdateRequest
    ): ApiResponse<List<CartModel>> {
        return ApiResponse.success(
            cartUseCase.updateCart(
                userId = user.getUserModel().id!!,
                productId = request.productId,
                qty = request.qty,
                action = request.action
            )
        )
    }
}

