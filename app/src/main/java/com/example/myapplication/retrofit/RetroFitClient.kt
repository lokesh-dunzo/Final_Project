package com.example.myapplication.retrofit

import com.example.myapplication.model.Dog
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetroFitClient{
    @GET("api/breeds/image/random")
    fun getRandomPic() : Call<Dog>
}

object RetroFitClientService {
    private const val BASE_URL = "https://dog.ceo/"
    val gson = GsonBuilder()
        .setLenient()
        .create()
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    // we can create same thing for more API
    val retroFirService: RetroFitClient by lazy{
        retrofit.create(RetroFitClient::class.java)
    }
}

