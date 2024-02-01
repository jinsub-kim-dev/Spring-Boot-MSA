package com.example.userservice.service

import com.example.userservice.dto.UserDto
import com.example.userservice.jpa.UserEntity
import com.example.userservice.jpa.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val modelMapper: ModelMapper
) : UserService {

    override fun createUser(userDto: UserDto): UserDto {
        userDto.userId = UUID.randomUUID().toString()

        val userEntity = modelMapper.map(userDto, UserEntity::class.java)
        userEntity.encryptedPwd = "encrypted_password"
        this.userRepository.save(userEntity)

        return modelMapper.map(userEntity, UserDto::class.java)
    }
}