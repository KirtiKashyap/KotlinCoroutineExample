package com.example.myapplication.model.response.profile

data class ProfileUniversity(
    val id : Int ?,
    val name : String ?,
    val email : String ?,
    val verified : Boolean ?,
    val study_year : Int ?,
    val degree : Int ?,
    val degree_name : String ?
)

data class ProfileLocation(
    val method : Int ?,
    val longitude : Double ?,
    val latitude : Double ?,
    val address_line_1 : String ?,
    val address_line_2 : String ?,
    val address_town : String ?,
    val address_county : String ?,
    val address_post_code : String ?
)


data class ProfileSports(
    val id : Int ?,
    val name : String ?,
    val ability : Int ?,
    val interest_level : Int ?
)


data class ProfileAvailability(
    val id : Int ?,
    val day : Int ?,
    val am : Boolean ?,
    val pm : Boolean ?,
    val evening : Boolean ?
)