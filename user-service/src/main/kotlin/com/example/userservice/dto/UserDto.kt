package com.example.userservice.dto

import java.util.Date

data class UserDto(
        val email: String = "",
        val name: String = "",
        var userId: String = "",
        val pwd: String = "",
        val createdAt: Date = Date(),
        val encryptedPwd: String = ""
) {
}