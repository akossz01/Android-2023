package com.tasty.recipesapp.data

import com.tasty.recipesapp.model.Recipe
import kotlin.Unit

data class RecipeDTO(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnailUrl: String,
    val keywords: List<String>,
    val isPublic: Boolean,
    val userEmail: String,
    val originalVideoUrl: String,
    val country: String,
    val numServings: Int,
    val components: List<ComponentDTO>,
    val instructions: List<InstructionDTO>,
    val nutrition: NutritionDTO
)

fun RecipeDTO.toRecipe(): Recipe {
    return Recipe(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnailUrl = this.thumbnailUrl,
        keywords = this.keywords,
        isPublic = this.isPublic,
        userEmail = this.userEmail,
        originalVideoUrl = this.originalVideoUrl,
        country = this.country,
        numServings = this.numServings,
        components = this.components.map { it.toComponent() },
        nutrition = this.nutrition.toNutrition()

    )
}