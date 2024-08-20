package com.example.orderservice.service

import com.example.orderservice.dto.OrderDto
import com.example.orderservice.jpa.OrderEntity
import com.example.orderservice.jpa.OrderRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val modelMapper: ModelMapper,
) : OrderService {

    override fun createOrder(orderDto: OrderDto): OrderDto {
        orderDto.orderId = UUID.randomUUID().toString()
        orderDto.totalPrice = orderDto.qty * orderDto.unitPrice

        val orderEntity = this.modelMapper.map(orderDto, OrderEntity::class.java)
        this.orderRepository.save(orderEntity)

        val resultOrderDto = this.modelMapper.map(orderEntity, OrderDto::class.java)
        return resultOrderDto
    }

    override fun getOrderByOrderId(orderId: String): OrderDto {
        val orderEntity = this.orderRepository.findByOrderId(orderId)
        val orderDto = this.modelMapper.map(orderEntity, OrderDto::class.java)
        return orderDto
    }

    override fun getOrdersByUserId(userId: String): Iterable<OrderEntity> {
        return this.orderRepository.findByUserId(userId)
    }
}