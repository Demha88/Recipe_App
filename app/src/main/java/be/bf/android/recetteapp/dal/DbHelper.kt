package be.bf.android.recetteapp.dal

import android.content.Context
import androidx.room.*
import be.bf.android.recetteapp.api.dto.Ingredient
import be.bf.android.recetteapp.api.dto.Instructions
import be.bf.android.recetteapp.api.dto.Meal
import be.bf.android.recetteapp.api.dto.Shopping_list
import be.bf.android.recetteapp.dal.converters.MealConverter
import be.bf.android.recetteapp.dal.dao.RecetteDao
import be.bf.android.recetteapp.dal.entities.User

@Database(entities = [User::class, Meal::class, Ingredient::class, Instructions::class, Shopping_list::class], version = 1, exportSchema = false)
@TypeConverters(MealConverter::class)
abstract class DbHelper : RoomDatabase() {

    companion object {

        const val DB_NAME="recette_database"
        private var instance: DbHelper? = null
        fun getInstance(context: Context): DbHelper {
            if (instance == null) {
                instance = Room
                    .databaseBuilder(context, DbHelper::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!

        }
    }
    abstract fun registerDatabaseDao(): RecetteDao

}