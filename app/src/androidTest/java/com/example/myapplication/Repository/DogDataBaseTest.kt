package com.example.myapplication.Repository

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.example.sample_crypto_tracker.getOrAwaitValue
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Rule

class DogDataBaseTest{
    private lateinit var db : DogDataBase
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @Before
    fun init(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = DogDataBase.getInstance(context)
    }

    @After
    fun closeDB(){
        db.close()
    }

    @Test
    fun addDogAndTest(){
        runBlocking {
            db.dogDao().nukeTable()
            val dog1 = DogEntity(0,"Hello!!! not pic available","pass")
            val dog2 = DogEntity(0,"Hello!!! not pic available 2","pass")
            db.dogDao().insertDog(DogEntity(0,"Hello!!! not pic available","pass"))
            val dog_data = db.dogDao().getAllDog().getOrAwaitValue()

            val dogs = dog_data
            Log.d("Data",dogs.toString())
            var ok1 :Boolean = false
            var ok2 : Boolean = false
            for(i in dogs!!){
                if(i.message == dog1.message && i.status == dog1.status){
                    ok1=true
                }
                if(i.message == dog2.message && i.status == dog2.status){
                    ok2=true
                }
            }
            Truth.assertThat(ok1).isTrue()
            Truth.assertThat(ok2).isFalse()
        }
    }
}
