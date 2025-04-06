package com.example.nearbychat.data

data class ChatMessage(
    val text: String,          // 消息文本内容
    val isSentByUser: Boolean  // 是否是用户发送的消息
)
