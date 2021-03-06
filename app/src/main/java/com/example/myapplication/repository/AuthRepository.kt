package com.example.myapplication.repository

import com.example.myapplication.data.remote.RetrofitInstance
import com.example.myapplication.model.request.TokenRequest

class AuthRepository() {
    suspend fun getAuthorization(request: TokenRequest) =
            RetrofitInstance.api.getToken(request)
    suspend fun getUserProfile(token: String) =
        RetrofitInstance.api.getUserProfile(token)


   /* suspend fun upsert(userProfile: UserProfile) = userDataBase.getUserDao().upsert(userProfile)

    fun getSavedUserProfile() = userDataBase.getUserDao().getUser()

    suspend fun deleteUserProfile(userProfile: UserProfile) = userDataBase.getUserDao().deleteUser(userProfile)*/



}