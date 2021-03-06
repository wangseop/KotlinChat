package com.example.kotlinchat.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.R
import com.example.kotlinchat.activity.ChatDetailActivity
import com.example.kotlinchat.activity.ChatDetailPopUpActivity
import com.example.kotlinchat.data.chatbot.ChatbotSource
import com.example.kotlinchat.data.chatbot.PartialChatBotSource
import com.example.kotlinchat.viewholder.ChatDetailViewHolder
import com.example.kotlinchat.viewholder.ChatSelectViewHolder

class PartialChatGroupAdapter(val mPartialChat : ArrayList<PartialChatBotSource>, val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var x_down:Float =.0f
    var x_up:Float =.0f

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
                bindHolder.chatDetailTitle.text = "Q"
            else
                bindHolder.chatDetailTitle.text = "A"

            bindHolder.chatDetailText.text = chatSrc.message
            bindHolder.itemView.setOnClickListener{       // 클릭시 텍스트 창 뜨게
//                mContext.startActivity(Intent(mContext, ChatDetailPopUpActivity::class.java))
            }
//            bindHolder.itemView.setOnTouchListener{ view: View, event: MotionEvent ->
//                when(event.action){
//                    MotionEvent.ACTION_DOWN->{
//                        x_down = event.x
//                    }
//                    MotionEvent.ACTION_MOVE->{
//                        x_up = event.x
//                        if(x_down - x_up >= 0){
//                            Log.d("Test Position", "$x_down // $x_up")
//                            val animation: Animation = AnimationUtils.loadAnimation(mContext, R.anim.swipe_left_to_right)
//                            view.startAnimation(animation)
//                        }
//                    }
//                }
//                false
//            }
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