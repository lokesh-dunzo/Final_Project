package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface DogDao {
    @Insert
    fun insertDog(dogEntity: DogEntity)

    @Query("select * from DOG")
    fun getAllDog(): LiveData<List<DogEntity>>

    @Query("DELETE FROM DOG")
    fun nukeTable()
}