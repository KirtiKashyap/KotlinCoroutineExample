package com.example.myapplication.data.remote

import com.example.myapplication.model.response.conversation.ConversationList
import com.example.myapplication.model.request.TokenRequest
import com.example.myapplication.model.response.profile.UserProfile
import com.example.myapplication.model.response.token.GetToken
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("user/token")
    suspend fun getToken(@Body tokenRequest: TokenRequest): Response<GetToken>

   @Headers(
       "Accept: application/json",
       "Content-Type: application/json"
   )
    @GET("message/conversation")
    suspend fun getConversationList(@Header("AUTHORIZATION") value: String ): Response<ConversationList>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("user/profile")
    suspend fun getUserProfile(@Header("AUTHORIZATION") value: String ): Response<UserProfile>

}