package com.example.catalogservice.vo

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.Date

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseCatalog(
        val productId: String = "",
        val productName: String = "",
        val unitPrice: Int = 0,
        val stock: Int = 0,
        val createdAt: Date = Date()
) {
}