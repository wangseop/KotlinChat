package com.example.kotlinchat.network

import android.content.ContentValues
import android.os.AsyncTask
import android.util.Log
import com.example.kotlinchat.activity.ChatDetailActivity
import com.example.kotlinchat.data.message.ChatMessage
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException

class CreateBotNetworkTask(val url: String, val values: ContentValues, val mContext: ChatDetailActivity)  : AsyncTask<Void, Void, String>(){

    // 비동기 송수신 부분
    override fun doInBackground(vararg params: Void?): String {
        val reqHttpURLConn: RequestHttpURLConnection = RequestHttpURLConnection()

        return reqHttpURLConn.request(url, values)
    }

    // doInBackground에서 받은 데이터 로직 처리
    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        try{
            Log.d("JSONRESULT", result)
            val jsonParser: JSONParser = JSONParser()
            val jsonObject: JSONObject = jsonParser.parse(result) as JSONObject
            val result:String = jsonObject["result"] as String
            Log.d("CreateBot Result :", result)
            if(result.equals("성공"))
                mContext.chatDetailResult = 1

        }catch(e : ParseException) {
            e.printStackTrace()
        }
    }


}