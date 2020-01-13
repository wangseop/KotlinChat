package com.example.kotlinchat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.adapter.ChatSelectGroupAdapter
import com.example.kotlinchat.data.chatbot.ChatbotSource
import java.io.*

class ChatSelectActivity : AppCompatActivity() {

    private lateinit var chatSelectGroupAdapter: ChatSelectGroupAdapter
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
        var intent: Intent = this.intent
        nickname = intent.getStringExtra("nick")
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
        chatSelectGroupAdapter = ChatSelectGroupAdapter(mSelectChat, this, nickname)

        // RecyclerView와 Adapter 연결
        recyclerView.adapter = chatSelectGroupAdapter

        // 데이터 추가
        // 외부 저장소 경로 보는 방법
        // shift 2번 -> Device File Explorer 에서 sdcard/KaKaoTalk/Chats/ 참조
        // 혹은 mnt/sdcard/KaKaoTalk/Chats/
//        val folder: File = File(Environment.getExternalStorageDirectory().absolutePath +"/KakaoTalk/Chats/KakaoTalk_Chats_2020-01-02_03.28.38")

        val folderPath = "mnt/sdcard/KakaoTalk/Chats"
        val folder:File = File(folderPath)

        for(file in folder.list()){
            try {
                val realPath:String = folderPath + "/" +file + "/KakaoTalkChats.txt"
                val buf = BufferedReader(FileReader(realPath))
                val title:String = buf.readLine()
                Log.d("KakaoTalk", title)
                buf.close()

                chatSelectGroupAdapter.addChatbotSource(ChatbotSource(title, realPath))

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
//                // folder 이름 내 채팅 목록 폴더명
//                Log.d("File List", folder.list()[0])
//
//                try {
//                    val buf = BufferedReader(FileReader(folder.absolutePath + "/" + folder.list()[0]))
//                    val line:String = buf.readLine()
//                    Log.d("KakaoTalk", line)
//                    buf.close()
//                } catch (e: FileNotFoundException) {
//                    e.printStackTrace()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
    }
}
