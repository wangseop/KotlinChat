package com.example.kotlinchat.adapter

import android.content.Context
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.data.chat.Chat
import com.example.kotlinchat.viewholder.MessageViewHolder

class MessageAdapter(val mChat : ArrayList<Chat>, val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

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
        val chat = mChat[position]

        val type:Int = holder.itemViewType
        var bindHolder:MessageViewHolder
        if (type == MSG_LEFT){
            bindHolder = holder as MessageViewHolder
            bindHolder.show_message.setText(chat.message)
            bindHolder.nick.setText(chat.sender)
            bindHolder.profile_image.setImageResource(R.drawable.tan)
        }
        else{
            bindHolder = holder as MessageViewHolder
            bindHolder.show_message.setText(chat.message)
            bindHolder.nick.setText(chat.sender)
        }
    }

    override fun getItemCount(): Int {
        return mChat.size
    }

    override fun getItemViewType(position: Int): Int {
        return mChat[position].msgType
    }

    fun addChat(chat:Chat){
        mChat.add(chat)
        Log.d("Message Adapter AddChat", ""+(mChat.size-1))
        notifyItemInserted(mChat.size-1)

    }
}