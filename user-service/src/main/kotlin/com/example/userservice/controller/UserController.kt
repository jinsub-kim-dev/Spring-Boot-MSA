package com.example.userservice.controller

import com.example.userservice.dto.UserDto
import com.example.userservice.service.UserService
import com.example.userservice.vo.Greeting
import com.example.userservice.vo.RequestUser
import com.example.userservice.vo.ResponseUser
import io.micrometer.core.annotation.Timed
import org.modelmapper.ModelMapper
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class UserController(
        private val environment: Environment,
        private val greeting: Greeting,
        private val userService: UserService,
        private val modelMapper: ModelMapper,
) {

    @GetMapping("/health_check")
    @Timed(value = "user.status", longTask = true)
    public fun status(): String {
        return "It's Working in User Service" +
                " port(local.server.port)= ${environment.getProperty("local.server.port")}" +
                " port(server.port)= ${environment.getProperty("server.port")}" +
                " token secret= ${environment.getProperty("token.secret")}" +
                " token expiration time= ${environment.getProperty("token.expiration_time")}"
    }

    @GetMapping("/welcome")
    @Timed(value = "user.welcome", longTask = true)
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

    @GetMapping("/users")
    fun getUsers(): ResponseEntity<List<ResponseUser>> {
        val userList = this.userService.getUserByAll()
        val result = userList.map { modelMapper.map(it, ResponseUser::class.java) }

        return ResponseEntity.status(HttpStatus.OK).body(result)
    }

    @GetMapping("/users/{userId}")
    fun getUser(@PathVariable userId: String): ResponseEntity<ResponseUser> {
        val userDto = this.userService.getUserByUserId(userId)
        val result = modelMapper.map(userDto, ResponseUser::class.java)

        return ResponseEntity.status(HttpStatus.OK).body(result)
    }
}