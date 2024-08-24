package com.example.orderservice.dto

data class Payload(
    val order_id: String = "",
    val user_id: String = "",
    val product_id: String = "",
    val qty: Int = 0,
    val unit_price: Int = 0,
    val total_price: Int = 0,
) {
}