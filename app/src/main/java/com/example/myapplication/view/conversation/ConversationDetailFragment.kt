package com.example.myapplication.view.conversation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.MessageAdapter
import com.example.myapplication.utils.Resource
import kotlinx.android.synthetic.main.fragment_conversation_detail.*

class ConversationDetailFragment : Fragment(R.layout.fragment_conversation_detail) {
    lateinit var viewModel: ConversationViewModel
    lateinit var messageAdapter: MessageAdapter
    val TAG = "ConversationDetailFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ConversationActivity).viewModel
        setupRecyclerView()

        viewModel.conversationList.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                   // hideProgressBar()
                    response.data?.let { conversationResponse ->
                       // messageAdapter.differ.submitList(conversationResponse.data[0].messages)
                    }
                }
                is Resource.Error -> {
                   // hideProgressBar()
                    response.message?.let { message ->
                       // Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    //showProgressBar()
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