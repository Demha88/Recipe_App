package be.bf.android.recetteapp.api.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "shopping_table")
data class Shopping_list(

    @PrimaryKey(autoGenerate = true)
    val id: Int =0,

    var name_shopList: String,

    @ColumnInfo(name="userId") val userId: Int,

    @ColumnInfo(name = "ingredient_Id") var idIngredient : Int

    )
