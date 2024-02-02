package com.example.catalogservice.service

import com.example.catalogservice.jpa.CatalogEntity
import com.example.catalogservice.jpa.CatalogRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CatalogServiceImpl(
        private val catalogRepository: CatalogRepository
) : CatalogService {

    val log = LoggerFactory.getLogger(this::class.java)

    override fun getAllCatalogs(): Iterable<CatalogEntity> {
        return this.catalogRepository.findAll()
    }
}