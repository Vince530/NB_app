package com.example.nearbychat.ui

import android.app.AlertDialog
import android.content.Context

class FriendOptionsDialog(
    private val context: Context,
    private val friendId: String,
    private val callback: (String) -> Unit
) {
    fun show() {
        AlertDialog.Builder(context)
            .setTitle("选择操作")
            .setItems(arrayOf("聊天", "提醒")) { _, which ->
                val option = if (which == 0) "聊天" else "提醒"
                callback(option)
            }
            .setNegativeButton("取消", null)
            .show()
    }
}
