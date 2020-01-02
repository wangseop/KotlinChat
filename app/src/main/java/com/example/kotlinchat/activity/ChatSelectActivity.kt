package com.example.kotlinchat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.adapter.ChatSelectGroupAdapter
import com.example.kotlinchat.adapter.ChatUserGroupAdapter
import com.example.kotlinchat.data.chatbot.ChatbotSource
import com.example.kotlinchat.data.group.ChatUser

class ChatSelectActivity : AppCompatActivity() {

    private lateinit var chatUserGroupAdapter: ChatSelectGroupAdapter
    private lateinit var mSelectChat:ArrayList<ChatbotSource>
    private lateinit var recyclerView: RecyclerView
    private lateinit var nickname: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_select)
        getIntentExtra()
        createInit()


    }


    private fun getIntentExtra(){
//        var intent: Intent = this.intent
//        nickname = intent.getStringExtra("nick")
    }

    private fun createInit(){
        recyclerView = findViewById(R.id.recyclerview_chat_select)
        recyclerView.setHasFixedSize(true)

//        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(applicationContext)
//        linearLayoutManager.stackFromEnd = true
//        recyclerView.layoutManager = linearLayoutManager

        // 대화리스트 초기화
        mSelectChat = ArrayList<ChatbotSource>()

        // MessageAdapter 설정
        chatUserGroupAdapter = ChatSelectGroupAdapter(mSelectChat, this)

        // RecyclerView와 Adapter 연결
        recyclerView.adapter = chatUserGroupAdapter
    }
}
