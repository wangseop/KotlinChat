package com.example.kotlinchat.network

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.kotlinchat.activity.LatestMessagesActivity
import com.example.kotlinchat.data.user.LoginData
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException

class NetworkLoginTask(
    val context: Context, val url: String, val values: ContentValues, val loginData: LoginData) : AsyncTask<Void, Void, String>(){

    override fun doInBackground(vararg params: Void?): String {
        val reqHttpURLConn: RequestHttpURLConnection = RequestHttpURLConnection()

        return reqHttpURLConn.request(url, values)
    }


    override fun onPostExecute(s: String?) {
        super.onPostExecute(s)
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
            Log.d("Login Post Data", jsonObject.toString())
            if (jsonObject["state"] as String == "OK") {
                val intent = Intent(context, LatestMessagesActivity::class.java)
                val id:String = jsonObject["id"] as String
                val nick:String = jsonObject["nick"] as String
                val avatars:Array<String> = Array(jsonArray.size, {""})

                val chats:Map<String, Array<String>> = jsonObject["chat"] as Map<String, Array<String>>
                for (i in  0 until jsonArray.size){
                    avatars[i] = jsonArray[i] as String
                }
//                intent.putExtra("nick", pref.getString("id",null))
                intent.putExtra("id", id)
                intent.putExtra("nick", nick)
                intent.putExtra("avatars", avatars)

                // 임시로 avatar 채팅 내용 넘겨주기위해 사용
                for (avatar in avatars){

                    val tmp_chat:Array<String> = Array((chats[avatar] as JSONArray).size, {""})
                    for (i in 0 until tmp_chat.size){
                        tmp_chat[i] = (chats[avatar] as JSONArray)[i] as String
                    }
                    intent.putExtra(avatar, tmp_chat)
                }
                Log.d("NetworkLoginTask", "onPostExecute")
                Log.d("onPostExecute", "$id + // $nick")
                // 쿠키값 전달
//                intent.putExtra("cookie", jsonMap["cookie"])
                context.startActivity(intent)
            }
            // 로그인 정보 확인 성공 시

// 출력할 닉네임 전달(지금은 ID 사용)


        } catch (e: ParseException) {
            e.printStackTrace()
        } catch (e: NullPointerException) { // 로그인 실패 시
            Toast.makeText(context, "ID / Password 를 확인해주세요", Toast.LENGTH_SHORT).show()
        }
    }

//    @Throws(ParseException::class)
//    protected fun parseJSONData(s: String?): Map<String, String?> {
//        val jsonParser = JSONParser()
//        val jsonObject =
//            jsonParser.parse(s) as JSONObject
//        Log.d("JSON", jsonObject.toString())
//        val state = jsonObject["state"] as String?
//        val nick = jsonObject["nick"] as String?
//        val cookie = jsonObject["cookie"] as String?
//        val data: MutableMap<String, String?> =
//            HashMap()
//        data["state"] = state
//        data["nick"] = nick
//        data["cookie"] = cookie
//        return data
//    }

}