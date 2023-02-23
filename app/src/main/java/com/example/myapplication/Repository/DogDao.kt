package com.example.myapplication.Repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface DogDao {
    @Insert
    fun insert(dogEntity: DogEntity)

    @Query("select * from DOG")
    fun getAllDog(): LiveData<List<DogEntity>>
}