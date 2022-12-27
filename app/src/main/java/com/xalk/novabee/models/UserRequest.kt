package com.xalk.novabee.models

data class UserRequest(
    val email: String,
    val fullName: String,
    val password: String
)