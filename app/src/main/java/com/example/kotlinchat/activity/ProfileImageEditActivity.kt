package com.example.kotlinchat.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinchat.R


class ProfileImageEditActivity : AppCompatActivity() {
    val REQUEST_CODE:Int = 0
    val resultOK:Int =  1
    val resultFAIL:Int = 2

    lateinit var title: TextView
    lateinit var editImage: ImageView
    lateinit var confirmBtn: Button
    lateinit var cancelBtn: Button
    val mContext: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_image_edit)

        createInit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun createInit(){

        val intent = this.intent
        title = findViewById(R.id.profile_image_edit_title)
        editImage = findViewById(R.id.profile_image_edit_context)
        confirmBtn = findViewById(R.id.profile_image_edit_confirm_btn)
        cancelBtn = findViewById(R.id.profile_image_edit_cancel_btn)

        // text 초기화
        title.text = "수정"

        // 이미지 선택 시 이미지 선택창 뜨게

        editImage.setOnClickListener(){
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, REQUEST_CODE)
        }


        // 버튼 초기화
        confirmBtn.setOnClickListener(){
            val intent: Intent = Intent()

            setResult(resultOK, intent)
            finish()
        }

        cancelBtn.setOnClickListener(){
            val intent: Intent = Intent()
            setResult(resultFAIL, intent)
            finish()
        }
    }
}
