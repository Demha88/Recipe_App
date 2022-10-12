package be.bf.android.recetteapp.api.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.w3c.dom.ProcessingInstruction

@Entity(tableName = "instructions_table"
//        ,
//foreignKeys = [
//        ForeignKey(
//                entity = Meal::class,
//                parentColumns = ["idMeal"],
//                childColumns = ["idMeal"],
//                onDelete = ForeignKey.CASCADE,
//                onUpdate = ForeignKey.CASCADE
//        )
//]
        )
data class Instructions (

        @PrimaryKey(autoGenerate = true)
        val id: Int =0,

        @ColumnInfo(name = "instruction")
        var instr: String,

        @ColumnInfo(name = "idMeal") var idMeal: String

        )
