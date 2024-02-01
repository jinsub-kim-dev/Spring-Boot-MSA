package com.example.userservice.service

import com.example.userservice.dto.UserDto

interface UserService {
    fun createUser(userDto: UserDto): UserDto
}