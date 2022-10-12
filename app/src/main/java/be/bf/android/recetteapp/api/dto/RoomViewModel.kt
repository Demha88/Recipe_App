package be.bf.android.recetteapp.api.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.bf.android.recetteapp.dal.DbHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RoomViewModel(val database: DbHelper): ViewModel() {


    private val _readAllMeal: MutableLiveData<List<Meal>> = MutableLiveData()
    val readAllMeal: LiveData<List<Meal>>
        get() = _readAllMeal


    private val _readMealWithInstruction: MutableLiveData<List<MealWithInstructions>> = MutableLiveData()
    val readMealWithInstruction: LiveData<List<MealWithInstructions>>
        get() = _readMealWithInstruction

    private val _readIngredient: MutableLiveData<List<Ingredient>> = MutableLiveData()
    val readIngredient: LiveData<List<Ingredient>>
    get() = _readIngredient

    private val _getIngredient: MutableLiveData<List<Ingredient>> = MutableLiveData()
    val getIngredient: LiveData<List<Ingredient>>
        get() = _getIngredient

    private val _readMealWithIngredient: MutableLiveData<List<MealWithIngredient>> = MutableLiveData()
    val readMealWithIngredient: LiveData<List<MealWithIngredient>>
        get() = _readMealWithIngredient


init {
    viewModelScope.launch {
        database.registerDatabaseDao().readAllMeals().collect(){
            _readAllMeal.value = it
        }
    }

    viewModelScope.launch {
        database.registerDatabaseDao().getMealWithInstructions().collect(){
            _readMealWithInstruction.value = it
        }
    }

    viewModelScope.launch {
        database.registerDatabaseDao().getMealWithIngredient().collect(){
            _readMealWithIngredient.value = it
        }
    }



}

    fun insertFavoris(meal: Meal){
        viewModelScope.launch {
            database.registerDatabaseDao().addMeal(meal)
        }
    }

    fun deleteFavoris(meal: Meal){
        viewModelScope.launch {
            database.registerDatabaseDao().deleteMeal(meal)
        }
    }

    fun clearInstruction(meal: String){
        viewModelScope.launch {
            database.registerDatabaseDao().clearInstructions(meal)
        }
    }

    fun clearIngredient(meal: String){
        viewModelScope.launch {
            database.registerDatabaseDao().clearIngredients(meal)
        }
    }

    fun insertInstructions(instruction: Instructions){
        viewModelScope.launch {
          database.registerDatabaseDao().addInstruction(instruction)
        }
    }



    fun insertIngredient(ingredient: Ingredient){
        viewModelScope.launch {
            database.registerDatabaseDao().addIngredient(ingredient)
        }

    }

    fun readIngredientById(meal : String){
        viewModelScope.launch {
            val ingredients = database.registerDatabaseDao().getByMealIngredient(meal)
            _readIngredient.value = ingredients
        }
    }





}