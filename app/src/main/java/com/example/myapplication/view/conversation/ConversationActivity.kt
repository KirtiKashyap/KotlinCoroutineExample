package com.example.myapplication.view.conversation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.repository.ConversationRepository
import com.example.myapplication.utils.Resource
import com.example.myapplication.utils.SharedPref

class ConversationActivity : AppCompatActivity() {
    lateinit var viewModel: ConversationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)
        val conversationRepository = ConversationRepository()
        val viewModelProviderFactory = ConversationVIewModelFactory(conversationRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(ConversationViewModel::class.java)
    }
}