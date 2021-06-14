package com.example.myapplication.view.message

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.MessageAdapter
import com.example.myapplication.model.request.SendMessage
import com.example.myapplication.utils.Resource
import com.example.myapplication.utils.SharedPref
import com.example.myapplication.view.conversation.ConversationActivity
import kotlinx.android.synthetic.main.fragment_message.*

class MessageFragment : Fragment(R.layout.fragment_message) {
    private lateinit var viewModel: MessageViewModel
    private val TAG = "MessageFragment"
    private lateinit var messageAdapter: MessageAdapter
    private var conversationId =0
    private var userId=0
    private var participentId=0


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ConversationActivity).messageViewModel
        val sharedPreference= activity?.let { SharedPref(it.applicationContext) }
        val token=sharedPreference?.getValueString("APP_PREF_TOKEN")
        userId=sharedPreference?.getValueInt("USER_ID")!!
        conversationId = arguments?.getInt("conversationId")!!
        participentId = arguments?.getInt("participentId")!!
        if (token != null) {
            if (conversationId != null) {
                viewModel.getMessageList(token,conversationId)
            }
        }

        setupRecyclerView()
        observeMessage()
        btnSend.setOnClickListener {
            val timeInMillis = Calendar.getInstance().time
            if (token != null) {
                viewModel.sendMessage(token = token,sendMessage = SendMessage(message = txtMessage.text.toString(),
                    content = txtMessage.text.toString(),sender = conversationId!!,sent =timeInMillis.toString() ),id = conversationId!!)
                observeSendMessageResponse()
            }

        }
    }

    private fun observeSendMessageResponse() {
        viewModel.sendMessageResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    Log.e(TAG, "Message send:")
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    response.message?.let { message ->
                         Log.e(TAG, "An error occured:")
                    }
                }
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun observeMessage() {
        viewModel.messageList.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {

                    response.data?.let { messageResponse ->
                        progressBar.visibility = View.GONE
                        rvMessageList.visibility = View.VISIBLE
                        //TODO set APP userID variable so that can check that its my message or others message
                        if (!messageResponse.data.isNullOrEmpty()) messageAdapter.setItems(
                            ArrayList(
                                messageResponse.data
                            )
                        )
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    response.message?.let { message ->
                        // Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    rvMessageList.visibility = View.GONE
                }
            }
        })
    }


    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter( userId,participentId)
        rvMessageList.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}