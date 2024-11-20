package com.tasty.recipesapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tasty.recipesapp.data.ComponentDTO
import com.tasty.recipesapp.data.InstructionDTO
import com.tasty.recipesapp.data.NutritionDTO

class Converters {

    private val gson = Gson()

    // Convert List<String> to JSON String
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return gson.toJson(value)
    }

    // Convert JSON String back to List<String>
    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    // Convert List<ComponentDTO> to JSON String
    @TypeConverter
    fun fromComponentDTOList(value: List<ComponentDTO>?): String? {
        return gson.toJson(value)
    }

    // Convert JSON String back to List<ComponentDTO>
    @TypeConverter
    fun toComponentDTOList(value: String?): List<ComponentDTO>? {
        val type = object : TypeToken<List<ComponentDTO>>() {}.type
        return gson.fromJson(value, type)
    }

    // Convert List<InstructionDTO> to JSON String
    @TypeConverter
    fun fromInstructionDTOList(value: List<InstructionDTO>?): String? {
        return gson.toJson(value)
    }

    // Convert JSON String back to List<InstructionDTO>
    @TypeConverter
    fun toInstructionDTOList(value: String?): List<InstructionDTO>? {
        val type = object : TypeToken<List<InstructionDTO>>() {}.type
        return gson.fromJson(value, type)
    }

    // Convert NutritionDTO to JSON String
    @TypeConverter
    fun fromNutritionDTO(value: NutritionDTO?): String? {
        return gson.toJson(value)
    }

    // Convert JSON String back to NutritionDTO
    @TypeConverter
    fun toNutritionDTO(value: String?): NutritionDTO? {
        return gson.fromJson(value, NutritionDTO::class.java)
    }
}
