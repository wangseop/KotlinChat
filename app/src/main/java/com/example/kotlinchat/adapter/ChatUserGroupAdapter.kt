package com.example.kotlinchat.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.activity.ChatBotActivity
import com.example.kotlinchat.activity.ChatSelectActivity
import com.example.kotlinchat.activity.LatestMessagesActivity
import com.example.kotlinchat.data.group.ChatUser
import com.example.kotlinchat.viewholder.ChatUserPlusViewHolder
import com.example.kotlinchat.viewholder.ChatUserViewHolder


class ChatUserGroupAdapter(val mUser : ArrayList<ChatUser>, val mContext: Context, val nickname: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val chatSelectRequest:Int = 1
    init{
        mUser.add(ChatUser(false, 0, "", ""))
        Log.d("Message Adapter AddChat", ""+(mUser.size-1))
        notifyItemInserted(mUser.size-1)

//        addChatUser(ChatUser(true, R.drawable.round_crop_girl, "아린", ""))
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder : RecyclerView.ViewHolder
        Log.d("OnCreateViewHolder", "latest_message_")

        // User 아닐 경우
        when (viewType){
            0 -> {
                Log.d("ChatUserGroupAdapter", "onCreateViewHolder 0")
                val view = LayoutInflater.from(mContext).inflate(R.layout.latest_message_plus, parent, false)
                viewHolder = ChatUserPlusViewHolder(view)

            }
            1 -> {
                Log.d("ChatUserGroupAdapter", "onCreateViewHolder 1")
                val view = LayoutInflater.from(mContext).inflate(R.layout.latest_message_row, parent, false)
                view.setOnClickListener{
                    val intent = Intent(mContext, ChatBotActivity::class.java)
                    // + 버튼 고려해서 mUser.size-2 위치로 설정
                    val indexName:String = "${mUser[mUser.size-2].username}"
                    Log.d("indexName", indexName)
//                intent.putExtra("nick", pref.getString("id",null))
                    intent.putExtra("nick", nickname)
                    intent.putExtra("indexName", indexName)
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
            bindHolder.profile_image.setImageResource(R.drawable.crop_girl)
            bindHolder.profile_name.text = "${user.username}(아바타)"
            bindHolder.latest_msg.setText(user.latestMessage)

        }else{
            val bindHolder = holder as ChatUserPlusViewHolder
            bindHolder.plus_image.setOnClickListener{

                // 우선 startActivity로 처리
                val intent:Intent = Intent(mContext, ChatSelectActivity::class.java)
                intent.putExtra("nick", nickname)
//                mContext.startActivity(intent)

                val latestMessagesActivity:LatestMessagesActivity = mContext as LatestMessagesActivity
                latestMessagesActivity.startActivityForResult(intent, chatSelectRequest)




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
        Log.d("Chat User Add", user.username)
        mUser.add(mUser.size - 1, user)
        Log.d("Message Adapter AddChat", ""+(mUser.size-2))
        // 추가되는 user의 위치 : 마지막 직전 index
        notifyItemInserted(mUser.size-2)

    }
}