package com.tasty.recipesapp.data

import com.tasty.recipesapp.model.Recipe

data class RecipeDTO(
    val recipeID: Int,
    val name: String?,
    val description: String?,
    val thumbnailUrl: String?,
    val keywords: String?,
    val isPublic: Boolean?,
    val userEmail: String?,
    val originalVideoUrl: String?,
    val country: String?,
    val numServings: Int?,
    val components: List<ComponentDTO>?,
    val instructions: List<InstructionDTO>?,
    val nutrition: NutritionDTO?
)

fun RecipeDTO.toRecipe(): Recipe {
    return Recipe(
        id = this.recipeID,
        name = this.name ?: "",
        description = this.description,
        thumbnailUrl = this.thumbnailUrl,
        keywords = this.keywords ?: "",
        isPublic = this.isPublic ?: false,
        userEmail = this.userEmail,
        originalVideoUrl = this.originalVideoUrl,
        country = this.country,
        numServings = this.numServings ?: 0,
        components = this.components?.map { it.toComponent() } ?: emptyList(),
        instructions = this.instructions?.map { it.toInstruction() } ?: emptyList(),
        nutrition = this.nutrition?.toNutrition()
    )
}