package com.tasty.recipesapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tasty.recipesapp.data.ComponentDTO
import com.tasty.recipesapp.data.InstructionDTO
import com.tasty.recipesapp.data.NutritionDTO

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val internalId: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String?,
    @ColumnInfo(name = "keywords") val keywords: List<String>?,
    @ColumnInfo(name = "isPublic") val isPublic: Boolean,
    @ColumnInfo(name = "userEmail") val userEmail: String?,
    @ColumnInfo(name = "originalVideoUrl") val originalVideoUrl: String?,
    @ColumnInfo(name = "country") val country: String?,
    @ColumnInfo(name = "numServings") val numServings: Int?,
    @ColumnInfo(name = "components") val components: List<ComponentDTO>?,
    @ColumnInfo(name = "instructions") val instructions: List<InstructionDTO>?,
    @ColumnInfo(name = "nutrition") val nutrition: NutritionDTO?
)
