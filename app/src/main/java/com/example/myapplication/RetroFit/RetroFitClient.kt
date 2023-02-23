package com.example.myapplication.RetroFit

import com.example.myapplication.Model.Dog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface RetroFitClient {
    @GET("api/breeds/image/random")
    fun getRandomPic() : Call<Dog>

    companion object{
        var retroFitInstance : RetroFitClient? = null;
        fun getRetroFirInstance()  : RetroFitClient{
            if(retroFitInstance == null){
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://dog.ceo/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                retroFitInstance = retrofit.create(RetroFitClient::class.java)
            }
            return retroFitInstance!!;
        }
    }
}