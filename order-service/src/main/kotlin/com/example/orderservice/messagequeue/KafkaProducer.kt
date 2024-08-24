package com.example.orderservice.messagequeue

import com.example.orderservice.dto.OrderDto
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    val log = LoggerFactory.getLogger(this::class.java)

    fun send(topic: String, orderDto: OrderDto): OrderDto {
        val mapper = ObjectMapper()
        var jsonInString = ""
        try {
            jsonInString = mapper.writeValueAsString(orderDto)
        } catch (ex: JsonProcessingException) {
            ex.printStackTrace()
        }

        this.kafkaTemplate.send(topic, jsonInString)
        log.info("Kafka Producer send data from Order microservice: $orderDto")

        return orderDto
    }
}