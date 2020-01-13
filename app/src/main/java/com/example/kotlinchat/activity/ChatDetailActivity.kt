package com.example.kotlinchat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
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

class ChatDetailActivity : AppCompatActivity(), View.OnClickListener  {

    private lateinit var partialChatGroupAdapter: PartialChatGroupAdapter
    private lateinit var mPartialChat:ArrayList<PartialChatBotSource>
    private lateinit var recyclerView: RecyclerView
    private lateinit var nickname: String
    private lateinit var filePath:String

    private lateinit var sendBtn: Button
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

        // view init
        sendBtn = findViewById(R.id.send_btn_chat_detail)

        // view listener 초기화
        sendBtn.setOnClickListener(this)

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
        var tellQ = false
        try {
            buf = BufferedReader(FileReader(filePath))
            val texts:List<String> = buf.readLines()
            val questioner = "회원님 : "
            val respondent:String = texts[0].substringBefore("님과").substring(1) + ": "
            var startIdx:Int = 0

            Log.d("respondent size", ""+respondent.length)
            Log.d("respondent before", texts[0].substringBefore("님과").substring(1))
            Log.d("respondent before", texts[0].substringBefore("님과").substring(0, texts[0].substringBefore("님과").length-1))
            Log.d("questioner", questioner)
            Log.d("respondent", respondent)

            // Q먼저 나오게 필요없는 부분 제거
            for(index in texts.indices){
                if(texts[index].contains(questioner)){
                    startIdx = index
                    break
                }
            }

            // qa 처리부분
            var textContext:String = ""
            for(text in texts.subList(startIdx, texts.size)){
                if(text.isBlank()) continue     // 공백문 제거

                var cutText:String = ""
                // 문자열 분류
                if(text.contains(questioner)){   // Q 부분
                    if (!tellQ){    // Q 첫 문장
                        if(!textContext.isBlank()) partialChatGroupAdapter.addPartialChatSource(PartialChatBotSource(tellQ, textContext))
                        tellQ = true
                        textContext = ""

                    }
                    cutText = text.substringAfter(questioner, "")
                }
                else if(text.contains(respondent)){   // A 부분
                    if(tellQ){  // A 첫 문장
                        if(!textContext.isBlank()) partialChatGroupAdapter.addPartialChatSource(PartialChatBotSource(tellQ, textContext))
                        tellQ = false
                        textContext = ""
                    }
                    cutText = text.substringAfter(respondent, "")
                }
                else{       // 그 외 부분
                    if((text.contains(" 오전 ") or text.contains(" 오후 ")) and text.substringBefore("년", "").isDigitsOnly())
                        continue
                    cutText = text
                }

                // 문장 접합 시 첫문장이 아닐경우 마침표 추가
                if(!textContext.isBlank()) cutText = ". $cutText"
                textContext += cutText

                Log.d("text Size", ""+textContext.length)

//                isTellQ = !isTellQ
            }
            // 끝문장 처리
            if(!tellQ and !textContext.isBlank()) partialChatGroupAdapter.addPartialChatSource(PartialChatBotSource(tellQ, textContext))


        } catch (e: IOException) {
            e.printStackTrace()
        }finally{
            buf.close()

        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.send_btn_chat_detail -> {
                Log.d("Button : ", "Chat Detail Send")
            }
        }
    }
}
