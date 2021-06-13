package com.example.myapplication.view.conversation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.adapter.ConversationAdapter
import com.example.myapplication.repository.ConversationRepository
import com.example.myapplication.utils.Resource
import com.example.myapplication.utils.SharedPref
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentConversationBinding
import kotlinx.android.synthetic.main.fragment_conversation.*

class ConversationFragment : Fragment(R.layout.fragment_conversation), ConversationAdapter.ConversationItemListener{
   lateinit var viewModel : ConversationViewModel

    private lateinit var adapter: ConversationAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentConversationBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        val conversationRepository = ConversationRepository()
        val viewModelProviderFactory = ConversationVIewModelFactory(conversationRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            ConversationViewModel::class.java)
        binding.lifecycleOwner = this

        // Giving the binding access to the AlbumViewModel
        binding.viewModel = viewModel
        adapter = ConversationAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        setHasOptionsMenu(true)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sharedPreference= activity?.let { SharedPref(it.applicationContext) }

        val token =sharedPreference?.getValueString("APP_PREF_TOKEN_TYPE")+" "+sharedPreference?.getValueString("APP_PREF_ACCESS_TOKEN")

        viewModel.getList(token)
        viewModel.conversationList.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    response.data?.let {

                        recyclerView.visibility = View.VISIBLE
                        if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    response.message?.let { message ->
                        Log.e("TAG", "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onClickedConversation(id: Int?) {
        findNavController().navigate(
            R.id.action_conversationFragment_to_conversationDetailFragment,
            bundleOf("id" to id)
        )
    }

}