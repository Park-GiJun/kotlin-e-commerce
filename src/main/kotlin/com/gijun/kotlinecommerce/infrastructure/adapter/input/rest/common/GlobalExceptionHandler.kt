package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.common

import com.gijun.kotlinecommerce.domain.common.exception.DomainNotFoundException
import com.gijun.kotlinecommerce.domain.common.exception.DomainValidationException
import com.gijun.kotlinecommerce.domain.common.exception.DomainAlreadyExistsException
import com.gijun.kotlinecommerce.domain.common.exception.DomainUnauthorizedException
import com.gijun.kotlinecommerce.domain.common.exception.DomainForbiddenException
import com.gijun.kotlinecommerce.domain.common.exception.DomainConflictException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DomainNotFoundException::class)
    fun handleNotFoundException(e: DomainNotFoundException): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error(e.message ?: "Not Found", "NOT_FOUND"))
    }

    @ExceptionHandler(DomainValidationException::class)
    fun handleValidationException(e: DomainValidationException): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(e.message ?: "Validation Failed", "VALIDATION_ERROR"))
    }

    @ExceptionHandler(DomainAlreadyExistsException::class)
    fun handleAlreadyExistsException(e: DomainAlreadyExistsException): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiResponse.error(e.message ?: "Already Exists", "ALREADY_EXISTS"))
    }

    @ExceptionHandler(DomainUnauthorizedException::class)
    fun handleUnauthorizedException(e: DomainUnauthorizedException): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse.error(e.message ?: "Unauthorized", "UNAUTHORIZED"))
    }

    @ExceptionHandler(DomainForbiddenException::class)
    fun handleForbiddenException(e: DomainForbiddenException): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(ApiResponse.error(e.message ?: "Forbidden", "FORBIDDEN"))
    }

    @ExceptionHandler(DomainConflictException::class)
    fun handleConflictException(e: DomainConflictException): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiResponse.error(e.message ?: "Conflict", "CONFLICT"))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(e.message ?: "Invalid Argument", "INVALID_ARGUMENT"))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error(e.message ?: "Internal Server Error", "INTERNAL_ERROR"))
    }
}
