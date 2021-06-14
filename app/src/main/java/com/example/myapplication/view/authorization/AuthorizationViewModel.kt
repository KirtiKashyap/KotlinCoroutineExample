package com.example.myapplication.view.authorization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.request.TokenRequest
import com.example.myapplication.model.response.profile.UserProfile
import com.example.myapplication.model.response.token.GetToken
import com.example.myapplication.repository.AuthRepository
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthorizationViewModel (private val authRepository: AuthRepository): ViewModel() {

    val tokenData: MutableLiveData<Resource<GetToken>> = MutableLiveData()
    val userProfile: MutableLiveData<Resource<UserProfile>> = MutableLiveData()

    fun getToken(tokenRequest: TokenRequest) = viewModelScope.launch() {
        tokenData.postValue(Resource.Loading())
        val token = authRepository.getAuthorization(tokenRequest)
        tokenData.postValue(handleLoginResponse(token))
    }

    fun getUserProfile(token: String) = viewModelScope.launch() {

        userProfile.postValue(Resource.Loading())
        val responseData = async {  authRepository.getUserProfile(token)}
        userProfile.postValue(handleProfileResponse(responseData.await()))
    }

    private fun handleProfileResponse(response: Response<UserProfile>) : Resource<UserProfile> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private fun handleLoginResponse(response: Response<GetToken>) : Resource<GetToken> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    /*fun saveUserProfile(userProfile: UserProfile) = viewModelScope.launch {
        authRepository.upsert(userProfile)
    }

    fun getSavedProfile() = authRepository.getSavedUserProfile()

    fun deleteProfile(userProfile: UserProfile) = viewModelScope.launch {
        authRepository.deleteUserProfile(userProfile)
    }*/

}