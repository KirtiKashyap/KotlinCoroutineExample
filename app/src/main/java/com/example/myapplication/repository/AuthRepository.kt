package com.example.myapplication.repository

import com.example.myapplication.data.remote.RetrofitInstance
import com.example.myapplication.model.request.TokenRequest

class AuthRepository() {
    suspend fun getAuthorization(request: TokenRequest) =
        RetrofitInstance.api.getToken(request)
    suspend fun getUserProfile(token: String) =
        RetrofitInstance.api.getUserProfile(token)
}