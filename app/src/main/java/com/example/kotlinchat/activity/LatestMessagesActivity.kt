package com.example.kotlinchat.activity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.adapter.ChatUserGroupAdapter
import com.example.kotlinchat.data.group.ChatUser
import com.example.kotlinchat.network.NetworkBotCloseTask

class LatestMessagesActivity : AppCompatActivity() {

    private val chatSelectRequest: Int = 1
    private val chatBotRequest: Int = 2
    private val chatSelectResult: Int = 1
    private lateinit var chatUserGroupAdapter: ChatUserGroupAdapter
    private lateinit var mUser: ArrayList<ChatUser>
    private lateinit var recyclerView: RecyclerView
    private lateinit var nickname: String
    private lateinit var id: String
    lateinit var avatars: Array<String>
    lateinit var chats: HashMap<String, Array<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)
        getIntentExtra()
        createInit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("onActivityResult", "exit " + requestCode)
        if (requestCode == chatSelectRequest) {        // chatSelectAcitivity 종료시
            val intent: Intent = data as Intent

            var otherName: String = "아린"
            if (resultCode == chatSelectResult) {

                otherName = intent.getStringExtra("otherName")
            }

            chatUserGroupAdapter.addChatUser(
                ChatUser(
                    true,
                    R.drawable.default_profile,
                    otherName,
                    ""
                )
            )
        }
        if (requestCode == chatBotRequest) {          // chatBotActivity 종료시

//            val intent: Intent = data as Intent
//            val lateChat: Array<String> = intent.getStringArrayExtra("lateChat")
//            val indexName: String = intent.getStringExtra("indexName")
//
//            // 추가된 채팅 내용 업데이트(클라이언트 프로그램에)
//            (chats[indexName] as Array<String>).plus(lateChat)
        }
    }

    private fun getIntentExtra() {
        var intent: Intent = this.intent
        nickname = intent.getStringExtra("nick")
        id = intent.getStringExtra("id")
        avatars = intent.getStringArrayExtra("avatars")
        chats = hashMapOf()

        for (avatar in avatars) {
            chats[avatar] = intent.getStringArrayExtra(avatar) as Array<String>
            val size: Int = (chats[avatar] as Array<String>).size
            Log.d("Avatar Chat Size", "$size")

            for (i in chats[avatar] as Array<String>) {
                Log.d("Avatar Chat Context", i)

            }
        }
    }

    private fun createInit() {
        recyclerView = findViewById(R.id.recyclerview_latest_message)
        recyclerView.setHasFixedSize(true)

//        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(applicationContext)
//        linearLayoutManager.stackFromEnd = true
//        recyclerView.layoutManager = linearLayoutManager

        // 대화리스트 초기화
        mUser = ArrayList<ChatUser>()

        // MessageAdapter 설정
        chatUserGroupAdapter = ChatUserGroupAdapter(mUser, chats, this, nickname, id)

        // RecyclerView와 Adapter 연결
        recyclerView.adapter = chatUserGroupAdapter

        // 챗봇 목록 있을 경우 추가
        for (i in 0 until (avatars.size))
        //
            chatUserGroupAdapter.addChatUser(
                ChatUser(
                    true,
                    R.drawable.default_profile,
                    avatars[i],
                    ""
                )
            )
    }
}

