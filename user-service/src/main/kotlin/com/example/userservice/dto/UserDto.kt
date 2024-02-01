package com.example.userservice.dto

import com.example.userservice.vo.ResponseOrder
import java.util.Date

data class UserDto(
        val email: String = "",
        val name: String = "",
        var userId: String = "",
        val pwd: String = "",
        val createdAt: Date = Date(),
        val encryptedPwd: String = "",

        var orders: List<ResponseOrder> = listOf()
) {
}