package com.gijun.kotlinecommerce.application.port.input.user

import com.gijun.kotlinecommerce.application.dto.command.user.RegisterUserCommand
import com.gijun.kotlinecommerce.domain.user.model.UserModel

interface UserUseCase {
    fun registerUser(command: RegisterUserCommand): UserModel
    fun deleteUser(userId: String) : Boolean
    fun getUserByEmail(email: String) : UserModel?
    fun login(email: String, password: String) : UserModel?
}