package com.example.kotlinchat.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import org.w3c.dom.Text

class MessageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    lateinit var show_message:TextView
    lateinit var profile_image:ImageView
    lateinit var nick:TextView
    init{
        this.show_message = itemView.findViewById(R.id.show_message)
        this.profile_image = itemView.findViewById(R.id.profile_image)
        this.nick = itemView.findViewById(R.id.nick)
    }
}