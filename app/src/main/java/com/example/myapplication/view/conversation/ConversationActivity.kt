package com.example.myapplication.view.conversation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.repository.AuthRepository
import com.example.myapplication.repository.ConversationRepository
import com.example.myapplication.utils.Resource
import com.example.myapplication.view.authorization.AuthorizationViewModel
import com.example.myapplication.view.authorization.AuthorizationViewModelFactory

class ConversationActivity : AppCompatActivity() {
    lateinit var viewModel: ConversationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)

        val conversationRepository = ConversationRepository()
        val viewModelProviderFactory = ConversationVIewModelFactory(conversationRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(
                ConversationViewModel::class.java)

       viewModel.getList("Bearer 286|u9Z0mD2mGSktflG0e2dA947K37plx8N73xU4XJJY")
        viewModel.conversationList.observe(this, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    //hideProgressBar()
                    response.data?.let {
                        it
                    }
                }
                is Resource.Error -> {
                    // hideProgressBar()
                    response.message?.let { message ->
                        //Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    //showProgressBar()
                }
            }
        })

    }
}