package com.example.myapplication.Repository

import Repository.MainRepository
import android.util.Log
import com.example.myapplication.Model.Dog
import com.example.myapplication.RetroFit.RetroFitClient
import com.google.common.truth.Truth
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Call


class MainRepositoryTest{

    lateinit var db : DogDataBase
    lateinit var retroFitClient: RetroFitClient
    lateinit var repository: MainRepository

    @Before
    fun init(){
        db = Mockito.mock(DogDataBase :: class.java)
        retroFitClient = Mockito.mock(RetroFitClient::class.java)
         //   RetroFitClient.getRetroFirInstance()
        repository = MainRepository(retroFitClient,db)
    }

    @Test
    fun testing_Get_Random_pic(){
        if(repository.internetIsConnected()){
            //val dog : Dog
            //Mockito.`when`(retroFitClient.getRandomPic()).thenReturn()
            runBlocking {
                val dog = repository.getRandomPic()
            }
            //println("Lokesh "+dog.toString())
            //Truth.assertThat(dog.status).isEqualTo("")
        }
        else{
            val dog = repository.getRandomPic();
            Truth.assertThat(dog == Dog("not fount","not found"))
        }
    }
}