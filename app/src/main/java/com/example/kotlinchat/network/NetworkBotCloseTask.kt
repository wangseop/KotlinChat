package com.example.kotlinchat.network

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.activity.ChatBotActivity
import com.example.kotlinchat.activity.LatestMessagesActivity
import com.example.kotlinchat.adapter.MessageAdapter
import com.example.kotlinchat.data.message.ChatMessage
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException

class NetworkBotCloseTask (val context: Context, val url: String, val values: ContentValues) : AsyncTask<Void, Void, String>() {


    // 비동기 송수신 부분
    override fun doInBackground(vararg params: Void?): String {
        Log.d("doInBackground", "NetworkBotCloseTask")
        val reqHttpURLConn: RequestHttpURLConnection = RequestHttpURLConnection()

        return reqHttpURLConn.request(url, values)
    }

    // doInBackground에서 받은 데이터 로직 처리
    override fun onPostExecute(s: String?) {
        super.onPostExecute(s)
        Log.d("onPostExecute", "NetworkBotCloseTask")

        try {
//            val jsonMap = parseJSONData(s)
//            if (jsonMap["state"] == "OK") { // 로그인 정보 저장
////                if (loginData.isAutoChecked()) pref.saveLoginData(context, loginData)
//                val intent = Intent(context, ChatActivity::class.java)
//                //                    intent.putExtra("nick", pref.getString("id",null));
//        }
            val jsonParser:JSONParser = JSONParser()
            val jsonObject: JSONObject = jsonParser.parse(s) as JSONObject
            val jsonArray: JSONArray = jsonObject["avatars"] as JSONArray
            Log.d("Bot Close Data", jsonObject.toString())
            if (jsonObject["state"] as String == "OK") {
                val chatBotActivity : ChatBotActivity = context as ChatBotActivity
                val intent = Intent()

                val avatars:Array<String> = Array(jsonArray.size, {""})

                val chats:Map<String, Array<String>> = jsonObject["chat"] as Map<String, Array<String>>
                for (i in  0 until jsonArray.size){
                    avatars[i] = jsonArray[i] as String
                }
//                intent.putExtra("nick", pref.getString("id",null))
                intent.putExtra("avatars", avatars)
                // 임시로 avatar 채팅 내용 넘겨주기위해 사용
                for (avatar in avatars){

                    val tmp_chat:Array<String> = Array((chats[avatar] as JSONArray).size, {""})
                    for (i in 0 until tmp_chat.size){
                        tmp_chat[i] = (chats[avatar] as JSONArray)[i] as String
                    }
                    intent.putExtra(avatar, tmp_chat)
                }
                Log.d("NetworkBotCloseTask", "onPostExecute")

                chatBotActivity.setResult(chatBotActivity.RESPONSE_CLOSE_BOT, intent)
//                chatBotActivity.finish()

            }

            // 로그인 정보 확인 성공 시

// 출력할 닉네임 전달(지금은 ID 사용)


        } catch (e: ParseException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
        }
    }
}

