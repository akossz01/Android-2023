package com.tasty.recipesapp.model

data class Component(
    val rawText: String,
    val extraComment: String?,
    val ingredientName: String,
    val measurement: String, // e.g., "8 oz"
    val position: Int
)
