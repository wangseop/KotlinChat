package com.example.kotlinchat.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.activity.ChatDetailActivity
import com.example.kotlinchat.data.chatbot.ChatbotSource
import com.example.kotlinchat.data.chatbot.PartialChatBotSource
import com.example.kotlinchat.viewholder.ChatDetailViewHolder
import com.example.kotlinchat.viewholder.ChatSelectViewHolder

class PartialChatGroupAdapter(val mPartialChat : ArrayList<PartialChatBotSource>, val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder : RecyclerView.ViewHolder

        val view = LayoutInflater.from(mContext).inflate(R.layout.chat_detail_row, parent, false)
        viewHolder = ChatDetailViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatSrc = mPartialChat[position]
        val type:Int = holder.itemViewType
        if (type == 1){
            val bindHolder = holder as ChatDetailViewHolder
            if(chatSrc.isQ)
                bindHolder.chatDetailTitle.setText("Q")
            else
                bindHolder.chatDetailTitle.setText("A")

            bindHolder.chatDetailText.setText(chatSrc.message)
            bindHolder.itemView.setOnClickListener{       // 클릭시 텍스트 창 뜨게

            }
        }
    }

    override fun getItemCount(): Int {
        return mPartialChat.size
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    fun addPartialChatSource(src: PartialChatBotSource){
        mPartialChat.add(src)
        Log.d("ChatSelect Adapter", ""+(mPartialChat.size-1))
        notifyItemInserted(mPartialChat.size-1)

    }
}