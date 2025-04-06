package com.example.nearbychat.data

data class User(
    val id: String,
    val name: String,
    var avatarUrl: String = "", // 默认空，表示使用默认头像
    val latitude: Double,
    val longitude: Double
)
