package be.bf.android.recetteapp.api.dto

import androidx.room.Embedded
import androidx.room.Relation

data class MealWithIngredient(
    @Embedded val meal : Meal,
    @Relation(
        parentColumn = "idMeal",
        entityColumn = "idMeal"
    )
    val ingredients : List<Ingredient>

)
