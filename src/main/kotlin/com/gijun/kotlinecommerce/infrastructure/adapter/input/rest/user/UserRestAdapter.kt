package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.user

import com.gijun.kotlinecommerce.application.dto.command.user.RegisterUserCommand
import com.gijun.kotlinecommerce.application.port.input.user.UserUseCase
import com.gijun.kotlinecommerce.domain.common.PageRequest
import com.gijun.kotlinecommerce.domain.common.PageResponse
import com.gijun.kotlinecommerce.domain.user.exception.UserValidationException
import com.gijun.kotlinecommerce.domain.user.model.UserModel
import com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.common.ApiResponse
import com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.user.request.LoginRequest
import com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.user.reponse.LoginResponse
import com.gijun.kotlinecommerce.infrastructure.security.JwtTokenProvider
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User API")
class UserRestAdapter(
    private val userUseCase: UserUseCase,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register new user", security = [])
    @SecurityRequirement(name = "")
    fun registerUser(@RequestBody command: RegisterUserCommand): ApiResponse<UserModel> {
        return ApiResponse.success(userUseCase.registerUser(command))
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", security = [])
    @SecurityRequirement(name = "")
    fun login(@RequestBody request: LoginRequest): ApiResponse<LoginResponse> {
        val user = userUseCase.login(request.email, request.password)
            ?: throw UserValidationException("Invalid email or password")

        val token = jwtTokenProvider.createToken(user.email)
        return ApiResponse.success(LoginResponse.of(token, user))
    }

    @GetMapping
    @Operation(summary = "Get all users")
    fun getAllUsers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ApiResponse<PageResponse<UserModel>> {
        val pageRequest = PageRequest(page, size)
        return ApiResponse.success(userUseCase.getAllUsers(pageRequest))
    }

    @GetMapping("/{email}")
    @Operation(summary = "Get user by email")
    fun getUserByEmail(@PathVariable email: String): ApiResponse<UserModel?> {
        return ApiResponse.success(userUseCase.getUserByEmail(email))
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user")
    fun deleteUser(@PathVariable userId: String): ApiResponse<Boolean> {
        return ApiResponse.success(userUseCase.deleteUser(userId))
    }
}
