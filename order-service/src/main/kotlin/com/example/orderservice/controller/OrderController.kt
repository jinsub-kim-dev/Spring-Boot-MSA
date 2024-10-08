package com.example.orderservice.controller

import com.example.orderservice.dto.OrderDto
import com.example.orderservice.messagequeue.KafkaProducer
import com.example.orderservice.messagequeue.OrderProducer
import com.example.orderservice.service.OrderService
import com.example.orderservice.vo.RequestOrder
import com.example.orderservice.vo.ResponseOrder
import org.modelmapper.ModelMapper
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/order-service")
class OrderController(
    private val env: Environment,
    private val orderService: OrderService,
    private val modelMapper: ModelMapper,
    private val kafkaProducer: KafkaProducer,
    private val orderProducer: OrderProducer
) {

    @GetMapping("/health_check")
    fun status(): String {
        return "It's Working in Order Service on PORT ${env.getProperty("local.server.port")}"
    }

    @PostMapping("/{userId}/orders")
    fun createOrder(@PathVariable("userId") userId: String,
                    @RequestBody orderDetails: RequestOrder): ResponseEntity<ResponseOrder> {
        val orderDto = this.modelMapper.map(orderDetails, OrderDto::class.java)
        orderDto.userId = userId

        /* jpa */
//        val createdOrder = this.orderService.createOrder(orderDto)
//        val responseOrder = this.modelMapper.map(createdOrder, ResponseOrder::class.java)

        /* kafka */
        orderDto.orderId = UUID.randomUUID().toString()
        orderDto.totalPrice = orderDetails.qty * orderDetails.unitPrice

        /* send this order to kafka */
        this.kafkaProducer.send("example-catalog-topic", orderDto)
        this.orderProducer.send("orders", orderDto)

        val responseOrder = this.modelMapper.map(orderDto, ResponseOrder::class.java)

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder)
    }

    @GetMapping("/{userId}/orders")
    fun getOrder(@PathVariable("userId") userId: String): ResponseEntity<List<ResponseOrder>> {
        val orderList = this.orderService.getOrdersByUserId(userId)
        val result = orderList.map { v -> this.modelMapper.map(v, ResponseOrder::class.java) }

        return ResponseEntity.status(HttpStatus.OK).body(result)
    }
}