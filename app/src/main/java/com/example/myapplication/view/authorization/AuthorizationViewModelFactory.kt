package com.example.myapplication.view.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.AuthRepository

class AuthorizationViewModelFactory (private val authRepository: AuthRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthorizationViewModel(authRepository) as T
    }
}