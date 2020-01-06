package com.example.kotlinchat.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R

class ChatDetailViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
    lateinit var chatDetailTitle: TextView
    lateinit var chatDetailText: TextView
    init{
        this.chatDetailTitle = itemView.findViewById(R.id.chat_detail_item_title)
        this.chatDetailText = itemView.findViewById(R.id.chat_detail_item_text)
    }
}