package com.example.myapplication.RetroFit

import com.google.common.truth.Truth
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RetroFitClientServiceTest{
    private lateinit var retroFitClient: RetroFitClient

    @Before
    fun init(){
        retroFitClient = RetroFitClientService.retroFirService
    }

    @Test
    fun not_null_output() {
        val call = retroFitClient.getRandomPic()
        val response= call.execute().body()

        Assert.assertNotNull(response)
    }

    @Test
    fun getting_jpeg_pic_message(){
        val call = retroFitClient.getRandomPic()
        val response= call.execute().body()?.message
        Truth.assertThat(response?.substring(response.length-4)).isEqualTo(".jpg")
    }

    @Test
    fun getting_success_status(){
        val call = retroFitClient.getRandomPic()
        val response= call.execute().body()?.status
        Truth.assertThat(response).isEqualTo("success")
    }
}