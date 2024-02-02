package com.example.catalogservice.controller

import com.example.catalogservice.service.CatalogService
import com.example.catalogservice.vo.ResponseCatalog
import org.modelmapper.ModelMapper
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/catalog-service")
class CatalogController(
        private val environment: Environment,
        private val modelMapper: ModelMapper,
        private val catalogService: CatalogService
) {

    @GetMapping("/health_check")
    public fun status(): String {
        return "It's Working in Catalog Service on PORT ${environment.getProperty("local.server.port")}"
    }

    @GetMapping("/catalogs")
    fun getCatalogs(): ResponseEntity<List<ResponseCatalog>> {
        val catalogList = this.catalogService.getAllCatalogs()
        val result = catalogList.map { modelMapper.map(it, ResponseCatalog::class.java) }

        return ResponseEntity.status(HttpStatus.OK).body(result)
    }
}