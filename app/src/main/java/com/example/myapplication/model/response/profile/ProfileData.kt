package com.example.myapplication.model.response.profile

import androidx.room.Entity
import androidx.room.PrimaryKey
//@Entity(tableName = "user_profile")
data class ProfileData(
//@PrimaryKey
val id : Int,
val first_name : String,
val last_name: String,
val date_of_birth : String,
val email : String,
val photo : String,
val phone_number : String,
val gender : Int,
val university_id : Int,
val university : ProfileUniversity,
val location : ProfileLocation,
val sports : List<ProfileSports>,
val availability : List<ProfileAvailability>
)
