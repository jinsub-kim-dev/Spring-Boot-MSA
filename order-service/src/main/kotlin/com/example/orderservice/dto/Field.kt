package com.example.orderservice.dto

data class Field(
    val type: String = "",
    val optional: Boolean = false,
    val field: String = ""
) {
}