package com.example.myapplication.model.response.conversation

data class MessageData(val data: List<MessageList>,val meta : MetaMessage)

data class MessageList(
    val id: Int ?,
    val sender : Int ?,
    val message : String ?,
    val read : Int ?,
    val sent : String ?
)
data class MetaMessage(val pagination : Pagination)

data class Pagination(
    val total: Int ?,
    val count : Int ?,
    val per_page : Int ?,
    val current_page : Int ?,
    val total_pages : Int ?,

)
