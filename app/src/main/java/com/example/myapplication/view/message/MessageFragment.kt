package com.example.myapplication.view.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.MessageAdapter
import com.example.myapplication.utils.Resource
import com.example.myapplication.utils.SharedPref
import com.example.myapplication.view.conversation.ConversationActivity
import kotlinx.android.synthetic.main.fragment_message.*

class MessageFragment : Fragment(R.layout.fragment_message) {
    lateinit var viewModel: MessageViewModel
    val TAG = "MessageFragment"
    lateinit var messageAdapter: MessageAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ConversationActivity).messageViewModel

        val sharedPreference= activity?.let { SharedPref(it.applicationContext) }
        val token =sharedPreference?.getValueString("APP_PREF_TOKEN_TYPE")+" "+sharedPreference?.getValueString("APP_PREF_ACCESS_TOKEN")
        val userId=sharedPreference?.getValueInt("USER_ID")
        arguments?.getInt("id")?.let {
            //viewModel.start(it)
            viewModel.getMessageList(token,userId!!)
        }
        setupRecyclerView()

        viewModel.messageList.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {

                    response.data?.let { messageResponse ->
                        progressBar.visibility=View.GONE
                        rvMessageList.visibility=View.VISIBLE
                        //TODO set APP userID variable so that can check that its my message or others message
                        if (!messageResponse.data.isNullOrEmpty()) messageAdapter.setItems(ArrayList(messageResponse.data))
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility=View.GONE
                    response.message?.let { message ->
                       // Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    progressBar.visibility=View.VISIBLE
                    rvMessageList.visibility=View.GONE
                }
            }
        })
    }


    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter()
        rvMessageList.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}