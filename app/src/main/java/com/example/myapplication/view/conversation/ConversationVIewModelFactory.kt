package com.example.myapplication.view.conversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.ConversationRepository

class ConversationVIewModelFactory(private val authRepository: ConversationRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ConversationViewModel(authRepository) as T
    }
}