package com.example.kotlinchat.network
import android.content.ContentValues
import android.os.AsyncTask
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.adapter.MessageAdapter
import com.example.kotlinchat.data.message.ChatMessage
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException

class NetworkTask(val msgAdapter: MessageAdapter, val recyclerView: RecyclerView, val url: String, val values: ContentValues) : AsyncTask<Void, Void, String>(){

    private val MSG_LEFT:Int = 1
    private val MSG_RIGHT:Int = 2
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
            val jsonParser:JSONParser = JSONParser()
            val jsonObject: JSONObject = jsonParser.parse(result) as JSONObject
            val sender:String = jsonObject["sender"] as String
            val receiver:String = jsonObject["receiver"] as String
            val msg:String = jsonObject["message"] as String

            val chatMessage: ChatMessage = ChatMessage(MSG_LEFT, sender,
                receiver, msg)
//            lateChat.plus("$msg // left")
            msgAdapter.addChat(chatMessage)
            recyclerView.smoothScrollToPosition(msgAdapter.itemCount - 1)
        }catch(e : ParseException) {
            e.printStackTrace()
        }
    }


}