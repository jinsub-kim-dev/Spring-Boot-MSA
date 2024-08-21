package com.example.userservice.service

import com.example.userservice.client.OrderServiceClient
import com.example.userservice.dto.UserDto
import com.example.userservice.jpa.UserEntity
import com.example.userservice.jpa.UserRepository
import com.example.userservice.vo.ResponseOrder
import org.modelmapper.ModelMapper
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.UUID

@Service
class UserServiceImpl(
    private val env: Environment,
    private val userRepository: UserRepository,
    private val modelMapper: ModelMapper,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val restTemplate: RestTemplate,
    private val orderServiceClient: OrderServiceClient
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

        /* Without Communication */
//        val orders: MutableList<ResponseOrder> = mutableListOf()
//        userDto.orders = orders

        /* Using as RestTemplate */
//        val orderUrl = String.format(env.getProperty("order_service.url") ?: "", userId)
//        val orderListResponse = this.restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//            object: ParameterizedTypeReference<List<ResponseOrder>>() {})
//
//        val orderList = orderListResponse.body
//        userDto.orders = orderList!!

        /* Using as FeignClient */
        val orderList = this.orderServiceClient.getOrders(userId)
        userDto.orders = orderList

        return userDto
    }

    override fun getUserByAll(): Iterable<UserEntity> {
        return this.userRepository.findAll()
    }

    override fun getUserDetailsByEmail(username: String): UserDto {
        val userEntity = this.userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found")

        val userDto = ModelMapper().map(userEntity, UserDto::class.java)
        return userDto
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = this.userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found")

        return User(userEntity.email, userEntity.encryptedPwd,
            true, true, true, true, listOf())
    }
}