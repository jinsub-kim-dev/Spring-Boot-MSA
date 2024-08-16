package com.example.apigatewayservice.filter

import io.jsonwebtoken.Jwts
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthorizationHeaderFilter(
    private val env: Environment
) : AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>(Config::class.java) {

    val log = LoggerFactory.getLogger(this::class.java)

    class Config {

    }

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request

            if (!request.headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return@GatewayFilter onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED)
            }

            val authorizationHeader = request.headers[HttpHeaders.AUTHORIZATION]?.get(0)
            val jwt = authorizationHeader?.replace("Bearer ", "") ?: ""

            if (!isJwtValid(jwt)) {
                return@GatewayFilter onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED)
            }

            chain.filter(exchange)
        }
    }

    private fun isJwtValid(jwt: String): Boolean {
        var returnValue = true

        try {
            val subject = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                .parseClaimsJws(jwt).body.subject

            if (subject.isEmpty()) {
                returnValue = false
            }
        } catch (ex: Exception) {
            returnValue = false
        }

        return returnValue
    }

    private fun onError(exchange: ServerWebExchange, err: String, httpStatus: HttpStatus): Mono<Void> {
        val response = exchange.response
        response.statusCode = httpStatus

        log.error(err)
        return response.setComplete()
    }
}