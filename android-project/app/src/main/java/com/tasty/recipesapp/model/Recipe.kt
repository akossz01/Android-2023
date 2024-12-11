package com.tasty.recipesapp.model

import com.tasty.recipesapp.data.entity.RecipeEntity

data class Recipe(
    val id: Int,
    val name: String,
    val description: String?,
    val thumbnailUrl: String?,
    val keywords: String?,
    val isPublic: Boolean,
    val userEmail: String?,
    val originalVideoUrl: String?,
    val country: String?,
    val numServings: Int?,
    val components: List<Component>,
    val instructions: List<Instruction>,
    val nutrition: Nutrition?
)

fun Recipe.toRecipeEntity(): RecipeEntity {
    return RecipeEntity(
        internalId = this.id,
        name = this.name,
        description = this.description ?: "",
        thumbnailUrl = this.thumbnailUrl ?: "",
        keywords = this.keywords,
        isPublic = this.isPublic,
        userEmail = this.userEmail ?: "",
        originalVideoUrl = this.originalVideoUrl ?: "",
        country = this.country ?: "",
        numServings = this.numServings ?: 0,
        components = this.components.map { it.toEntity() },
        instructions = this.instructions.map { it.toEntity() },
        nutrition = this.nutrition?.toEntity()
    )
}