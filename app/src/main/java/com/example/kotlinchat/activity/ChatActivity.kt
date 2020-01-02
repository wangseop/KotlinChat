package com.example.kotlinchat.activity

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.adapter.MessageAdapter
import com.example.kotlinchat.data.chat.Chat
import com.example.kotlinchat.network.NetworkTask
import com.example.kotlinchat.network.RequestHttpURLConnection
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

class ChatActivity : AppCompatActivity(), View.OnClickListener {

    private val MSG_LEFT:Int = 1
    private val MSG_RIGHT:Int = 2

    private var nick: String = "nick"
    private var cookie: String = ""
    private lateinit var button_attach: ImageButton
    private lateinit var editText_chat: EditText
    private lateinit var button_send: ImageButton
    private lateinit var selectImgLayout: RelativeLayout
    private lateinit var mapActionbar: Toolbar

    private val welcomePath: String = "http://3.15.44.44:5000/"
    private val messagePath: String = "http://3.15.44.44:5000/msg"
    private val messageImgPath: String = "http://3.15.44.44:5000/img"
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var mchat:ArrayList<Chat>
    private lateinit var recyclerView:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var intent: Intent = this.intent
        nick = intent.getStringExtra("nick")
//        // 액션바
//        mapActionbar = (Toolbar)findViewById(R.id.chat_toolbar);
//
//        setSupportActionBar(mapActionbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Trigo");
//        // 액션바 뒤로가기 버튼 추가
//        actionBar.setDisplayHomeAsUpEnabled(true);
//
//        // Manifest에 설정할 권한 부여
//        ActivityCompat.requestPermissions(ChatActivity.this, String[]{Manifest.permission.INTERNET}, 2);
//        ActivityCompat.requestPermissions(ChatActivity.this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        // 문장 입력 바
        editText_chat = findViewById(R.id.editText_chat)

        // button 리스너 추가
        button_send = findViewById(R.id.button_send)

        button_send.setOnClickListener(this)

        recyclerView = findViewById(R.id.my_recycler_view)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager

        // 대화리스트 초기화
        mchat = ArrayList<Chat>()

        // MessageAdapter 설정
        messageAdapter = MessageAdapter(mchat, this)

        // RecyclerView와 Adapter 연결
        recyclerView.adapter = messageAdapter
    }

    override fun onClick(v: View) {
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein)

        val id:Int = v.id

        if (id == R.id.button_send){
            sendMessage()
        }
    }

    private fun sendMessage(){
        val msg:String = editText_chat.text.toString()
        Log.d("MSG", msg)
        if (msg != null){

            // 내가 입력한 메세지 전송
            val chat:Chat = Chat(MSG_RIGHT, nick, "Trigobot", msg)

            messageAdapter.addChat(chat)
            recyclerView.smoothScrollToPosition(messageAdapter.itemCount - 1)

            val contentValues: ContentValues = ContentValues()
            contentValues.put("name", nick)
            contentValues.put("msg", msg)
//            contentValues.put("cookie", cookie);

            editText_chat.setText("")

            val networkTask: NetworkTask = NetworkTask(messageAdapter, recyclerView, messagePath, contentValues)
            networkTask.execute()
        }
    }

}
