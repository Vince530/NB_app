package com.example.nearbychat.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.nearbychat.R

class ProfileActivity : AppCompatActivity() {
    private lateinit var avatarImageView: ImageView
    private lateinit var changeAvatarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        avatarImageView = findViewById(R.id.avatarImageView)
        changeAvatarButton = findViewById(R.id.changeAvatarButton)

        changeAvatarButton.setOnClickListener { openImagePicker() }
    }

    // 使用 registerForActivityResult 代替 startActivityForResult
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            avatarImageView.setImageURI(imageUri)
        }
    }

    fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }
}
