package com.example.kotlinchat.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.data.message.ChatMessage
import com.example.kotlinchat.viewholder.MessageViewHolder

class MessageAdapter(val mChatMessage : ArrayList<ChatMessage>, val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val MSG_LEFT:Int = 1
    private val MSG_RIGHT:Int = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder : RecyclerView.ViewHolder

        val view = if (viewType == MSG_RIGHT){
            LayoutInflater.from(mContext).inflate(R.layout.message_single_layout_right, parent, false)
        }
        else{
            LayoutInflater.from(mContext).inflate(R.layout.message_single_layout_left, parent, false)
        }

        return MessageViewHolder(view)

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = mChatMessage[position]

        val type:Int = holder.itemViewType
        var bindHolder:MessageViewHolder
        if (type == MSG_LEFT){
            bindHolder = holder as MessageViewHolder
            bindHolder.show_message.setText(chat.message)
            bindHolder.nick.text = "${chat.sender}(아바타)"
            // 프로필 이미지 수정하려면 ImageResource의 경로를 따로 받아와야 함(현재는 default 이미지대로만 출력)
            bindHolder.profile_image.setImageResource(R.drawable.default_profile)
        }
        else{
            bindHolder = holder as MessageViewHolder
            bindHolder.show_message.setText(chat.message)
            bindHolder.nick.setText(chat.sender)
        }
    }

    override fun getItemCount(): Int {
        return mChatMessage.size
    }

    override fun getItemViewType(position: Int): Int {
        return mChatMessage[position].msgType
    }

    fun addChat(chatMessage:ChatMessage){
        mChatMessage.add(chatMessage)
        Log.d("Message Adapter AddChat", ""+(mChatMessage.size-1))
        notifyItemInserted(mChatMessage.size-1)

    }
}