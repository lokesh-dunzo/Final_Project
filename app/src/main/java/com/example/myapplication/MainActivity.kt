package com.example.myapplication

import Repository.MainRepository
import ViewModel.MainViewModel
import ViewModel.MyViewModelFactory
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.myapplication.RetroFit.RetroFitClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.bumptech.glide.Glide
import com.example.myapplication.RecylerView.Adaptor
import com.example.myapplication.Repository.DogDataBase
import com.example.myapplication.Repository.DogEntity
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    lateinit var viewModel : MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var adaptor: Adaptor
    var list = mutableListOf<DogEntity>()
    val CHANNEL_ID = "channelID1"
    val CHANNEL_NAME = "channelName1"
    val NOTIF_ID = 1

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adaptor = Adaptor()

        viewModel = ViewModelProvider(this,MyViewModelFactory(MainRepository(RetroFitClient.getRetroFirInstance(),DogDataBase.getInstance(this)))).get(MainViewModel::class.java)
        // code for notification
        createNotifChannel()
        val intent = Intent(this,MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run{
            addNextIntentWithParentStack(intent)
            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notif = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Congrats")
            .setContentText("You have fetched new 10 Images click here to Fetch More!!!!")
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        val notiManger = NotificationManagerCompat.from(this)

        // code for shred pref

        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = sharedPref.edit()

        // code for circle while loading pic
        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 7f
        circularProgressDrawable.centerRadius = 40f
        circularProgressDrawable.start()
        val imageView: ImageView = findViewById<View>(R.id.imageview) as ImageView

        viewModel.randomPicData.observe(this , Observer {
            if(it.message != "not fount"){
                val dogEntity = DogEntity(0,it.message,it.status)
                //list.add(0,dogEntity)
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
            fetchDogs ++;
            Log.d("fetch",fetchDogs.toString());
            if(fetchDogs%10 == 0){
                notiManger.notify(NOTIF_ID,notif)
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
        GlobalScope.launch {
            viewModel.getRandomPic()
        }
    }
    private fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}