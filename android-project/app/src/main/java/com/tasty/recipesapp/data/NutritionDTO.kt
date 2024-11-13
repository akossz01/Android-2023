package com.tasty.recipesapp.data

import com.tasty.recipesapp.model.Nutrition

data class NutritionDTO(
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carbohydrates: Int,
    val sugar: Int,
    val fiber: Int
)

fun NutritionDTO.toNutrition(): Nutrition {
    return Nutrition(
        calories = this.calories,
        protein = this.protein,
        fat = this.fat,
        carbohydrates = this.carbohydrates,
        sugar = this.sugar,
        fiber = this.fiber
    )
}