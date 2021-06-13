package com.example.myapplication.view.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.ConversationRepository

class MessageViewModelFactory (private val conversationRepository: ConversationRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MessageViewModel(conversationRepository) as T
    }
}