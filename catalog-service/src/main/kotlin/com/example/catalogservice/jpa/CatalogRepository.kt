package com.example.catalogservice.jpa

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CatalogRepository : CrudRepository<CatalogEntity, Long> {

    fun findByProductId(productId: String): CatalogEntity?
}