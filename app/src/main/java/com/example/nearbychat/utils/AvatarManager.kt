package com.example.nearbychat.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream

object AvatarManager {
    private const val AVATAR_FILE_NAME = "user_avatar.png"

    // 保存头像到本地存储
    fun saveAvatar(context: Context, bitmap: Bitmap) {
        val file = File(context.filesDir, AVATAR_FILE_NAME)
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
    }

    // 加载头像
    fun loadAvatar(context: Context): Bitmap? {
        val file = File(context.filesDir, AVATAR_FILE_NAME)
        return if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
    }

    // 获取头像路径
    fun getAvatarPath(context: Context): String {
        return File(context.filesDir, AVATAR_FILE_NAME).absolutePath
    }
}
