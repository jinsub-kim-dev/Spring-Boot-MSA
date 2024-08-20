package com.example.orderservice.jpa

import org.hibernate.annotations.ColumnDefault
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "orders")
class OrderEntity(
    productId: String = "",
    qty: Int = 0,
    unitPrice: Int = 0,
    totalPrice: Int = 0,
    userId: String = "",
    orderId: String = ""
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, length = 120)
    var productId: String = productId

    @Column(nullable = false)
    var qty: Int = qty

    @Column(nullable = false)
    var unitPrice: Int = unitPrice

    @Column(nullable = false)
    var totalPrice: Int = totalPrice

    @Column(nullable = false)
    var userId: String = userId

    @Column(nullable = false, unique = true)
    var orderId: String = orderId

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private var createdAt: Date = Date()
}