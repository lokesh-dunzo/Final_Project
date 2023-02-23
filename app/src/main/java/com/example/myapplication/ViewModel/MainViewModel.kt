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
    val localDataBasePics = MutableLiveData<List<DogEntity>>()
    val localDataBasePicsNonLive = mutableListOf<DogEntity>()
    fun getRandomPic() = randomPicData.postValue(repository.getRandomPic())
    fun addData(dogEntity: DogEntity){
        repository.insertDog(dogEntity)
        localDataBasePicsNonLive.add(0,dogEntity);
        localDataBasePics.postValue(localDataBasePicsNonLive)
    }
    fun getAllData() = repository.getPicsFromDB()
    fun clearTable() = repository.clearTable()
}