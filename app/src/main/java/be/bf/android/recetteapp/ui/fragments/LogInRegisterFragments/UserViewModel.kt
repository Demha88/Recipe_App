package be.bf.android.recetteapp.ui.fragments.LogInRegisterFragments


import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.bf.android.recetteapp.api.dto.Ingredient
import be.bf.android.recetteapp.api.dto.Instructions
import be.bf.android.recetteapp.api.dto.Meal
import be.bf.android.recetteapp.api.dto.UserWithMeal
import be.bf.android.recetteapp.dal.dao.RecetteDao
import be.bf.android.recetteapp.dal.entities.User
import kotlinx.coroutines.*

class UserViewModel(val dao: RecetteDao) : ViewModel() {



    private val _readAllData : MutableLiveData<List<UserWithMeal>> = MutableLiveData()
    val readAllData: LiveData<List<UserWithMeal>>
        get() = _readAllData


    private val _readAllMeal : MutableLiveData<List<Meal>> = MutableLiveData()
    val readAllMeal: LiveData<List<Meal>>
        get() = _readAllMeal


    private val users: MutableLiveData<List<User>> = MutableLiveData()
    val Users: LiveData<List<User>>
        get() = users

    private val currentUser: MutableLiveData<User?> = MutableLiveData()
    val CurrentUser: LiveData<User?>
        get() = currentUser




    init {
        viewModelScope.launch {
            //dao.registerDatabaseDao().addMeal(meal)
            dao.getUserWithMeal().collect(){
                _readAllData.value = it
            }

            dao.readAllMeals().collect(){
                _readAllMeal.value = it

            }
        }


    }




    fun loginUser(username: String, password: String, cb: (response: String, userId : Int?) -> Unit) {
        viewModelScope.launch {
           dao.getUsername(username).collect() {
                Log.d("LOG IN", "$it")
                if (it?.password == password) {
                    currentUser.value = it
                    cb("LOGIN", it.userId)
                } else {
                    cb("ERROR", null)
                }
            }
        }
    }
//fun loginUser(user: User, cb: (response: String) -> Unit) {
//    viewModelScope.launch {
//        dao.getUsername(user.userName).collect() {
//            Log.d("LOG IN", "$it")
//            if (it?.password == user.password) {
//                currentUser.value = it
//                cb("LOGIN")
//            } else {
//                cb("ERROR")
//            }
//        }
//    }
//}

//    fun registerUser(email: String, username: String, password: String, cb: (reponse: String) -> Unit)
//    {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                dao.getUsername(username).collect() {
//                    Log.d("VM", it.toString())
//                    if (it?.userName == username) {
//                        withContext(Dispatchers.Main) {
//                            cb("ERROR")
//                        }
//                    } else {
//                        dao.insert(User(0, email, username, password))
//
//                    }
//                }
//            }
//        }
//
//    }
//
//    fun registerUser(user: User, cb: (reponse: String) -> Unit) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                dao.getUsername(user.userName).collect(){
//                    Log.d("VM", it.toString())
//                    if (it?.userName== user.userName){
//                        //currentUser.postValue(it)
//                        //withContext(Dispatchers.IO){
//                            cb("ERROR")
//                        //}
//                    } else {
//                           dao.insert(User(user.userId, user.email, user.userName, user.password))
//                    }
//                }
//            }
//        }
//  }

    fun registerUser(user: User) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                dao.getUsername(user.userName).collect(){ userFromDb ->
                    Log.d("VM", userFromDb.toString())
                    if (userFromDb != null){
                        //currentUser.postValue(it)
                        //withContext(Dispatchers.IO){
                        withContext(Dispatchers.Main) {
                            currentUser.value = null
                        }

                        //}
                    } else {

                        dao.insert(User(user.userId, user.email, user.userName, user.password))
                        withContext(Dispatchers.Main) {
                            currentUser.value = user
                        }
                    }
                }
            }
        }
    }

    fun getFavoris(meal: Meal){
        viewModelScope.launch {
            //dao.registerDatabaseDao().addMeal(meal)
            dao.getUserWithMeal().collect(){
                _readAllData.value = it
            }
        }
    }

    fun insertFavoris(meal: Meal){
        viewModelScope.launch {
            //dao.addMeal(meal)
            dao.getUserWithMeal().collect(){
                dao.addMeal(meal)
            }
        }
    }


    fun insertInstructions(instruction: Instructions){
        viewModelScope.launch {
            dao.readAllMeals().collect(){
                dao.addInstruction(instruction)
            }
        }

    }

    fun insertIngredient(ingredient: Ingredient){
        viewModelScope.launch {
            dao.readAllMeals().collect(){
                dao.addIngredient(ingredient)
            }
        }

    }






}




