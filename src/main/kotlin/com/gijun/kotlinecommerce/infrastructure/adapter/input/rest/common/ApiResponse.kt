package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.common

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null,
    val errorCode: String? = null
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(success = true, data = data)
        }

        fun <T> error(message: String, errorCode: String? = null): ApiResponse<T> {
            return ApiResponse(success = false, message = message, errorCode = errorCode)
        }
    }
}
