package Repository
import androidx.room.RoomDatabase
import com.example.myapplication.Model.Dog
import com.example.myapplication.Repository.DogDataBase
import com.example.myapplication.Repository.DogEntity
import com.example.myapplication.RetroFit.RetroFitClient
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response


class MainRepository(private val retroFitClient: RetroFitClient,private val dogDataBase: DogDataBase) {
    fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
    fun getRandomPic() : Dog{

        var result : Dog = Dog("not fount","not found")
        if(internetIsConnected()){
            var call : Call<Dog> = retroFitClient.getRandomPic()

            //here storing data in local data base
            runBlocking {
                try {
                    val response: Response<Dog> = call.execute()
                    result = response.body()!!
                }
                catch(ex :java.lang.Exception){
                    /// Error handlling
                }
            }
        }
        else{
            // Here I will do Local DataBase Work

        }
        return result
    }

    fun getPicsFromDB() = dogDataBase.dogDao().getAllDog()
    fun insertDog(dogEntity: DogEntity) = dogDataBase.dogDao().insertDog(dogEntity)
    fun clearTable() = dogDataBase.dogDao().nukeTable()
}