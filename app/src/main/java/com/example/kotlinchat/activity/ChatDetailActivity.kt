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
import org.w3c.dom.Text
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
        var isTellQ = false
        try {
            buf = BufferedReader(FileReader(filePath))
            val texts:List<String> = buf.readLines()
            val questioner = "회원님 : "
            val respondent:String = texts[0].substringBefore("님과").substring(1) + ": "
            Log.d("respondent size", ""+respondent.length)

            Log.d("respondent before", texts[0].substringBefore("님과").substring(1))
            Log.d("respondent before", texts[0].substringBefore("님과").substring(0, texts[0].substringBefore("님과").length-1))
            Log.d("questioner", questioner)
            Log.d("respondent", respondent)
            for(text in texts.subList(0, texts.size)){
                if(text.isBlank()) continue     // 공백문 제거
                if(text.contains(questioner)) isTellQ = true
                if(text.contains(respondent)) isTellQ = false

                Log.d("contains", ""+ text.contains(questioner) + " / " + text.contains(respondent) + " / " + isTellQ)

//                isTellQ = !isTellQ
                partialChatGroupAdapter.addPartialChatSource(PartialChatBotSource(isTellQ, text))
            }


        } catch (e: IOException) {
            e.printStackTrace()
        }finally{
            buf.close()

        }
    }
}
