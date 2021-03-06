package com.example.kotlinchat.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.activity.ChatDetailActivity
import com.example.kotlinchat.activity.ChatSelectActivity
import com.example.kotlinchat.data.chatbot.ChatbotSource
import com.example.kotlinchat.viewholder.ChatSelectViewHolder

class ChatSelectGroupAdapter(val mSelectChat : ArrayList<ChatbotSource>, val mContext: Context, val nickname: String, val id:String) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val chatSelectRequest:Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder : RecyclerView.ViewHolder

        val view = LayoutInflater.from(mContext).inflate(R.layout.chat_select_row, parent, false)
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
            bindHolder.itemView.setOnClickListener{       // 클릭시 텍스트 창 뜨게
                val intent: Intent = Intent(mContext, ChatDetailActivity::class.java)
                intent.putExtra("path", chatSrc.directorySrc)
                intent.putExtra("nick", nickname)
                intent.putExtra("id", id)

                val prevActivity:ChatSelectActivity = mContext as ChatSelectActivity
                prevActivity.startActivityForResult(intent, chatSelectRequest)
            }
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