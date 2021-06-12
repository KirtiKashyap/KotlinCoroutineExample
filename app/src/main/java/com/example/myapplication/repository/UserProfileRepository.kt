package com.example.myapplication.repository

import com.example.myapplication.data.remote.RetrofitInstance

class UserProfileRepository {
    suspend fun getUserProfile(token: String) =
        RetrofitInstance.api.getUserProfile(token)
}