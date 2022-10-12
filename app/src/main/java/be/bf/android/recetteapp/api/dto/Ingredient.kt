package be.bf.android.recetteapp.api.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient_table"
//    ,
//    foreignKeys = [
//        ForeignKey(
//            entity = Meal::class,
//            parentColumns = ["idMeal"],
//            childColumns = ["idMeal"],
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
//        )
//    ]
    )
data class Ingredient(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="ingredient_Id")
    val id: Int = 0,

    @ColumnInfo(name = "name_ingredient")
    var name : String,

    @ColumnInfo(name = "qtity_ingredient")
    var qtity: String,

    @ColumnInfo(name = "idMeal") var idMeal: String,
    var isChecked: Boolean = false,

)
