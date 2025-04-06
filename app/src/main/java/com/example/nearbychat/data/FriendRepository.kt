package com.example.nearbychat.data

import com.example.nearbychat.model.Zhouqiong

class FriendRepository {
    fun getNearbyFriends(): List<Zhouqiong> {
        return listOf(
            Zhouqiong("liuxinyu", 39.9042, 116.4074, "https://example.com/avatar_liuxinyu.png"),
            Zhouqiong("zhouqiong", 39.9050, 116.4080, "https://example.com/avatar_zhouqiong.png")
        )
    }
}
