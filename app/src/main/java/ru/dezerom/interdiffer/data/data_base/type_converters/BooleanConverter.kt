package ru.dezerom.interdiffer.data.data_base.type_converters

import androidx.room.TypeConverter

class BooleanConverter {

    @TypeConverter
    fun booleanToInt(boolean: Boolean) =
        if (boolean) 1 else 0

    @TypeConverter
    fun intToBoolean(int: Int) =
        int == 1

}
