package com.example.userservice.vo

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class RequestUser(
        @NotNull(message = "Email cannot be null")
        @Size(min = 2, message = "Email not be less than two characters")
        val email: String,

        @NotNull(message = "Name cannot be null")
        @Size(min = 2, message = "Name not be less than two characters")
        val name: String,

        @NotNull(message = "Password cannot be null")
        @Size(min = 8, message = "Password not be less than eight characters")
        val pwd: String,
) {
}