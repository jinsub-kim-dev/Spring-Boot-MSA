package com.example.userservice.security

import com.example.userservice.service.UserService
import com.example.userservice.vo.RequestLogin
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.core.env.Environment
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.Date
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    private val userService: UserService,
    private val env: Environment
) : UsernamePasswordAuthenticationFilter() {



    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {

        try {
            val creds: RequestLogin = ObjectMapper().readValue(request.inputStream, RequestLogin::class.java)

            return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    creds.email,
                    creds.password,
                    listOf()
                )
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val username = (authResult.principal as User).username
        val userDetails = this.userService.getUserDetailsByEmail(username)

        val token = Jwts.builder()
            .setSubject(userDetails.userId)
            .setExpiration(Date(System.currentTimeMillis() + (env.getProperty("token.expiration_time")?.toLong() ?: 0)))
            .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
            .compact()

        response.addHeader("token", token)
        response.addHeader("userId", userDetails.userId)
    }
}