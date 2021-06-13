package com.example.myapplication.repository

import com.example.myapplication.data.remote.RetrofitInstance

class ConversationRepository {
    suspend fun getConversationList(token: String) =
        RetrofitInstance.api.getConversationList(token)

    suspend fun getMessageList(token: String,id : Int) =
        RetrofitInstance.api.getMessage(token,id)


}