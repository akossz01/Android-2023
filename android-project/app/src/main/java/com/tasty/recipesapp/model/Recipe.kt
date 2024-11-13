package com.tasty.recipesapp.model

import com.tasty.recipesapp.data.RecipeDTO

data class Recipe(
    val id: Int,
    val name: String,
    val description: String?,
    val thumbnailUrl: String?,
    val keywords: List<String>,
    val isPublic: Boolean,
    val userEmail: String?,
    val originalVideoUrl: String?,
    val country: String?,
    val numServings: Int?,
    val components: List<Component>,
    val nutrition: Nutrition
)