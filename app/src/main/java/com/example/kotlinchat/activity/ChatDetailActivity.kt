package com.example.kotlinchat.activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.adapter.PartialChatGroupAdapter
import com.example.kotlinchat.controller.SwipeController
import com.example.kotlinchat.controller.SwipeControllerActions
import com.example.kotlinchat.data.chatbot.PartialChatBotSource
import com.example.kotlinchat.network.CreateBotNetworkTask
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


class ChatDetailActivity : AppCompatActivity(), View.OnClickListener{

    val requestEdit:Int = 0
    val resultOK:Int =  1
    val resultFAIL:Int = 2

    var chatDetailResult:Int = 0

    private lateinit var partialChatGroupAdapter: PartialChatGroupAdapter
    private lateinit var mPartialChat:ArrayList<PartialChatBotSource>
    private lateinit var recyclerView: RecyclerView
    private lateinit var nickname: String
    private lateinit var id: String
    private lateinit var filePath:String
    private lateinit var otherName: String
    private val mContext: Context = this
    private var x_down:Float = .0f
    private var x_up:Float = .0f

    private lateinit var sendBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("ChatDetailActivity", "onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail)

        val prevIntent:Intent = intent
        getIntentExtra()
        createInit()
        textDataInit()

    }


    private fun getIntentExtra(){
        Log.d("ChatDetailActivity", "getIntentExtra")

        var intent: Intent = this.intent
        filePath = intent.getStringExtra("path")
        nickname = intent.getStringExtra("nick")
        id = intent.getStringExtra("id")

        Log.d("ChatDetailActivity", "id : $id , nickname : $nickname")

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

        // swipe 적용
        acceptSwipe()
//        val simpleCallback: ItemTouchHelper.SimpleCallback = object :
//            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: ViewHolder,
//                target: ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
//
//                var position:Int = viewHolder.layoutPosition
//                if (position % 2 == 1) position -= 1
//                mPartialChat.removeAt(position)
//                mPartialChat.removeAt(position)
//
//                partialChatGroupAdapter.notifyItemRemoved(position)
//                partialChatGroupAdapter.notifyItemRemoved(position)
//
//            }
//        }
//        val itemTouchHelper = ItemTouchHelper(simpleCallback)
//        itemTouchHelper.attachToRecyclerView(recyclerView)
    }



    private fun textDataInit(){
        lateinit var buf:BufferedReader
        var tellQ = false
        try {
            buf = BufferedReader(FileReader(filePath))
            val texts:List<String> = buf.readLines()
            val questioner = "회원님 : "
            val respondent:String = texts[0].substringBefore("님과").substring(1) + ": "
            otherName = respondent.substringBefore(" :")
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

    private fun acceptSwipe(){
        val swipeController:SwipeController = SwipeController(object : SwipeControllerActions() {
            // 수정
            override fun onLeftClicked(position: Int) {
                val intent:Intent = Intent(mContext, ChatDetailEditActivity::class.java)
                intent.putExtra("context", partialChatGroupAdapter.mPartialChat[position].message)
                intent.putExtra("position", position)

                startActivityForResult(intent, requestEdit)

            }

            // delete
            override fun onRightClicked(position: Int) {
                var currPos = position
                if(position % 2 == 1) currPos -= 1
                partialChatGroupAdapter.mPartialChat.removeAt(currPos)
                partialChatGroupAdapter.notifyItemRemoved(currPos)
                partialChatGroupAdapter.mPartialChat.removeAt(currPos)
                partialChatGroupAdapter.notifyItemRemoved(currPos)
                partialChatGroupAdapter.notifyItemRangeChanged(currPos, partialChatGroupAdapter.itemCount)
            }
        })
        val itemTouchHelper:ItemTouchHelper = ItemTouchHelper(swipeController)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun onDraw(
                c: Canvas,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                swipeController.onDraw(c)
            }
        })
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.send_btn_chat_detail -> {
                Log.d("Button : ", "Chat Detail Send")
                var count = 0
                var fullContexts = ""
                val size = partialChatGroupAdapter.mPartialChat.size
                val partialChat:ArrayList<PartialChatBotSource> = partialChatGroupAdapter.mPartialChat
                while(count < size){
                    var itemTitle:String = ""
                    var mark:String = "##"
                    if(partialChat[count].isQ){
                        itemTitle = "Q"
                        mark = "#@@#"
                    }
                    else{
                        itemTitle = "A"
                        mark = "@##@"
                    }
                    val itemText:String = partialChat[count++].message
                    Log.d("Item Context -  ", "$itemTitle : $itemText")
                    fullContexts += "$itemTitle : $itemText $mark"
                }

                val values = ContentValues()
//                val autoChecked = autoLogin.isChecked
                values.put("contexts", fullContexts)
                values.put("nick", nickname)
                values.put("id", id)
                values.put("indexName", "$otherName")
                Log.d("chat_index", "채팅내용 생성 $otherName")
                val task = CreateBotNetworkTask(sendInfoUrl, values, this)
                task.execute()


            }
        }
    }


    // 수정 결과 확인
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("ChatDetailActivity", "onActivityResult")
        if(requestCode == requestEdit){
            if(resultCode == resultOK){
                val intent:Intent = data as Intent

                val pos:Int = intent.getIntExtra("position", 0)
                val editContext:String = intent.getStringExtra("context")
                partialChatGroupAdapter.mPartialChat[pos].message = editContext
                partialChatGroupAdapter.notifyItemChanged(pos)
            }
        }
    }

    companion object {
        //    private ActionBar actionbar;
        private const val sendInfoUrl = "http://3.15.44.44:5000/CreateBot"
    }
}
