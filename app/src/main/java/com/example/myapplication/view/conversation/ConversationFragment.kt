package com.example.myapplication.view.conversation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import com.example.myapplication.R
import com.example.myapplication.adapter.ConversationAdapter
import com.example.myapplication.utils.Resource
import com.example.myapplication.utils.SharedPref
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_conversation.*

class ConversationFragment : Fragment(R.layout.fragment_conversation), ConversationAdapter.ConversationItemListener{
    lateinit var viewModel: ConversationViewModel
    val TAG = "ConversationFragment"

    private lateinit var conversationadapter: ConversationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ConversationActivity).conversationViewModel
        setupRecyclerView()
        val sharedPreference= activity?.let { SharedPref(it.applicationContext) }
        val token =sharedPreference?.getValueString("APP_PREF_TOKEN_TYPE")+" "+sharedPreference?.getValueString("APP_PREF_ACCESS_TOKEN")

        viewModel.getList(token)
        viewModel.conversationList.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    response.data?.let {
                        recyclerView.visibility = View.VISIBLE
                        //TODO set APP userID variable so that can check that its my message or others message
                        if (!it.data.isNullOrEmpty()) conversationadapter.setItems(ArrayList(it.data))
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    response.message?.let { message ->
                        Log.e("TAG", "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    recyclerView.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
            }
        })
    }
    private fun setupRecyclerView() {
        conversationadapter = ConversationAdapter(this)
        recyclerView.apply {
            adapter = conversationadapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    override fun onClickedConversation(id: Int?) {
        findNavController().navigate(
            R.id.action_conversationFragment_to_messageFragment,
            bundleOf("id" to id)
        )
    }

}