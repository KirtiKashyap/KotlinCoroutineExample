package com.example.myapplication.model.request

data class SendMessage(
    val message : String,
    val content : String,
    val sender : Int,
    val sent : String
)