package com.tasty.recipesapp.model

import com.tasty.recipesapp.data.ComponentDTO
import com.tasty.recipesapp.data.InstructionDTO
import com.tasty.recipesapp.data.NutritionDTO
import com.tasty.recipesapp.data.RecipeDTO
import com.tasty.recipesapp.data.MeasurementUnitDTO

import kotlin.Unit

// Extension function to map from RecipeDTO to Recipe model
fun RecipeDTO.toModel(): Recipe {
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
        components = this.components.map { it.toModel() },
        nutrition = this.nutrition.toModel()
    )
}

// Mapping from ComponentDTO to Component model
fun ComponentDTO.toModel(): Component {
    return Component(
        rawText = this.rawText,
        extraComment = this.extraComment,
        ingredientName = this.ingredient.name,  // Ensure 'ingredient' is not null
        measurement = "${this.measurement.quantity} ${this.measurement.unit.abbreviation}",
        position = this.position
    )
}

// Mapping from InstructionDTO to Instruction model
fun InstructionDTO.toModel(): Instruction {
    return Instruction(
        id = this.instructionID,
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