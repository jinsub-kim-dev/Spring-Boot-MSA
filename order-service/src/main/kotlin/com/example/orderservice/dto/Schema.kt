package com.example.orderservice.dto

data class Schema(
    val type: String = "",
    val fields: List<Field> = listOf(),
    val optional: Boolean = false,
    val name: String = ""
) {
}