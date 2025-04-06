package com.example.nearbychat.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nearbychat.R
import com.example.nearbychat.data.ChatAdapter
import com.example.nearbychat.data.ChatMessage
import com.google.firebase.database.*
import com.vanniktech.emoji.EmojiPopup

class ChatActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var emojiButton: ImageButton
    private lateinit var emojiPopup: EmojiPopup
    private lateinit var database: DatabaseReference
    private val chatMessages = mutableListOf<ChatMessage>()
    private var friendId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // 获取好友 ID
        friendId = intent.getStringExtra("friend_id")
        title = "与 $friendId 聊天"

        // 初始化 UI
        recyclerView = findViewById(R.id.chat_recycler_view)
        messageInput = findViewById(R.id.chat_input)
        sendButton = findViewById(R.id.send_button)
        emojiButton = findViewById(R.id.emoji_button)

        // 初始化聊天适配器
        chatAdapter = ChatAdapter(chatMessages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter

        // 绑定 Firebase 聊天数据库
        database = FirebaseDatabase.getInstance().reference.child("chats").child(friendId!!)
        listenForMessages()

        // 发送消息
        sendButton.setOnClickListener { sendMessage() }

        // 初始化表情选择器
        emojiPopup = EmojiPopup.Builder.fromRootView(findViewById(R.id.chat_input_layout))
            .setOnEmojiPopupShownListener { emojiButton.setImageResource(R.drawable.ic_keyboard) }
            .setOnEmojiPopupDismissListener { emojiButton.setImageResource(R.drawable.ic_emoji) }
            .build(messageInput)

        emojiButton.setOnClickListener { emojiPopup.toggle() }
    }

    /**
     * 发送消息
     */
    private fun sendMessage() {
        val messageText = messageInput.text.toString().trim()
        if (messageText.isNotEmpty()) {
            val message = ChatMessage(messageText, true)  // ✅ 修正，删除 friendId!!
            database.push().setValue(message)
            messageInput.text.clear()
        }
    }

    /**
     * 监听 Firebase 消息变化
     */
    private fun listenForMessages() {
        database.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(ChatMessage::class.java)
                message?.let {
                    chatMessages.add(it)
                    chatAdapter.notifyItemInserted(chatMessages.size - 1)
                    recyclerView.scrollToPosition(chatMessages.size - 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        })
    }
}
