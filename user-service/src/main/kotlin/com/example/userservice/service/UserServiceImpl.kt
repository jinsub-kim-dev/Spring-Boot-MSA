package com.example.userservice.service

import com.example.userservice.dto.UserDto
import com.example.userservice.jpa.UserEntity
import com.example.userservice.jpa.UserRepository
import com.example.userservice.vo.ResponseOrder
import org.modelmapper.ModelMapper
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val modelMapper: ModelMapper,
        private val passwordEncoder: BCryptPasswordEncoder
) : UserService {

    override fun createUser(userDto: UserDto): UserDto {
        userDto.userId = UUID.randomUUID().toString()

        val userEntity = modelMapper.map(userDto, UserEntity::class.java)
        userEntity.encryptedPwd = passwordEncoder.encode(userDto.pwd)
        this.userRepository.save(userEntity)

        return modelMapper.map(userEntity, UserDto::class.java)
    }

    override fun getUserByUserId(userId: String): UserDto {
        val userEntity = this.userRepository.findByUserId(userId) ?: throw UsernameNotFoundException("User not found")
        val userDto = modelMapper.map(userEntity, UserDto::class.java)

        val orders: MutableList<ResponseOrder> = mutableListOf()
        userDto.orders = orders

        return userDto
    }

    override fun getUserByAll(): Iterable<UserEntity> {
        return this.userRepository.findAll()
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = this.userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found")

        return User(userEntity.email, userEntity.encryptedPwd,
            true, true, true, true, listOf())
    }
}