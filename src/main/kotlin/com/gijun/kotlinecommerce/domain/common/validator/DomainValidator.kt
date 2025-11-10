package com.gijun.kotlinecommerce.domain.common.validator

import com.gijun.kotlinecommerce.domain.common.exception.DomainValidationException

/**
 * 도메인 공통 Validator 인터페이스
 */
interface DomainValidator<T> {
    /**
     * 주어진 값의 유효성을 검증하고, 유효하지 않으면 예외를 발생시킵니다.
     */
    fun validate(value: T)

    /**
     * 주어진 값이 유효한지 boolean으로 반환합니다.
     */
    fun isValid(value: T): Boolean
}

/**
 * 공통 validation 유틸리티
 */
object CommonValidators {
    /**
     * 문자열이 비어있지 않은지 검증
     */
    fun validateNotBlank(value: String, fieldName: String) {
        if (value.isBlank()) {
            throw DomainValidationException("$fieldName must not be blank")
        }
    }

    /**
     * 문자열 길이가 범위 내에 있는지 검증
     */
    fun validateLength(value: String, fieldName: String, min: Int, max: Int) {
        if (value.length < min || value.length > max) {
            throw DomainValidationException("$fieldName length must be between $min and $max")
        }
    }

    /**
     * 숫자가 양수인지 검증
     */
    fun validatePositive(value: Number, fieldName: String) {
        if (value.toDouble() <= 0) {
            throw DomainValidationException("$fieldName must be positive")
        }
    }

    /**
     * 숫자가 0 이상인지 검증
     */
    fun validateNonNegative(value: Number, fieldName: String) {
        if (value.toDouble() < 0) {
            throw DomainValidationException("$fieldName must be non-negative")
        }
    }

    /**
     * 숫자가 범위 내에 있는지 검증
     */
    fun validateRange(value: Number, fieldName: String, min: Number, max: Number) {
        val doubleValue = value.toDouble()
        if (doubleValue < min.toDouble() || doubleValue > max.toDouble()) {
            throw DomainValidationException("$fieldName must be between $min and $max")
        }
    }

    /**
     * 이메일 형식 검증
     */
    fun validateEmail(email: String) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        if (!emailRegex.matches(email)) {
            throw DomainValidationException("Invalid email format")
        }
    }

    /**
     * Null이 아닌지 검증
     */
    fun <T> validateNotNull(value: T?, fieldName: String): T {
        return value ?: throw DomainValidationException("$fieldName must not be null")
    }
}
