package com.rajkumar.datamaticsapp.model.repository

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import com.rajkumar.datamaticsapp.model.Topping
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromString(value: String?): List<Topping> {
        val listType: Type = object : TypeToken<List<Topping?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<Topping?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}