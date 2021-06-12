package com.example.myapplication.view.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.response.profile.UserProfile
import com.example.myapplication.repository.UserProfileRepository
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileViewModel(private val userProfileRepository: UserProfileRepository): ViewModel() {
    private val userProfile: MutableLiveData<Resource<UserProfile>> = MutableLiveData()

    fun getUserProfile(token: String) = viewModelScope.launch() {

        userProfile.postValue(Resource.Loading())
        val responseData = userProfileRepository.getUserProfile(token)
        userProfile.postValue(handleResponse(responseData))
    }

    private fun handleResponse(response: Response<UserProfile>) : Resource<UserProfile> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}