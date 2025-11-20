package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.common

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class RequestLoggingFilter : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(javaClass)
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Skip logging for static resources and actuator endpoints
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response)
            return
        }

        val wrappedRequest = ContentCachingRequestWrapper(request)
        val wrappedResponse = ContentCachingResponseWrapper(response)

        val startTime = System.currentTimeMillis()

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse)
        } finally {
            val duration = System.currentTimeMillis() - startTime
            logRequestResponse(wrappedRequest, wrappedResponse, duration)
            wrappedResponse.copyBodyToResponse()
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        return path.startsWith("/actuator") ||
                path.startsWith("/swagger") ||
                path.startsWith("/v3/api-docs") ||
                path.endsWith(".css") ||
                path.endsWith(".js") ||
                path.endsWith(".html") ||
                path.endsWith(".ico")
    }

    private fun logRequestResponse(
        request: ContentCachingRequestWrapper,
        response: ContentCachingResponseWrapper,
        duration: Long
    ) {
        val timestamp = LocalDateTime.now().format(dateTimeFormatter)
        val method = request.method
        val uri = request.requestURI
        val queryString = request.queryString?.let { "?$it" } ?: ""
        val status = response.status
        val clientIp = getClientIp(request)

        // Get request body
        val requestBody = getRequestBody(request)

        // Get response body
        val responseBody = getResponseBody(response)

        val logMessage = buildString {
            appendLine("\n" + "═".repeat(80))
            appendLine("HTTP REQUEST/RESPONSE LOG")
            appendLine("═".repeat(80))
            appendLine("Timestamp      : $timestamp")
            appendLine("Method         : $method")
            appendLine("URI            : $uri$queryString")
            appendLine("Client IP      : $clientIp")
            appendLine("️Duration       : ${duration}ms")
            appendLine("Status Code    : $status ${getStatusText(status)}")

            // Request Headers
            val importantHeaders = listOf("content-type", "user-agent", "accept", "authorization")
            val headers = request.headerNames.toList()
                .filter { it.lowercase() in importantHeaders }
                .associateWith { request.getHeader(it) }

            if (headers.isNotEmpty()) {
                appendLine("─".repeat(80))
                appendLine("📋 Request Headers:")
                headers.forEach { (key, value) ->
                    val maskedValue = if (key.lowercase() == "authorization") {
                        "Bearer ***MASKED***"
                    } else {
                        value
                    }
                    appendLine("   • $key: $maskedValue")
                }
            }

            // Request Body
            if (requestBody.isNotBlank()) {
                appendLine("─".repeat(80))
                appendLine("Request Body:")
                val truncatedBody = if (requestBody.length > 500) {
                    "${requestBody.take(500)}... [truncated ${requestBody.length} bytes]"
                } else {
                    requestBody
                }
                appendLine(truncatedBody)
            }

            // Response Body
            if (responseBody.isNotBlank()) {
                appendLine("─".repeat(80))
                appendLine("Response Body:")
                val truncatedBody = if (responseBody.length > 500) {
                    "${responseBody.take(500)}... [truncated ${responseBody.length} bytes]"
                } else {
                    responseBody
                }
                appendLine(truncatedBody)
            }

            appendLine("═".repeat(80))
        }

        // Log based on status code
        when {
            status >= 500 -> log.error(logMessage)
            status >= 400 -> log.warn(logMessage)
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

    private fun getRequestBody(request: ContentCachingRequestWrapper): String {
        return try {
            val content = request.contentAsByteArray
            if (content.isNotEmpty()) {
                String(content, Charsets.UTF_8)
            } else {
                ""
            }
        } catch (e: Exception) {
            "[Unable to read request body: ${e.message}]"
        }
    }

    private fun getResponseBody(response: ContentCachingResponseWrapper): String {
        return try {
            val content = response.contentAsByteArray
            if (content.isNotEmpty()) {
                String(content, Charsets.UTF_8)
            } else {
                ""
            }
        } catch (e: Exception) {
            "[Unable to read response body: ${e.message}]"
        }
    }

    private fun getStatusText(status: Int): String {
        return when (status) {
            200 -> "OK"
            201 -> "Created"
            204 -> "No Content"
            400 -> "Bad Request"
            401 -> "Unauthorized"
            403 -> "Forbidden"
            404 -> "Not Found"
            500 -> "Internal Server Error"
            else -> ""
        }
    }
}
