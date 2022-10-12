package be.bf.android.recetteapp.api.dto

import androidx.room.Embedded
import androidx.room.Relation
import be.bf.android.recetteapp.dal.entities.User

data class UserWithMeal (
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val meals : List<Meal>

)