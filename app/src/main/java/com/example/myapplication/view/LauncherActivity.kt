package com.example.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.model.request.TokenRequest
import com.example.myapplication.repository.AuthRepository
import com.example.myapplication.utils.Resource
import com.example.myapplication.view.authorization.AuthorizationViewModel
import com.example.myapplication.view.authorization.AuthorizationViewModelFactory
import com.example.myapplication.view.conversation.ConversationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

class LauncherActivity : AppCompatActivity() {
    lateinit var viewModel: AuthorizationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        val authRepository = AuthRepository()
        val viewModelProviderFactory = AuthorizationViewModelFactory(authRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            AuthorizationViewModel::class.java)
        loginButton.setOnClickListener {
            val tokenRequest= TokenRequest(email = userName.text.toString(),password = passWord.text.toString())
            viewModel.getToken(tokenRequest)
        }


        viewModel.tokenData.observe(this, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    //hideProgressBar()
                    response.data?.let {
                        it.access_token
                        it.token_type
                        it.is_verified
                        val intent = Intent(this, ConversationActivity::class.java).apply {
                            //putExtra(EXTRA_MESSAGE, message)
                        }
                        startActivity(intent)
                    }
                }
                is Resource.Error -> {
                    // hideProgressBar()
                    response.message?.let { message ->
                        //Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    //showProgressBar()
                }
            }
        })

    }

}