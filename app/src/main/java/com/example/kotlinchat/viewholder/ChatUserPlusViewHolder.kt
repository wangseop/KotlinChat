package com.example.kotlinchat.viewholder

import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R

class ChatUserPlusViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)    {

    lateinit var plus_image: ImageButton
    init{
        this.plus_image = itemView.findViewById(R.id.add_chat_btn)
    }
}