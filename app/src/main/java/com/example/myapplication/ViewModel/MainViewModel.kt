package ViewModel
import Repository.MainRepository
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.myapplication.Model.Dog
import com.example.myapplication.Repository.DogEntity

class MainViewModel(private val repository: MainRepository)  : androidx.lifecycle.ViewModel() {
    val randomPicData = MutableLiveData<Dog>()
    val localDataBasePics = MutableLiveData<List<Dog>>()
    val localDataBasePicsNonLive = mutableListOf<Dog>()
    fun getRandomPic(){
        randomPicData.postValue(repository.getRandomPic())
    }
}