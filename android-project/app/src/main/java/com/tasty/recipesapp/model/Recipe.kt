package com.tasty.recipesapp.model

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
    val instructions: List<Instruction>,
    val nutrition: Nutrition
)