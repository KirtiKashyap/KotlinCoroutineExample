package com.example.myapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.model.response.profile.UserProfile

/*
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: UserProfile): Long

   */
/* @Query("SELECT * FROM user_profile")
    fun getAllUsers(): LiveData<List<UserProfile>>*//*


    @Query("SELECT * FROM user_profile")
    fun getUser(): LiveData<UserProfile>
    @Delete
    suspend fun deleteUser(article: UserProfile)
}*/
