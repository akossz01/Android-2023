package com.tasty.recipesapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tasty.recipesapp.data.ComponentDTO
import com.tasty.recipesapp.data.InstructionDTO
import com.tasty.recipesapp.data.NutritionDTO
import com.tasty.recipesapp.data.RecipeDTO

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

fun RecipeEntity.toRecipeDTO(): RecipeDTO {
    return RecipeDTO(
        id = this.internalId,
        name = this.name ?: "",
        description = this.description ?: "",
        thumbnailUrl = this.thumbnailUrl ?: "",
        keywords = this.keywords ?: listOf(),
        isPublic = this.isPublic,
        userEmail = this.userEmail ?: "",
        originalVideoUrl = this.originalVideoUrl ?: "",
        country = this.country ?: "",
        numServings = this.numServings ?: 0,
        components = this.components?.map { it } ?: listOf(),
        instructions = this.instructions?.map { it } ?: listOf(),
        nutrition = this.nutrition ?: NutritionDTO(0, 0, 0, 0, 0, 0)
    )
}