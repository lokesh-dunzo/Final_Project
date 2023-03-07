package com.example.myapplication.repository

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "DOG")
data class DogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val message: String,
    val status: String,
)