package com.example.kotlinchat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.kotlinchat.R

class ChatDetailActivity : AppCompatActivity() {

    lateinit var text: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail)

        val prevIntent:Intent = intent
        text = findViewById(R.id.textView_test)

        text.setText(prevIntent.getStringExtra("path"))
    }
}
