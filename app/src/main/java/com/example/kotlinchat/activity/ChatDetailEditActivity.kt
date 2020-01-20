package com.example.kotlinchat.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.kotlinchat.R

class ChatDetailEditActivity : AppCompatActivity() {

    val resultOK:Int =  1
    val resultFAIL:Int = 2

    lateinit var title: TextView
    lateinit var editInput: EditText
    lateinit var confirmBtn:Button
    lateinit var cancelBtn:Button
    val mContext: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail_edit)

        createInit()
    }

    private fun createInit(){

        val intent = this.intent
        title = findViewById(R.id.chat_detail_edit_title)
        editInput = findViewById(R.id.chat_detail_edit_context)
        confirmBtn = findViewById(R.id.chat_detail_edit_confirm_btn)
        cancelBtn = findViewById(R.id.chat_detail_edit_cancel_btn)

        // text 초기화
        title.text = "수정"

        // 수정할 내용 초기화
        val initContext:String = intent.getStringExtra("context")
        val initPos:Int = intent.getIntExtra("position", 0)
        editInput.text.clear()
        editInput.text.insert(0, initContext)

        // 버튼 초기화
        confirmBtn.setOnClickListener(){
            val intent:Intent = Intent()
            intent.putExtra("position", initPos)

            intent.putExtra("context", editInput.text.toString())
            setResult(resultOK, intent)
            finish()
        }

        cancelBtn.setOnClickListener(){
            val intent:Intent = Intent()
            intent.putExtra("position", initPos)
            setResult(resultFAIL, intent)
            finish()
        }
    }
}
