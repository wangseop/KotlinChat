package com.example.kotlinchat.adapter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.activity.ChatActivity
import com.example.kotlinchat.activity.ChatSelectActivity
import com.example.kotlinchat.activity.LatestMessagesActivity
import com.example.kotlinchat.data.chat.Chat
import com.example.kotlinchat.data.group.ChatUser
import com.example.kotlinchat.viewholder.ChatUserPlusViewHolder
import com.example.kotlinchat.viewholder.ChatUserViewHolder
import com.example.kotlinchat.viewholder.MessageViewHolder

import java.io.*


class ChatUserGroupAdapter(val mUser : ArrayList<ChatUser>, val mContext: Context, val nickname: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val chatSelectResult:Int = 1
    init{
        mUser.add(ChatUser(false, "", "", ""))
        Log.d("Message Adapter AddChat", ""+(mUser.size-1))
        notifyItemInserted(mUser.size-1)

        addChatUser(ChatUser(true, "", "nick", ""))
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder : RecyclerView.ViewHolder

        // User 아닐 경우
        when (viewType){
            0 -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.latest_message_plus, parent, false)
                viewHolder = ChatUserPlusViewHolder(view)

            }
            1 -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.latest_message_row, parent, false)
                view.setOnClickListener{
                    val intent = Intent(mContext, ChatActivity::class.java)

//                intent.putExtra("nick", pref.getString("id",null))
                    intent.putExtra("nick", nickname)
                    // 쿠키값 전달
//                intent.putExtra("cookie", jsonMap["cookie"])
                    mContext.startActivity(intent)
                }
                viewHolder =  ChatUserViewHolder(view)
            }
        }


        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = mUser[position]

        val type:Int = holder.itemViewType
        if (type == 1){
            val bindHolder = holder as ChatUserViewHolder
            bindHolder.profile_image.setImageResource(R.drawable.round_tan)
            bindHolder.profile_name.setText(user.username)
            bindHolder.latest_msg.setText(user.latestMessage)

        }else{
            val bindHolder = holder as ChatUserPlusViewHolder
            bindHolder.plus_image.setOnClickListener{

                // 우선 startActivity로 처리
                val intent:Intent = Intent(mContext, ChatSelectActivity::class.java)
//                mContext.startActivity(intent)

                val latestMessagesActivity:LatestMessagesActivity = mContext as LatestMessagesActivity
                latestMessagesActivity.startActivityForResult(intent, chatSelectResult)

//                addChatUser(ChatUser(true, "", "nick", ""))
                // 외부 저장소 경로 보는 방법
                // shift 2번 -> Device File Explorer 에서 sdcard/KaKaoTalk/Chats/ 참조
                // 혹은 mnt/sdcard/KaKaoTalk/Chats/
//                val folder:File = File(Environment.getExternalStorageDirectory().absolutePath +"/KakaoTalk/Chats/KakaoTalk_Chats_2020-01-02_03.28.38")
//                val folder:File = File("mnt/sdcard" +"/KakaoTalk/Chats/KakaoTalk_Chats_2020-01-02_03.28.38")
//                // folder 이름 내 채팅 목록 폴더명
//                Log.d("File List", folder.list()[0])
//
//                try {
//                    val buf = BufferedReader(FileReader(folder.absolutePath + "/" + folder.list()[0]))
//                    val line:String = buf.readLine()
//                    Log.d("KakaoTalk", line)
//                    buf.close()
//                } catch (e: FileNotFoundException) {
//                    e.printStackTrace()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }


            }
        }
    }

    override fun getItemCount(): Int {
        return mUser.size
    }

    override fun getItemViewType(position: Int): Int {
        if (mUser[position].isUser) return 1
        else return 0
    }

    fun addChatUser(user:ChatUser){
        mUser.add(mUser.size - 1, user)
        Log.d("Message Adapter AddChat", ""+(mUser.size-2))
        notifyItemInserted(mUser.size-2)

    }
}