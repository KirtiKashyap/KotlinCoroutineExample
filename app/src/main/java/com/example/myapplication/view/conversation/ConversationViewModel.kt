package com.example.myapplication.view.conversation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.conversation.ConversationList
import com.example.myapplication.repository.ConversationRepository
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ConversationViewModel(private val conversationRepository: ConversationRepository): ViewModel() {
    val conversationList: MutableLiveData<Resource<ConversationList>> = MutableLiveData()

    fun getList(token: String) = viewModelScope.launch() {

        conversationList.postValue(Resource.Loading())
        val responseData = conversationRepository.getConversationList(token)
        conversationList.postValue(handleResponse(responseData))
    }

    private fun handleResponse(response: Response<ConversationList>) : Resource<ConversationList>{
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}