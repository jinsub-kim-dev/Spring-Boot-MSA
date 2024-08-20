package com.example.orderservice.dto

data class OrderDto(
    val productId: String = "",
    val qty: Int = 0,
    val unitPrice: Int = 0,
    var totalPrice: Int = 0,

    var orderId: String = "",
    var userId: String = ""
) {
}