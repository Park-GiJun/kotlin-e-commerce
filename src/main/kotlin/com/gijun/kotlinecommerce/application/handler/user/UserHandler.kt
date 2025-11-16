package com.gijun.kotlinecommerce.application.handler.user

import com.gijun.kotlinecommerce.application.dto.command.user.RegisterUserCommand
import com.gijun.kotlinecommerce.application.port.input.user.UserUseCase
import com.gijun.kotlinecommerce.application.port.output.persistence.user.UserJpaPort
import com.gijun.kotlinecommerce.domain.common.PageRequest
import com.gijun.kotlinecommerce.domain.common.PageResponse
import com.gijun.kotlinecommerce.domain.common.validator.CommonValidators
import com.gijun.kotlinecommerce.domain.user.exception.UserAlreadyExistsException
import com.gijun.kotlinecommerce.domain.user.exception.UserNotFoundException
import com.gijun.kotlinecommerce.domain.user.exception.UserValidationException
import com.gijun.kotlinecommerce.domain.user.model.UserModel
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserHandler(
    private val userJpaPort: UserJpaPort,
    private val passwordEncoder: PasswordEncoder
) : UserUseCase {

    override fun registerUser(command: RegisterUserCommand): UserModel {
        validateForRegister(command.email, command.name, command.password)
        validateUserNotExists(command.email)

        val encodedPassword = passwordEncoder.encode(command.password)
        val newUser = UserModel.create(command.email, command.name, encodedPassword, command.role)
        return userJpaPort.save(newUser)
    }

    override fun deleteUser(userId: String): Boolean {
        val user = validateUserExists(userId)
        userJpaPort.delete(user)
        return true
    }

    override fun getUserByEmail(email: String): UserModel? {
        return userJpaPort.findByUserId(email)
    }

    override fun login(
        email: String,
        password: String
    ): UserModel? {
        val user = userJpaPort.findByUserId(email) ?: return null

        return if (passwordEncoder.matches(password, user.password)) {
            user
        } else {
            null
        }
    }

    override fun getAllUsers(pageRequest: PageRequest): PageResponse<UserModel> {
        return userJpaPort.findAll(pageRequest)
    }

    private fun validateUserExists(userId: String): UserModel {
        return userJpaPort.findByUserId(userId)
            ?: throw UserNotFoundException(userId)
    }

    private fun validateUserNotExists(userId: String) {
        userJpaPort.findByUserId(userId)?.let {
            throw UserAlreadyExistsException(userId)
        }
    }

    private fun validateForRegister(userId: String, name: String, password: String) {
        try {
            CommonValidators.validateNotBlank(userId, "User ID")
            CommonValidators.validateEmail(userId)
            CommonValidators.validateNotBlank(name, "Name")
            CommonValidators.validateLength(name, "Name", NAME_MIN_LENGTH, NAME_MAX_LENGTH)
            CommonValidators.validateNotBlank(password, "Password")
            CommonValidators.validateLength(password, "Password", PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH)
        } catch (e: Exception) {
            throw UserValidationException(e.message ?: "User validation failed", e)
        }
    }

    companion object {
        private const val NAME_MIN_LENGTH = 1
        private const val NAME_MAX_LENGTH = 100
        private const val PASSWORD_MIN_LENGTH = 8
        private const val PASSWORD_MAX_LENGTH = 100
    }
}