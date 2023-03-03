package com.example.myapplication.Repository

import Repository.MainRepository
import android.util.Log
import com.example.myapplication.Model.Dog
import com.example.myapplication.RetroFit.RetroFitClient
import com.example.myapplication.RetroFit.RetroFitClientService
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.spy


class MainRepositoryTest{

    lateinit var db : DogDataBase
    lateinit var retroFitClient: RetroFitClient
    lateinit var repository: MainRepository

    @Before
    fun init(){
        db = Mockito.mock(DogDataBase :: class.java)
        val retroFitClient = RetroFitClientService.retroFirService
         //   RetroFitClient.getRetroFirInstance()
        repository = MainRepository(retroFitClient,db)
    }

    @Test
    fun testing_Get_Random_pic(){
        if(repository.internetIsConnected()){

            Log.d("PRint Lokesh","BI")
            //Mockito.`when`(RetroFitClientService.retroFirService.getRandomPic()).then(Call<Dog>)
            runBlocking {
                val dog = repository.getRandomPic()
            }
        }
        else{
            Log.d("PRint Lokesh","YES")
            val dog = repository.getRandomPic()
            Truth.assertThat(dog == Dog("not fount","not found"))
        }
    }
}