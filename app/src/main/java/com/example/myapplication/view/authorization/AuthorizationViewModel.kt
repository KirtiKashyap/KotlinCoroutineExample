package com.example.myapplication.view.authorization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.request.TokenRequest
import com.example.myapplication.model.response.token.GetToken
import com.example.myapplication.repository.AuthRepository
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthorizationViewModel (private val authRepository: AuthRepository): ViewModel() {

    val tokenData: MutableLiveData<Resource<GetToken>> = MutableLiveData()

    fun getToken(tokenRequest: TokenRequest) = viewModelScope.launch() {
        tokenData.postValue(Resource.Loading())
        val token = authRepository.getAuthorization(tokenRequest)
        tokenData.postValue(handleResponse(token))
    }

    private fun handleResponse(response: Response<GetToken>) : Resource<GetToken> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}