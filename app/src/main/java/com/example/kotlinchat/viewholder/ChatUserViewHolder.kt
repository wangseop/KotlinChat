package com.example.kotlinchat.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R

class ChatUserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)  {

    lateinit var profile_image: ImageView
    lateinit var profile_name: TextView
    lateinit var latest_msg: TextView
    init{
        this.profile_image = itemView.findViewById(R.id.chat_profile)
        this.profile_name = itemView.findViewById(R.id.chat_profile_name)
        this.latest_msg = itemView.findViewById(R.id.chat_latest_message)
    }
}