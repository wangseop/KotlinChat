package com.example.kotlinchat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.adapter.ChatUserGroupAdapter
import com.example.kotlinchat.data.group.ChatUser

class LatestMessagesActivity : AppCompatActivity() {

    private val chatSelectRequest:Int = 1
    private val chatSelectResult:Int = 1
    private lateinit var chatUserGroupAdapter: ChatUserGroupAdapter
    private lateinit var mUser:ArrayList<ChatUser>
    private lateinit var recyclerView: RecyclerView
    private lateinit var nickname: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)
        getIntentExtra()
        createInit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("onActivityResult", "exit " + requestCode)

        if(requestCode == chatSelectRequest){        // chatSelectAcitivity 종료시
            var otherName:String = "아린"
            if(resultCode == chatSelectResult)
            {
                val intent:Intent = data as Intent
                otherName = intent.getStringExtra("otherName")
            }

            chatUserGroupAdapter.addChatUser(ChatUser(true, R.drawable.crop_girl, otherName, ""))
        }
    }

    private fun getIntentExtra(){
        var intent: Intent = this.intent
        nickname = intent.getStringExtra("nick")
    }

    private fun createInit(){
        recyclerView = findViewById(R.id.recyclerview_latest_message)
        recyclerView.setHasFixedSize(true)

//        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(applicationContext)
//        linearLayoutManager.stackFromEnd = true
//        recyclerView.layoutManager = linearLayoutManager

        // 대화리스트 초기화
        mUser = ArrayList<ChatUser>()

        // MessageAdapter 설정
        chatUserGroupAdapter = ChatUserGroupAdapter(mUser, this, nickname)

        // RecyclerView와 Adapter 연결
        recyclerView.adapter = chatUserGroupAdapter
    }
}

