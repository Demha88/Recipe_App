package be.bf.android.recetteapp.dal.converters

import androidx.room.TypeConverter
import androidx.room.TypeConverters

class MealConverter {

    @TypeConverter
    fun fromAnyToString(rep : Any?):String{

        if (rep == null){
            return ""
        }
        return rep as String
    }

    @TypeConverter
    fun fromStringToAny(rep: String?):Any{
        if (rep == null){
            return ""
        }
        return rep
    }

}