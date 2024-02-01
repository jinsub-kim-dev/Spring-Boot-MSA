package com.example.userservice.jpa

import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
        email: String = "",
        name: String = "",
        userId: String = "",
        encryptedPwd: String = ""
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, unique = true, length = 50)
    var email: String = email

    @Column(nullable = false, length = 50)
    var name: String = name

    @Column(nullable = false, unique = true)
    var userId: String = userId

    @Column(nullable = false, unique = true)
    var encryptedPwd: String = encryptedPwd
}