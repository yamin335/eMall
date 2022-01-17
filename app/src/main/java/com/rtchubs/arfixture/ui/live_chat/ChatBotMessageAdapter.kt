package com.rtchubs.arfixture.ui.live_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.models.LiveChatMessage

private const val SERVER = 0
private const val CLIENT = 1
class ChatBotMessageAdapter constructor(
    private val itemCallback: (Int) -> Unit
): RecyclerView.Adapter<ChatBotMessageAdapter.ViewHolder>() {
    private var chatMessageList: ArrayList<LiveChatMessage> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =  if (viewType == SERVER) {
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_server_chat_row, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_client_chat_row, parent, false)
        }
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chatMessageList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return chatMessageList[position].type ?: 0
    }

    override fun getItemCount(): Int {
        return chatMessageList.size
    }

    fun addMessage(item: LiveChatMessage) {
        chatMessageList.add(item)
        notifyItemInserted(chatMessageList.size - 1)
    }

    fun getMessageCount() = chatMessageList.size

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val dateTime: TextView = view.findViewById(R.id.dateTime)
        val message: TextView = view.findViewById(R.id.message)
        fun bind(item: LiveChatMessage) {
            dateTime.text = item.dateTime
            message.text = item.message
        }
    }
}