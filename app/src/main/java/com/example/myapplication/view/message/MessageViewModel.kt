package com.example.myapplication.view.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.response.conversation.MessageData
import com.example.myapplication.repository.ConversationRepository
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MessageViewModel(private val conversationRepository: ConversationRepository): ViewModel() {

    val messageList: MutableLiveData<Resource<MessageData>> = MutableLiveData()
    private val _id = MutableLiveData<Int>()


    fun getMessageList(token: String, id: Int)=viewModelScope.launch {
        messageList.postValue(Resource.Loading())
        val responseData = conversationRepository.getMessageList(token,id)
        messageList.postValue(handleMessageResponse(responseData))
    }

    private fun handleMessageResponse(response: Response<MessageData>) : Resource<MessageData>{
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun start(id: Int) {
        _id.value = id
    }
}