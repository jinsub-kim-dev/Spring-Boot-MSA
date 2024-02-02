package com.example.catalogservice.jpa

import org.hibernate.annotations.ColumnDefault
import java.io.Serializable
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "catalog")
class CatalogEntity(
        productId: String,
        productName: String,
        stock: Int,
        unitPrice: Int,
) : Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "product_id", nullable = false)
    var productId: String = productId

    @Column(name = "product_name", nullable = false)
    var productName: String = productName

    @Column(name = "stock", nullable = false)
    var stock: Int = stock

    @Column(name = "unit_price", nullable = false)
    var unitPrice: Int = unitPrice

    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    var createdAt: Date = Date()
}