package com.example.myapplication.model.conversation

data class ConversationList(val data : List<ListData> ?)

data class ListData(
        val id: Int ?,
        val started: String ?,
        val starter_id: Int ?,
        val accepted: Boolean ?,
        val last_activity: String ?,
        val reported: String ?,
        val participants: List<Participants> ?,
        val messages: List<Messages> ?
)

data class Participants(
        val id: Int ?,
        val name: String ?,
        val first_name: String ?,
        val photo: String ?,
        val date_of_birth: String ?,
        val university: University ?,
        val sports: List<Sports> ?
)
data class Messages(
        val id: Int ?,
        val sender: Int ?,
        val message: String ?,
        val read: Int ?,
        val sent: String ?
)

data class Sports(
        val id: Int ?,
        val name: String ?,
        val ability: Int ?,
        val interest_level : Int ?
)
data class University(
        val name: String ?,
        val degree_name: String ?,
        val year_of_study: Int ?
)