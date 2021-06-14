package com.example.myapplication.repository

import com.example.myapplication.data.remote.RetrofitInstance
import com.example.myapplication.model.request.SendMessage

class ConversationRepository (){
    suspend fun getConversationList(token: String) =
        RetrofitInstance.api.getConversationList(token)

    suspend fun getMessageList(token: String,id : Int) =
        RetrofitInstance.api.getMessage(token,id)

    suspend fun sendMessage(token: String,id : Int,sendMessage: SendMessage) =
        RetrofitInstance.api.sendMessage(token,id,sendMessage)


}