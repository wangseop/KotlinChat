package com.example.kotlinchat.activity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.adapter.MessageAdapter
import com.example.kotlinchat.data.message.ChatMessage
import com.example.kotlinchat.network.AvatarChatTask
import com.example.kotlinchat.network.NetworkBotCloseTask
import com.example.kotlinchat.network.NetworkTask


class ChatBotActivity : AppCompatActivity(), View.OnClickListener {

    val MSG_LEFT:Int = 1
    val MSG_RIGHT:Int = 2
    private val REQUEST_IMG_EDIT:Int = 1
    val RESPONSE_CLOSE_BOT:Int = 2
    private var id: String = "nick"
    private var nick: String = "유승효"
    private lateinit var indexName: String

    lateinit var chat: Array<String>
    private lateinit var lateChat: Array<String>
    private var cookie: String = ""
    private lateinit var button_attach: ImageButton
    private lateinit var editText_chat: EditText
    private lateinit var button_send: ImageButton
    private lateinit var selectImgLayout: RelativeLayout
//    private lateinit var mapActionbar: Toolbar

    private val welcomePath: String = "http://3.15.44.44:5000/"
    private val messagePath: String = "http://3.15.44.44:5000/msg"
    private val messageImgPath: String = "http://3.15.44.44:5000/img"
    private val botClosePath: String = "http://3.15.44.44:5000/botclose"
    private val loadChatPath:String = "http://3.15.44.44:5000/loadchats"

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var mchat:ArrayList<ChatMessage>
    private lateinit var recyclerView:RecyclerView

    private fun createInit(){
        var intent: Intent = this.intent
        id = intent.getStringExtra("id")
        nick = intent.getStringExtra("nick")
        indexName = intent.getStringExtra("indexName") as String

        // 현재 챗봇 activity 종료 시 채팅 방에 추가될 채팅 내용 담는 array
//        lateChat = arrayOf<String>()

        Log.d("ChatbotActivity", "onCreate : (indexName : $indexName)")

        // 액션바
        setSupportActionBar(findViewById(R.id.chat_toolbar))
        val toolbar:ActionBar = supportActionBar as ActionBar
        toolbar.title = indexName

        // 액션바 뒤로가기 버튼 추가
        toolbar.setDisplayHomeAsUpEnabled(true)
        // 액션바 뒤로가기 버튼 추가

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
        mchat = ArrayList<ChatMessage>()

        // MessageAdapter 설정
        messageAdapter = MessageAdapter(mchat, this)

        // RecyclerView와 Adapter 연결
        recyclerView.adapter = messageAdapter

        val contentValues: ContentValues = ContentValues()
        contentValues.put("id", id)
        contentValues.put("nick", nick)
        contentValues.put("indexName", indexName.replace(" ", ""))
//            contentValues.put("cookie", cookie);

        editText_chat.setText("")

        val avatarChatTask: AvatarChatTask = AvatarChatTask(this, messageAdapter, loadChatPath, nick, indexName.replace(" ", ""), contentValues)
        avatarChatTask.execute()



//        for (i in chat){
//            Log.d("Chatting $nick with $indexName", i)
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot)

        createInit()


    }

    override fun onBackPressed() {  // 뒤로가기 터치 시
        super.onBackPressed()
        Log.d("onBackPressed", "ChatbotActivity")
//        val intent = Intent()
//        intent.putExtra("indexName", indexName)
//        Log.d("NetworkBotCloseTask", "onPostExecute")
//
//        this.setResult(this.RESPONSE_CLOSE_BOT, intent)
//        this.finish()
//        val contentValues: ContentValues = ContentValues()
//        contentValues.put("id", id)
//        contentValues.put("nick", nick)
////            contentValues.put("cookie", cookie);
//
//
//        val networkBotCloseTask: NetworkBotCloseTask = NetworkBotCloseTask(this, botClosePath, contentValues)
//        networkBotCloseTask.execute()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
    }

    // 액션바에 메뉴 인플레이팅
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.activity_chatbot_menu, menu)
        return true
    }

    // 액션바 옵션 별 기능
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home->{        // 액션바에서 뒤로가기 선탵ㄱ 시
//            val intent = Intent()
//            intent.putExtra("lateChat", lateChat)
//            intent.putExtra("indexName", indexName)
//            Log.d("NetworkBotCloseTask", "onPostExecute")
//
//            this.setResult(this.RESPONSE_CLOSE_BOT, intent)
            finish()
            true
        }
        R.id.action_profile_change -> {
            val intent:Intent = Intent(this, ProfileImageEditActivity::class.java)
            this.startActivityForResult(intent, REQUEST_IMG_EDIT)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
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
            val chatMessage:ChatMessage = ChatMessage(MSG_RIGHT, nick, indexName, msg)
//            lateChat.plus("$msg // right")
            Log.d("Right Message", chatMessage.toString())
            messageAdapter.addChat(chatMessage)
            recyclerView.smoothScrollToPosition(messageAdapter.itemCount - 1)

            val contentValues: ContentValues = ContentValues()
            contentValues.put("id", id)
            contentValues.put("name", nick)
            contentValues.put("msg", msg)
            contentValues.put("indexName", indexName.replace(" ", ""))
//            contentValues.put("cookie", cookie);

            editText_chat.setText("")

            val networkTask: NetworkTask = NetworkTask(messageAdapter, recyclerView, messagePath, contentValues)
            networkTask.execute()
        }
    }

}
