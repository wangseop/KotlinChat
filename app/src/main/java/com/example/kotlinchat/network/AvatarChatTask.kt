package com.example.kotlinchat.network

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.activity.ChatBotActivity
import com.example.kotlinchat.adapter.MessageAdapter
import com.example.kotlinchat.data.message.ChatMessage
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException

class AvatarChatTask (val context: Context,val msgAdapter: MessageAdapter, val url: String, val nick:String, val indexName:String, val values: ContentValues) : AsyncTask<Void, Void, String>() {


    // 비동기 송수신 부분
    override fun doInBackground(vararg params: Void?): String {
        Log.d("doInBackground", "AvatarChatTask")
        val reqHttpURLConn: RequestHttpURLConnection = RequestHttpURLConnection()

        return reqHttpURLConn.request(url, values)
    }

    // doInBackground에서 받은 데이터 로직 처리
    override fun onPostExecute(s: String?) {
        super.onPostExecute(s)
        Log.d("onPostExecute", "AvatarChatTask")

        try {
//            val jsonMap = parseJSONData(s)
//            if (jsonMap["state"] == "OK") { // 로그인 정보 저장
////                if (loginData.isAutoChecked()) pref.saveLoginData(context, loginData)
//                val intent = Intent(context, ChatActivity::class.java)
//                //                    intent.putExtra("nick", pref.getString("id",null));
//        }
            val jsonParser: JSONParser = JSONParser()
            val jsonObject: JSONObject = jsonParser.parse(s) as JSONObject
            Log.d("Bot Close Data", jsonObject.toString())
            if (jsonObject["state"] as String == "OK") {
                val chats:Map<String, Array<String>> = jsonObject["chat"] as Map<String, Array<String>>
                val currActivity:ChatBotActivity = context as ChatBotActivity
                Log.d("chats", jsonObject["chat"].toString())

                val tmpChat:Array<String> = Array((chats[indexName] as JSONArray).size, {""})
                for (i in 0 until tmpChat.size){
                    tmpChat[i] = (chats[indexName] as JSONArray)[i] as String
                }

                for (i in 0 until tmpChat.size){
                    Log.d("Chat Context", tmpChat[i])
                    val posString = tmpChat[i].substringAfter(" // ")
                    val context = tmpChat[i].substringBefore(" // ")

                    if (posString == "left") msgAdapter.addChat(ChatMessage(currActivity.MSG_LEFT, indexName, nick, context))
                    else msgAdapter.addChat(ChatMessage(currActivity.MSG_RIGHT, nick, indexName, context))

                }
            }

            // 로그인 정보 확인 성공 시

// 출력할 닉네임 전달(지금은 ID 사용)


        } catch (e: ParseException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
        }
    }
}