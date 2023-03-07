package com.example.myapplication

import Repository.MainRepository
import ViewModel.MainViewModel
import ViewModel.MyViewModelFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.bumptech.glide.Glide
import com.example.myapplication.recylerview.Adaptor
import com.example.myapplication.repository.DogDataBase
import com.example.myapplication.repository.DogEntity
import com.example.myapplication.retrofit.RetroFitClientService
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var adaptor: Adaptor
    var list = mutableListOf<DogEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adaptor = Adaptor()

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(
                MainRepository(
                    RetroFitClientService.retroFirService,
                    DogDataBase.getInstance(this)
                )
            )
        ).get(MainViewModel::class.java)
        viewModel.start(this)

        val imageView: ImageView = findViewById<View>(R.id.imageview) as ImageView
        val circle = viewModel.getCirle(this)
        viewModel.randomPicData.observe(this, Observer {
            if (it.message != "not fount") {
                val dogEntity = DogEntity(0, it.message, it.status)
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.addData(dogEntity)
                }
                Glide.with(this).load(it.message)
                    .placeholder(circle)
                    .into(imageView)
                adaptor.addDogEntity(dogEntity)
            }
        })
        fetchNewImage()
        binding.fetchNewDog.setOnClickListener(View.OnClickListener {
            fetchNewImage()
            viewModel.onButtonClickAction()
        })
        viewModel.getAllData().observe(this, Observer {
            list = it.toMutableList()
            adaptor.setList(list)
        })
        binding.recyclerView.adapter = adaptor
    }

    fun fetchNewImage() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getRandomPic()
        }
    }
}