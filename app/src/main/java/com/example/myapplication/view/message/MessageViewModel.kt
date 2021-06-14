package com.example.myapplication.view.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.request.SendMessage
import com.example.myapplication.model.response.conversation.MessageData
import com.example.myapplication.repository.ConversationRepository
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MessageViewModel(private val conversationRepository: ConversationRepository): ViewModel() {

    val messageList: MutableLiveData<Resource<MessageData>> = MutableLiveData()
    val sendMessageResponse: MutableLiveData<Resource<Unit>> = MutableLiveData()
    private val _id = MutableLiveData<Int>()


    fun getMessageList(token: String, id: Int)=viewModelScope.launch {
        messageList.postValue(Resource.Loading())
        val responseData = conversationRepository.getMessageList(token,id)
        messageList.postValue(handleMessageResponse(responseData))
    }

    fun sendMessage(token : String,sendMessage: SendMessage,id: Int)=viewModelScope.launch {
        sendMessageResponse.postValue(Resource.Loading())
        val responseData = conversationRepository.sendMessage(token,id,sendMessage)
        sendMessageResponse.postValue(handleSendMessageResponse(responseData))
    }

    private fun handleSendMessageResponse(responseData: Response<Unit>): Resource<Unit>{
        return if(responseData.code()==200) {
            Resource.Success(responseData.body()!!)
        }else{
            Resource.Error(responseData.toString())
        }

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




