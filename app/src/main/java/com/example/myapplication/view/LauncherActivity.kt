package com.example.myapplication.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLauncherBinding
import com.example.myapplication.model.request.TokenRequest
import com.example.myapplication.repository.AuthRepository
import com.example.myapplication.utils.Resource
import com.example.myapplication.view.authorization.AuthorizationViewModel
import com.example.myapplication.view.authorization.AuthorizationViewModelFactory
import com.example.myapplication.view.conversation.ConversationActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.android.synthetic.main.activity_launcher.*

class LauncherActivity : AppCompatActivity() {
    lateinit var viewModel: AuthorizationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW)
            )
        }

        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras?.get(key)
                Log.d("TAG", "Key: $key Value: $value")
            }
        }



//        binding.subscribeButton.setOnClickListener {
//            Log.d("TAG", "Subscribing to weather topic")
//            // [START subscribe_topics]
//            Firebase.messaging.subscribeToTopic("weather")
//                .addOnCompleteListener { task ->
//                    var msg = getString(R.string.msg_subscribed)
//                    if (!task.isSuccessful) {
//                        msg = getString(R.string.msg_subscribe_failed)
//                    }
//                    Log.d("TAG", msg)
//                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                }
//            // [END subscribe_topics]
//        }

       /* binding.loginButton.setOnClickListener {
            // Get token
            // [START log_reg_token]

            Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, token)
                Log.d("TAG", msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })
            // [END log_reg_token]
        }*/







        val authRepository = AuthRepository()
        val viewModelProviderFactory = AuthorizationViewModelFactory(authRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            AuthorizationViewModel::class.java)

        binding.loginButton.setOnClickListener {
            val tokenRequest= TokenRequest(email = userName.text.toString(),password = passWord.text.toString())
            viewModel.getToken(tokenRequest)
        }


        viewModel.tokenData.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    //hideProgressBar()
                    response.data?.let {
                        it.access_token
                        it.token_type
                        it.is_verified
                        val intent = Intent(this, ConversationActivity::class.java).apply {
                            //putExtra(EXTRA_MESSAGE, message)
                            finish()
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