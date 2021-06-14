package com.example.myapplication.view.authorization

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.model.request.TokenRequest
import com.example.myapplication.utils.Resource
import com.example.myapplication.utils.SharedPref
import com.example.myapplication.view.conversation.ConversationActivity
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var viewModel: AuthorizationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference= activity?.let { SharedPref(it.applicationContext) }
        viewModel = (activity as AuthorizationActivity).viewModel

        loginButton.setOnClickListener {
            val tokenRequest= TokenRequest(email = userName.text.toString(),password = passWord.text.toString())
            viewModel.getToken(tokenRequest)
        }


        viewModel.tokenData.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    //hideProgressBar()
                    response.data?.let {

                        it.is_verified
                        val token=it.token_type+" "+it.access_token
                        sharedPreference?.save("APP_PREF_TOKEN",token)
                        viewModel.getUserProfile(token)
                        closeActivity()

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



        viewModel.userProfile.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    //hideProgressBar()
                    response.data?.let {
                        it.data!!.id
                        sharedPreference?.save("USER_ID",it.data!!.id!!)
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
    private fun closeActivity(){
        activity?.let{
            val intent = Intent (activity, ConversationActivity::class.java)
            it.finish()
            it.startActivity(intent)
        }
    }


}