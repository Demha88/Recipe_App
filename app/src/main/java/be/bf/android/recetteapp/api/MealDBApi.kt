package be.bf.android.recetteapp.api

import be.bf.android.recetteapp.api.dto.CategoryList
import be.bf.android.recetteapp.api.dto.MealByCategoryList
import be.bf.android.recetteapp.api.dto.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealDBApi {
    @GET("random.php")
    fun randomMeal():Call<MealList>

    @GET("lookup.php")
    fun getMealById(@Query("i") id: String):Call<MealList>

    @GET("filter.php")
    fun getPopularMeal(@Query("c") catgName: String):Call<MealByCategoryList>

    @GET("categories.php")
    fun getCategories() : Call<CategoryList>

    @GET("search.php")
    fun searchBar(@Query("s") searchName: String): Call<MealList>

    @GET("filter.php")
    fun getMealByCategory(@Query("c") categoryName: String):Call<MealByCategoryList>

}
