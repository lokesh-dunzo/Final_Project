package com.example.myapplication

import Repository.MainRepository
import ViewModel.MainViewModel
import ViewModel.MyViewModelFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.myapplication.RetroFit.RetroFitClient
import kotlinx.coroutines.launch
import com.bumptech.glide.Glide
import com.example.myapplication.RecylerView.Adaptor
import com.example.myapplication.Repository.DogDataBase
import com.example.myapplication.Repository.DogEntity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.notification.MyNotificationService
import kotlinx.coroutines.Dispatchers
import android.annotation.SuppressLint as SuppressLint1

class MainActivity : AppCompatActivity() {
    lateinit var viewModel : MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var adaptor: Adaptor
    var list = mutableListOf<DogEntity>()

    @SuppressLint1("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adaptor = Adaptor()

        viewModel = ViewModelProvider(this,MyViewModelFactory(MainRepository(RetroFitClient.getRetroFirInstance(),DogDataBase.getInstance(this)))).get(MainViewModel::class.java)
        val notificationService = MyNotificationService(this)
        // code for shred pref
        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = sharedPref.edit()

        // code for circle while loading pic
        val circularProgressDrawable = CircularProgressDrawable(this).apply { 
            strokeWidth = 7f
            centerRadius = 40f
        }
        circularProgressDrawable.start()
        val imageView: ImageView = findViewById<View>(R.id.imageview) as ImageView

        viewModel.randomPicData.observe(this , Observer {
            if(it.message != "not fount"){
                val dogEntity = DogEntity(0,it.message,it.status)
                list.add(dogEntity)
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.addData(dogEntity)
                }
                Glide.with(this).load(it.message)
                    .placeholder(circularProgressDrawable)
                    .into(imageView)
                adaptor.setList(list)
            }
        })
        fetch_new_image()
        binding.fetchNewDog.setOnClickListener(View.OnClickListener {
            fetch_new_image()
            var fetchDogs = sharedPref.getInt("fetch_count",0)
            fetchDogs ++
            Log.d("fetch",fetchDogs.toString())
            if(fetchDogs%10 != 0){
                notificationService.createNotification()
            }
            editor.apply{
                putInt("fetch_count",fetchDogs)
                apply()
            }
        })
        viewModel.getAllData().observe(this,Observer{
            list = it.toMutableList()
            adaptor.setList(list)
        })
        binding.recyclerView.adapter = adaptor
    }
    fun fetch_new_image(){
        lifecycleScope.launch {
            viewModel.getRandomPic()
        }
    }
}