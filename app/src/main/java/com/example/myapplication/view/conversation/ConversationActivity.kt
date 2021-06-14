package com.example.myapplication.view.conversation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.repository.ConversationRepository
import com.example.myapplication.view.message.MessageViewModel
import com.example.myapplication.view.message.MessageViewModelFactory

class ConversationActivity : AppCompatActivity() {
    lateinit var conversationViewModel: ConversationViewModel
    lateinit var messageViewModel: MessageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)
        val conversationRepository = ConversationRepository()
        val conversationViewModelProviderFactory = ConversationVIewModelFactory(conversationRepository)
        conversationViewModel = ViewModelProvider(this, conversationViewModelProviderFactory).get(ConversationViewModel::class.java)

        val messageViewModelProviderFactory = MessageViewModelFactory(conversationRepository)
        messageViewModel = ViewModelProvider(this, messageViewModelProviderFactory).get(MessageViewModel::class.java)
    }
}