package be.bf.android.recetteapp.api.dto

import androidx.room.Embedded
import androidx.room.Relation

data class MealWithInstructions(
    @Embedded val meal : Meal,
    @Relation(
        parentColumn = "idMeal",
        entityColumn = "idMeal"
    )
    val instructions : List<Instructions>

)
