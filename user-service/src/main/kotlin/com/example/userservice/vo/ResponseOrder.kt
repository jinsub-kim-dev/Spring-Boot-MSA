package com.example.userservice.vo

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.Date

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseOrder(
        val productId: String = "",
        val qty: Int = 0,
        val unitPrice: Int = 0,
        val totalPrice: Int = 0,
        val createdAt: Date = Date(),

        val orderId: String = ""
) {
}