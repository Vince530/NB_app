package com.example.nearbychat.model

data class Zhouqiong(
    val id: String,          // 用户 ID
    val latitude: Double,    // 纬度
    val longitude: Double,   // 经度
    val avatarUrl: String?   // 头像 URL
) {
    fun hasAvatar(): Boolean {
        return avatarUrl != null && avatarUrl.isNotEmpty()
    }
}
