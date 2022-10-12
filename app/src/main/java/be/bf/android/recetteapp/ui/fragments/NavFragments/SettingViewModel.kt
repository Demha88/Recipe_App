package be.bf.android.recetteapp.ui.fragments.NavFragments

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.bf.android.recetteapp.api.MealDBApi
import be.bf.android.recetteapp.api.client.RetrofitClient
import be.bf.android.recetteapp.api.dto.*
import be.bf.android.recetteapp.dal.DbHelper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingViewModel(val context: Context) : ViewModel(){


    private val _getAllPopularMeal: MutableLiveData<List<MealByCategory>> = MutableLiveData()
    val getAllPopularMeal : LiveData<List<MealByCategory>>
        get() = _getAllPopularMeal

    private val _getAllRandomMeal : MutableLiveData<List<MealByCategory>> = MutableLiveData()
    val getAllRandomMeal : LiveData<List<MealByCategory>>
        get() = _getAllRandomMeal

    private val randomMealLd: MutableLiveData<Meal> = MutableLiveData()
    val currentRandomMeal: LiveData<Meal>
        get() = randomMealLd

    private val mealByIdLD: MutableLiveData<Meal> = MutableLiveData()
    val currentMealId: LiveData<Meal>
        get() = mealByIdLD

    private val _getAllCategory: MutableLiveData<List<Category>> = MutableLiveData()
    val getAllCategory : LiveData<List<Category>>
        get() = _getAllCategory

    private  val _getMealByCategory: MutableLiveData<List<MealByCategory>> = MutableLiveData()
    val getMealByCategory : LiveData<List<MealByCategory>>
        get() = _getMealByCategory

    private val _searchMeals: MutableLiveData<List<Meal>> = MutableLiveData()
    val getSearchMeal : LiveData<List<Meal>>
        get() = _searchMeals

    private val _getMealsOfTheDay : MutableLiveData<List<MealByCategory>> = MutableLiveData()
    val getMealsOfTheDay : LiveData<List<MealByCategory>>
        get() = _getMealsOfTheDay



    // private var randomMealLd = MutableLiveData<Meal>()

    fun randomMeal(){
        val api = RetrofitClient.client.create(MealDBApi::class.java)
        api.randomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val randomMeal: Meal = response.body()!!.meals[0]
                        randomMealLd.value=randomMeal
                        Log.d("retroRep", "meal id ${randomMeal.idMeal}")
                    }
                }else {
                    Toast.makeText(context, "Can't find something on this mealDBApi path!", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
            }
        })
    }


    fun getMealById(id:String){
        val api = RetrofitClient.client.create(MealDBApi::class.java)
        api.getMealById(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val mealByID: Meal = response.body()!!.meals[0]
                        mealByIdLD.value=mealByID
                        Log.d("retroRep", "meal id ${mealByID.idMeal}")
                    }
                }else {
                    Toast.makeText(context, "Can't find something on this mealDBApi path !", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
            }
        })
    }

    fun getPopularMeal(){
        val api = RetrofitClient.client.create(MealDBApi::class.java)
        api.getPopularMeal("Vegetarian").enqueue(object : Callback<MealByCategoryList>{
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _getAllPopularMeal.value = response.body()!!.meals
                    }
                }else {
                    Toast.makeText(context, "Can't find something on this mealDBApi path!", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
            }
        })
    }


    fun randomMeals(){
        val api = RetrofitClient.client.create(MealDBApi::class.java)
        api.getPopularMeal("Beef").enqueue(object : Callback<MealByCategoryList> {
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        _getAllRandomMeal.value=response.body()!!.meals
                        Log.d("retroAllMeals", "meal id")
                    }
                }else {
                    Toast.makeText(context, "Can't find something on this mealDBApi path !", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
            }
        })
    }


    fun getMealsOfTheDay(){
        val api = RetrofitClient.client.create(MealDBApi::class.java)
        api.getPopularMeal("Seafood").enqueue(object : Callback<MealByCategoryList> {
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        _getMealsOfTheDay.value=response.body()!!.meals
                        Log.d("retroAllMeals", "meal id")
                    }
                }else {
                    Toast.makeText(context, "Can't find something on this mealDBApi path !", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
            }
        })
    }



    fun getCategories(){
        val api = RetrofitClient.client.create(MealDBApi::class.java)
        api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        _getAllCategory.postValue(it.categories)
                    }
                }else {
                    Toast.makeText(context, "Can't find something on this mealDBApi path !", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
            }
        })
    }

    fun getMealByCategory(categoryName : String){
        val api = RetrofitClient.client.create(MealDBApi::class.java)
        api.getMealByCategory(categoryName).enqueue(object : Callback<MealByCategoryList> {
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        _getMealByCategory.postValue(it.meals)
                    }
                }else {
                    Toast.makeText(context, "Can't find something on this mealDBApi path !", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
            }
        })
    }

//    fun get() {
//        val api = RetrofitClient.client.create(MealDBApi::class.java)
//        val response = api.getCategories().execute()
//    }


    fun searchMeals(searchName : String){
        val api = RetrofitClient.client.create(MealDBApi::class.java)
        api.searchBar(searchName).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful){
                    val searchList = response.body()?.meals
                    searchList?.let { _searchMeals.postValue(it) }
//                    response.body()?.let {
//                        _searchMeals.postValue(it.meals)
//                    }
                }else {
                    Toast.makeText(context, "Can't find something on this mealDBApi path !", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
            }
        })
    }





}