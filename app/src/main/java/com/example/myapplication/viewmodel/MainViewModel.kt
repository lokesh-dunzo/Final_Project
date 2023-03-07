package ViewModel
import Repository.MainRepository
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.myapplication.model.Dog
import com.example.myapplication.repository.DogEntity
import com.example.myapplication.notification.MyNotificationService

class MainViewModel(private val repository: MainRepository)  : androidx.lifecycle.ViewModel() {
    val randomPicData = MutableLiveData<Dog>()
    val localDataBasePics = MutableLiveData<List<DogEntity>>()
    val localDataBasePicsNonLive = mutableListOf<DogEntity>()
    private lateinit var notificationService: MyNotificationService
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    fun start(context: Context){
        notificationService = MyNotificationService(context)
        sharedPreferences = context.getSharedPreferences("myShared_pre",
            AppCompatActivity.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
    }
    fun getCirle(context: Context): CircularProgressDrawable {

        // code for circle while loading pic
        val circularProgressDrawable = CircularProgressDrawable(context).apply {
            strokeWidth = 7f
            centerRadius = 40f
        }
        circularProgressDrawable.start()

        return circularProgressDrawable
    }
    fun onButtonClickAction(){
        var fetchDogs = sharedPreferences.getInt("fetch_count", 0)
        fetchDogs++
        Log.d("fetch", fetchDogs.toString())
        if (fetchDogs % 10 != 0) {
            notificationService.createNotification()
        }
        editor.apply {
            putInt("fetch_count", fetchDogs)
            apply()
        }
    }
    fun getRandomPic() = randomPicData.postValue(repository.getRandomPic())
    fun addData(dogEntity: DogEntity){
        repository.insertDog(dogEntity)
        localDataBasePicsNonLive.add(0,dogEntity)
        localDataBasePics.postValue(localDataBasePicsNonLive)
    }
    fun getAllData() = repository.getPicsFromDB()
    //fun clearTable() = repository.clearTable()
}