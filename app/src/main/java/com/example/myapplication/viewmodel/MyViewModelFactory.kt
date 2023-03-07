package ViewModel

import Repository.MainRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            MainViewModel(repository) as T
        }else{
            throw java.lang.IllegalArgumentException("ViewModel Not Found")
        }
    }
}