package com.example.myapplication.Repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.Model.Dog
import kotlinx.coroutines.GlobalScope


@Database(entities = [DogEntity::class], version = 1)
abstract class DogDataBase : RoomDatabase() {

    abstract fun dogDao() : DogDao

    companion object{
        private var instance : DogDataBase? = null


        fun getInstance(context: Context): DogDataBase {
            if(instance == null){
                instance = Room.databaseBuilder(context.applicationContext,DogDataBase::class.java,"dogDatabase")
                    .build()
            }
            return instance!!
        }
    }
}