package com.example.orderservice.vo

data class RequestOrder(
    val productId: String,
    val qty: Int,
    val unitPrice: Int
) {
}