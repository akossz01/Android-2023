package com.tasty.recipesapp.model

import com.tasty.recipesapp.data.NutritionDTO

data class Nutrition(
    val calories: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val carbohydrates: Int = 0,
    val sugar: Int = 0,
    val fiber: Int = 0
)

fun Nutrition.toEntity(): NutritionDTO {
    return NutritionDTO(
        calories = this.calories,
        protein = this.protein,
        fat = this.fat,
        carbohydrates = this.carbohydrates,
        sugar = this.sugar,
        fiber = this.fiber
    )
}
