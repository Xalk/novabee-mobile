package com.xalk.novabee.models

data class ApiaryResponse(
    val __v: Int,
    val _id: String,
    val beehives: List<Any>,
    val createdAt: String,
    val description: String,
    val name: String,
    val startSeason: String,
    val updatedAt: String,
    val user: String
)