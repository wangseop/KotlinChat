package com.example.kotlinchat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.adapter.ChatSelectGroupAdapter
import com.example.kotlinchat.adapter.PartialChatGroupAdapter
import com.example.kotlinchat.data.chatbot.ChatbotSource
import com.example.kotlinchat.data.chatbot.PartialChatBotSource
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class ChatDetailActivity : AppCompatActivity() {

    private lateinit var partialChatGroupAdapter: PartialChatGroupAdapter
    private lateinit var mPartialChat:ArrayList<PartialChatBotSource>
    private lateinit var recyclerView: RecyclerView
    private lateinit var nickname: String
    private lateinit var filePath:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail)

        val prevIntent:Intent = intent
        getIntentExtra()
        createInit()
        textDataInit()

    }


    private fun getIntentExtra(){
        var intent: Intent = this.intent
        filePath = intent.getStringExtra("path")
//        nickname = intent.getStringExtra("nick")
    }


    private fun createInit(){
        recyclerView = findViewById(R.id.recyclerview_chat_detail)
        recyclerView.setHasFixedSize(true)

//        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(applicationContext)
//        linearLayoutManager.stackFromEnd = true
//        recyclerView.layoutManager = linearLayoutManager

        // 대화리스트 초기화
        mPartialChat = ArrayList<PartialChatBotSource>()

        // MessageAdapter 설정
        partialChatGroupAdapter = PartialChatGroupAdapter(mPartialChat, this)

        // RecyclerView와 Adapter 연결
        recyclerView.adapter = partialChatGroupAdapter

    }
    private fun textDataInit(){
        lateinit var buf:BufferedReader
        var isTellQ:Boolean = false
        try {
            buf = BufferedReader(FileReader(filePath))
            val texts:List<String> = buf.readLines()
            for(text in texts){
                isTellQ = !isTellQ
                partialChatGroupAdapter.addPartialChatSource(PartialChatBotSource(isTellQ, text))
            }
//            while(buf.readLine() != null){
//                val text:String = buf.readLine()
//                isTellQ = !isTellQ
//                Log.d("KakaoTalk", text)
//                partialChatGroupAdapter.addPartialChatSource(PartialChatBotSource(isTellQ, text))
//
//            }


        } catch (e: IOException) {
            e.printStackTrace()
        }finally{
            buf.close()

        }
    }
}
