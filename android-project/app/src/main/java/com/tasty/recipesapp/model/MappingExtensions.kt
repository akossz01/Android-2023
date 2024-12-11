package com.tasty.recipesapp.model

import com.tasty.recipesapp.data.ComponentDTO
import com.tasty.recipesapp.data.InstructionDTO
import com.tasty.recipesapp.data.NutritionDTO
import com.tasty.recipesapp.data.RecipeDTO
import com.tasty.recipesapp.data.MeasurementUnitDTO

// Extension function to map from RecipeDTO to Recipe model
fun RecipeDTO.toModel(): Recipe {
    return Recipe(
        id = this.recipeID,
        name = this.name ?: "",
        description = this.description ?: "",
        thumbnailUrl = this.thumbnailUrl ?: "",
        keywords = this.keywords ?: "",
        isPublic = this.isPublic ?: false,
        userEmail = this.userEmail ?: "",
        originalVideoUrl = this.originalVideoUrl ?: "",
        country = this.country ?: "",
        numServings = this.numServings ?: 0,
        components = this.components?.map { it.toModel() } ?: emptyList(),
        instructions = this.instructions?.map { it.toModel() } ?: emptyList(),
        nutrition = this.nutrition?.toModel() ?: Nutrition()
    )
}

// Mapping from ComponentDTO to Component model
fun ComponentDTO.toModel(): Component {
    return Component(
        rawText = this.rawText,
        extraComment = this.extraComment,
        ingredient = this.ingredient,  // Ensure 'ingredient' is not null
        measurement = this.measurement,
        position = this.position
    )
}

// Mapping from InstructionDTO to Instruction model
fun InstructionDTO.toModel(): Instruction {
    return Instruction(
        id = this.id,
        displayText = this.displayText,
        position = this.position
    )
}

// Mapping from NutritionDTO to Nutrition model
fun NutritionDTO.toModel(): Nutrition {
    return Nutrition(
        calories = this.calories,
        protein = this.protein,
        fat = this.fat,
        carbohydrates = this.carbohydrates,
        sugar = this.sugar,
        fiber = this.fiber
    )
}

fun MeasurementUnitDTO.toModel(): MeasurementUnit {
    return MeasurementUnit(
        name = this.name,
        displaySingular = this.displaySingular,
        displayPlural = this.displayPlural,
        abbreviation = this.abbreviation
    )
}