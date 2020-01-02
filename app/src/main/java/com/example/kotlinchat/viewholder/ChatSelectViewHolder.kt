package com.example.kotlinchat.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R

class ChatSelectViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
    lateinit var chatSelectTitle: TextView
    lateinit var chatSelectSrc: TextView
    init{
        this.chatSelectTitle = itemView.findViewById(R.id.chat_select_item_title)
        this.chatSelectSrc = itemView.findViewById(R.id.chat_select_item_src)
    }
}