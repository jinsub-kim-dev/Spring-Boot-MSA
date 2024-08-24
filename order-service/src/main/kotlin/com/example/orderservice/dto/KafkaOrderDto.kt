package com.example.orderservice.dto

import java.io.Serializable

data class KafkaOrderDto(
    val schema: Schema,
    val payload: Payload
) {
}