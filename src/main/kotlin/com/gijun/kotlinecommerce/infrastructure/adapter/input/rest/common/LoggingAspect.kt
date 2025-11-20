package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.common

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Aspect
@Component
class LoggingAspect(
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")

    @Around("execution(* com.gijun.kotlinecommerce.infrastructure.adapter.input.rest..*RestAdapter.*(..))")
    fun logApiCalls(joinPoint: ProceedingJoinPoint): Any? {
        val request = getHttpServletRequest() ?: return joinPoint.proceed()

        val startTime = System.currentTimeMillis()
        val timestamp = LocalDateTime.now().format(dateTimeFormatter)

        // Request Logging
        logRequest(joinPoint, request, timestamp)

        return try {
            val result = joinPoint.proceed()
            val duration = System.currentTimeMillis() - startTime

            // Response Logging (Success)
            logResponse(request, result, duration, timestamp, null)

            result
        } catch (e: Exception) {
            val duration = System.currentTimeMillis() - startTime

            // Response Logging (Error)
            logResponse(request, null, duration, timestamp, e)

            throw e
        }
    }

    private fun logRequest(joinPoint: ProceedingJoinPoint, request: HttpServletRequest, timestamp: String) {
        val method = request.method
        val uri = request.requestURI
        val queryString = request.queryString?.let { "?$it" } ?: ""
        val headers = getRequestHeaders(request)
        val parameters = getRequestParameters(joinPoint)
        val clientIp = getClientIp(request)

        val logMessage = buildString {
            appendLine("\n╔════════════════════════════════════════════════════════════════")
            appendLine("║ INCOMING REQUEST")
            appendLine("╠════════════════════════════════════════════════════════════════")
            appendLine("║ Timestamp    : $timestamp")
            appendLine("║ Method       : $method")
            appendLine("║ URI          : $uri$queryString")
            appendLine("║ Client IP    : $clientIp")
            appendLine("║ Handler      : ${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}")

            if (headers.isNotEmpty()) {
                appendLine("╠────────────────────────────────────────────────────────────────")
                appendLine("║ Headers:")
                headers.forEach { (key, value) ->
                    // Mask sensitive headers
                    val maskedValue = if (key.lowercase() in listOf("authorization", "cookie")) {
                        "***MASKED***"
                    } else {
                        value
                    }
                    appendLine("║   $key: $maskedValue")
                }
            }

            if (parameters.isNotEmpty()) {
                appendLine("╠────────────────────────────────────────────────────────────────")
                appendLine("║ Parameters:")
                parameters.forEach { param ->
                    val paramValue = try {
                        if (param == null) "null"
                        else if (isPrimitiveOrString(param)) param.toString()
                        else objectMapper.writeValueAsString(param)
                    } catch (e: Exception) {
                        param.toString()
                    }
                    appendLine("║   ${param?.javaClass?.simpleName ?: "null"}: $paramValue")
                }
            }

            appendLine("╚════════════════════════════════════════════════════════════════")
        }

        log.info(logMessage)
    }

    private fun logResponse(
        request: HttpServletRequest,
        result: Any?,
        duration: Long,
        requestTimestamp: String,
        error: Exception?
    ) {
        val timestamp = LocalDateTime.now().format(dateTimeFormatter)
        val uri = request.requestURI
        val method = request.method

        val logMessage = buildString {
            appendLine("\n╔════════════════════════════════════════════════════════════════")
            if (error != null) {
                appendLine("║ OUTGOING RESPONSE (ERROR)")
            } else {
                appendLine("║ OUTGOING RESPONSE (SUCCESS)")
            }
            appendLine("╠════════════════════════════════════════════════════════════════")
            appendLine("║ Request Time : $requestTimestamp")
            appendLine("║ Response Time: $timestamp")
            appendLine("║ Duration     : ${duration}ms")
            appendLine("║ Method       : $method")
            appendLine("║ URI          : $uri")

            if (error != null) {
                appendLine("╠────────────────────────────────────────────────────────────────")
                appendLine("║ Error:")
                appendLine("║   Type    : ${error.javaClass.simpleName}")
                appendLine("║   Message : ${error.message}")
            } else if (result != null) {
                appendLine("╠────────────────────────────────────────────────────────────────")
                appendLine("║ Response:")
                val responseBody = try {
                    if (isPrimitiveOrString(result)) result.toString()
                    else objectMapper.writeValueAsString(result)
                } catch (e: Exception) {
                    result.toString()
                }
                // Limit response body length for logging
                val truncatedBody = if (responseBody.length > 1000) {
                    "${responseBody.take(1000)}... [truncated]"
                } else {
                    responseBody
                }
                appendLine("║   $truncatedBody")
            }

            appendLine("╚════════════════════════════════════════════════════════════════")
        }

        if (error != null) {
            log.error(logMessage, error)
        } else {
            log.info(logMessage)
        }
    }

    private fun getHttpServletRequest(): HttpServletRequest? {
        val attributes = RequestContextHolder.getRequestAttributes()
        return (attributes as? ServletRequestAttributes)?.request
    }

    private fun getRequestHeaders(request: HttpServletRequest): Map<String, String> {
        return request.headerNames.toList().associateWith { headerName ->
            request.getHeader(headerName)
        }
    }

    private fun getRequestParameters(joinPoint: ProceedingJoinPoint): List<Any?> {
        return joinPoint.args.toList()
    }

    private fun getClientIp(request: HttpServletRequest): String {
        val xForwardedFor = request.getHeader("X-Forwarded-For")
        return when {
            !xForwardedFor.isNullOrBlank() -> xForwardedFor.split(",")[0].trim()
            else -> request.remoteAddr
        }
    }

    private fun isPrimitiveOrString(obj: Any): Boolean {
        return obj is String || obj is Number || obj is Boolean || obj is Char
    }
}
