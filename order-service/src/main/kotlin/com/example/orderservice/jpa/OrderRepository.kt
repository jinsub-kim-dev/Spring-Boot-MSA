package com.example.orderservice.jpa

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : CrudRepository<OrderEntity, Long> {
    fun findByOrderId(orderId: String): OrderEntity
    fun findByUserId(userId: String): Iterable<OrderEntity>
}