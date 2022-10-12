package be.bf.android.recetteapp.dal.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.bf.android.recetteapp.api.dto.*
import be.bf.android.recetteapp.dal.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface RecetteDao {

    @Query("SELECT * FROM users_table ORDER BY userId DESC")
    fun getAllUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(registerUser: User)

    @Query("DELETE FROM users_table")
    fun deleteAll(): Int

    @Query("SELECT * FROM users_table WHERE user_name LIKE :userName")
    fun getUsername(userName: String): Flow<User?>
    //suspend fun getUsername(userName: String): User

    @Query("SELECT * FROM users_table WHERE user_name LIKE :userName")
    fun getUserById(userName: String): User


    @Transaction
    @Query("SELECT * FROM users_table")
    fun getUserWithMeal(): Flow<List<UserWithMeal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMeal(meal: Meal)

//    @Transaction
//    suspend fun insertMealDB(userId : Int, mealList : Meal){
//        addMeal(mealList)
//    }


    @Update
    suspend fun updateMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal:Meal)

    @Query("SELECT * FROM meal_table")
    fun readAllMeals(): Flow<List<Meal>>

    @Transaction
    @Query("SELECT * FROM meal_table")
    fun getMealWithIngredient(): Flow<List<MealWithIngredient>>

    @Transaction
    @Query("SELECT * FROM meal_table")
    fun getMealWithInstructions(): Flow<List<MealWithInstructions>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIngredient(ingredient: Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient:Ingredient)

    @Query("SELECT * FROM ingredient_table")
    fun readAllIngredients(): Flow<List<Ingredient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addInstruction(instruction: Instructions)

    @Delete
    suspend fun deleteInstruction(instruction: Instructions)

    @Query("SELECT * FROM instructions_table")
    fun readAllInstructions(): Flow<List<Instructions>>


    @Query("DELETE FROM instructions_table WHERE idMeal = :idMeal")
    suspend fun clearInstructions(idMeal: String)

    @Query("DELETE FROM ingredient_table WHERE idMeal = :idMeal")
    suspend fun clearIngredients(idMeal: String)

    @Query("SELECT * FROM ingredient_table WHERE idMeal = :idMeal")
    suspend fun getByMealIngredient(idMeal: String) : List<Ingredient>





}