package com.example.myapplication.dagger

import android.content.Context
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.core.app.ApplicationProvider
import com.example.myapplication.Repository.DogDataBase
import com.example.myapplication.RetroFit.RetroFitClient
import dagger.Provides

@dagger.Module
class Module {
    @Provides
    fun provideRetroFitInstance() = RetroFitClient.getRetroFirInstance()

        //@Provides
    //fun provideDbInstance() {
       //val context = ApplicationProvider.getApplicationContext<Context>()
      //  DogDataBase.getInstance(getApplicationContext())
   // }
}