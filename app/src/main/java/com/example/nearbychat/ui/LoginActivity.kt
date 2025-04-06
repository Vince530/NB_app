package com.example.nearbychat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nearbychat.MainActivity
import com.example.nearbychat.R

class LoginActivity : AppCompatActivity() {

    private val users = mapOf(
        "liuxinyu" to "12345678",
        "zhouqiong" to "12345678"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)

        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val loggedInUser = sharedPreferences.getString("logged_in_user", null)

        // **检查是否已登录，避免重复输入**
        if (loggedInUser != null) {
            navigateToMain()
            return
        }

        btnLogin.setOnClickListener {
            val inputUsername = etUsername.text.toString().trim()
            val inputPassword = etPassword.text.toString().trim()

            if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (users[inputUsername] == inputPassword) {
                sharedPreferences.edit()
                    .putString("logged_in_user", inputUsername)
                    .apply()

                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                navigateToMain()
            } else {
                Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // **关闭当前登录页面**
    }
}
