package com.example.catalogservice.messagequeue

import com.example.catalogservice.jpa.CatalogRepository
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.modelmapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer(
    private val catalogRepository: CatalogRepository,
    private val modelMapper: ModelMapper
) {

    val log = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = ["example-catalog-topic"])
    fun updateQty(kafkaMessage: String) {
        log.info("Kafka Message: -> $kafkaMessage")

        var map = mapOf<Any, Any>()
        val mapper = ObjectMapper()
        try {
            map = mapper.readValue(kafkaMessage, object: TypeReference<Map<Any, Any>>() {})
        } catch (ex: JsonProcessingException) {
            ex.printStackTrace()
        }

        val catalogEntity = this.catalogRepository.findByProductId(map["productId"] as String)
            ?: throw RuntimeException()

        catalogEntity.stock = (catalogEntity.stock - map["qty"] as Int)
        this.catalogRepository.save(catalogEntity)
    }
}