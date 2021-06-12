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
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(
                ConversationViewModel::class.java)
        val sharedPreference= SharedPref(this)
        val token =sharedPreference.getValueString("APP_PREF_TOKEN_TYPE")+" "+sharedPreference.getValueString("APP_PREF_ACCESS_TOKEN")

       viewModel.getList(token)
        viewModel.conversationList.observe(this, { response ->
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