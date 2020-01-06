package com.example.kotlinchat.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.data.chatbot.ChatbotSource
import com.example.kotlinchat.viewholder.ChatSelectViewHolder

class ChatSelectGroupAdapter(val mSelectChat : ArrayList<ChatbotSource>, val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder : RecyclerView.ViewHolder

        val view = LayoutInflater.from(mContext).inflate(R.layout.chat_select_row, parent, false)
        view.setOnClickListener {       // 클릭시 텍스트 창 뜨게

        }
        viewHolder = ChatSelectViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatSrc = mSelectChat[position]

        val type:Int = holder.itemViewType
        if (type == 1){
            val bindHolder = holder as ChatSelectViewHolder
            bindHolder.chatSelectTitle.setText(chatSrc.title)
            bindHolder.chatSelectSrc.setText(chatSrc.directorySrc)
        }
    }

    override fun getItemCount(): Int {
        return mSelectChat.size
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    fun addChatbotSource(botSrc:ChatbotSource){
        mSelectChat.add(botSrc)
        Log.d("ChatSelect Adapter", ""+(mSelectChat.size-1))
        notifyItemInserted(mSelectChat.size-1)

    }
}