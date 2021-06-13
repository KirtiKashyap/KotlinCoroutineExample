package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemConversationBinding
import com.example.myapplication.model.response.conversation.ListData
import com.example.myapplication.model.response.conversation.Messages
import com.example.myapplication.model.response.conversation.Participants

class ConversationAdapter(private val listener: ConversationItemListener) : RecyclerView.Adapter<ConversationViewHolder>() {

    interface ConversationItemListener {
        fun onClickedConversation(id: Int?)
    }

    private val participantList = ArrayList<Participants>()
    private val messageList = ArrayList<Messages>()

    fun setItems(items: ArrayList<ListData>) {
        this.participantList.clear()
        this.messageList.clear()
        this.participantList.addAll(items[0].participants!!)
        this.messageList.addAll(items[0].messages!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val binding: ItemConversationBinding = ItemConversationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConversationViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = participantList.size

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) = holder.bind(participantList!![position]!!,messageList!![position])
}

class ConversationViewHolder(private val itemBinding: ItemConversationBinding,
                             private val listener: ConversationAdapter.ConversationItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var participents : Participants
    private lateinit var messages: Messages
    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(participants: Participants, messages: Messages) {
        this.participents = participants
        this.messages=messages
        //Glide.with(this).load(participants.photo).into(ivImage)
        itemBinding.tvName.text = participants.name
        itemBinding.tvMessage.text=messages.message
    }

    override fun onClick(v: View?) {
        listener.onClickedConversation(participents.id)
    }
}