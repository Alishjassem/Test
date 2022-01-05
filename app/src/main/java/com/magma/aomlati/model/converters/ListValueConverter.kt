package com.magma.aomlati.model.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.magma.aomlati.model.Value
import java.io.Serializable

class ListValueConverter : Serializable {

    @TypeConverter
    fun fromObject(values: List<Value>?): String? {
        if (values == null) return null
        val gson = Gson()
        val type = object : TypeToken<List<Value>?>() {}.type
        return gson.toJson(values, type)
    }

    @TypeConverter
    fun toObject(values: String?): List<Value>? {
        if (values == null) return null
        val gson = Gson()
        val type = object : TypeToken<List<Value>?>() {}.type
        return gson.fromJson(values, type)
    }
}