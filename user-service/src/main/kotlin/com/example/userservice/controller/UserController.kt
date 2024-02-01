package com.example.userservice.controller

import com.example.userservice.dto.UserDto
import com.example.userservice.service.UserService
import com.example.userservice.vo.Greeting
import com.example.userservice.vo.RequestUser
import com.example.userservice.vo.ResponseUser
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class UserController(
        private val greeting: Greeting,
        private val userService: UserService,
        private val modelMapper: ModelMapper
) {

    @GetMapping("/health_check")
    public fun status(): String {
        return "It's Working in User Service."
    }

    @GetMapping("/welcome")
    public fun welcome(): String? {
        return greeting.message
    }

    @PostMapping("/users")
    fun createUser(@RequestBody user: RequestUser): ResponseEntity<ResponseUser> {
        var userDto = modelMapper.map(user, UserDto::class.java)

        this.userService.createUser(userDto)
        val responseUser = modelMapper.map(userDto, ResponseUser::class.java)

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser)
    }
}