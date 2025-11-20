package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.common

import com.gijun.kotlinecommerce.domain.common.exception.DomainNotFoundException
import com.gijun.kotlinecommerce.domain.common.exception.DomainValidationException
import com.gijun.kotlinecommerce.domain.common.exception.DomainAlreadyExistsException
import com.gijun.kotlinecommerce.domain.common.exception.DomainUnauthorizedException
import com.gijun.kotlinecommerce.domain.common.exception.DomainForbiddenException
import com.gijun.kotlinecommerce.domain.common.exception.DomainConflictException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")

    @ExceptionHandler(DomainNotFoundException::class)
    fun handleNotFoundException(e: DomainNotFoundException, request: HttpServletRequest): ResponseEntity<ApiResponse<Unit>> {
        logException(e, request, HttpStatus.NOT_FOUND, "NOT_FOUND")
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error(e.message ?: "Not Found", "NOT_FOUND"))
    }

    @ExceptionHandler(DomainValidationException::class)
    fun handleValidationException(e: DomainValidationException, request: HttpServletRequest): ResponseEntity<ApiResponse<Unit>> {
        logException(e, request, HttpStatus.BAD_REQUEST, "VALIDATION_ERROR")
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(e.message ?: "Validation Failed", "VALIDATION_ERROR"))
    }

    @ExceptionHandler(DomainAlreadyExistsException::class)
    fun handleAlreadyExistsException(e: DomainAlreadyExistsException, request: HttpServletRequest): ResponseEntity<ApiResponse<Unit>> {
        logException(e, request, HttpStatus.CONFLICT, "ALREADY_EXISTS")
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiResponse.error(e.message ?: "Already Exists", "ALREADY_EXISTS"))
    }

    @ExceptionHandler(DomainUnauthorizedException::class)
    fun handleUnauthorizedException(e: DomainUnauthorizedException, request: HttpServletRequest): ResponseEntity<ApiResponse<Unit>> {
        logException(e, request, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED")
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse.error(e.message ?: "Unauthorized", "UNAUTHORIZED"))
    }

    @ExceptionHandler(DomainForbiddenException::class)
    fun handleForbiddenException(e: DomainForbiddenException, request: HttpServletRequest): ResponseEntity<ApiResponse<Unit>> {
        logException(e, request, HttpStatus.FORBIDDEN, "FORBIDDEN")
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(ApiResponse.error(e.message ?: "Forbidden", "FORBIDDEN"))
    }

    @ExceptionHandler(DomainConflictException::class)
    fun handleConflictException(e: DomainConflictException, request: HttpServletRequest): ResponseEntity<ApiResponse<Unit>> {
        logException(e, request, HttpStatus.CONFLICT, "CONFLICT")
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiResponse.error(e.message ?: "Conflict", "CONFLICT"))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException, request: HttpServletRequest): ResponseEntity<ApiResponse<Unit>> {
        logException(e, request, HttpStatus.BAD_REQUEST, "INVALID_ARGUMENT")
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(e.message ?: "Invalid Argument", "INVALID_ARGUMENT"))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception, request: HttpServletRequest): ResponseEntity<ApiResponse<Unit>> {
        logException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", includeStackTrace = true)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error(e.message ?: "Internal Server Error", "INTERNAL_ERROR"))
    }

    private fun logException(
        exception: Exception,
        request: HttpServletRequest,
        status: HttpStatus,
        errorCode: String,
        includeStackTrace: Boolean = false
    ) {
        val timestamp = LocalDateTime.now().format(dateTimeFormatter)
        val method = request.method
        val uri = request.requestURI
        val queryString = request.queryString?.let { "?$it" } ?: ""
        val clientIp = getClientIp(request)

        val logMessage = buildString {
            appendLine("\n╔════════════════════════════════════════════════════════════════")
            appendLine("║ EXCEPTION CAUGHT")
            appendLine("╠════════════════════════════════════════════════════════════════")
            appendLine("║ Timestamp      : $timestamp")
            appendLine("║ Status Code    : ${status.value()} ${status.reasonPhrase}")
            appendLine("║ Error Code     : $errorCode")
            appendLine("║ Method         : $method")
            appendLine("║ URI            : $uri$queryString")
            appendLine("║ Client IP      : $clientIp")
            appendLine("╠────────────────────────────────────────────────────────────────")
            appendLine("║ Exception Details:")
            appendLine("║   Type         : ${exception.javaClass.name}")
            appendLine("║   Message      : ${exception.message ?: "No message"}")

            if (exception.cause != null) {
                appendLine("║   Caused By    : ${exception.cause?.javaClass?.name}")
                appendLine("║   Cause Message: ${exception.cause?.message}")
            }

            if (includeStackTrace) {
                appendLine("╠────────────────────────────────────────────────────────────────")
                appendLine("║ Stack Trace:")
                exception.stackTrace.take(10).forEach { element ->
                    appendLine("║   at $element")
                }
                if (exception.stackTrace.size > 10) {
                    appendLine("║   ... ${exception.stackTrace.size - 10} more")
                }
            }

            appendLine("╚════════════════════════════════════════════════════════════════")
        }

        when {
            status.is5xxServerError -> log.error(logMessage, if (includeStackTrace) exception else null)
            status.is4xxClientError -> log.warn(logMessage)
            else -> log.info(logMessage)
        }
    }

    private fun getClientIp(request: HttpServletRequest): String {
        val xForwardedFor = request.getHeader("X-Forwarded-For")
        return when {
            !xForwardedFor.isNullOrBlank() -> xForwardedFor.split(",")[0].trim()
            else -> request.remoteAddr
        }
    }
}
