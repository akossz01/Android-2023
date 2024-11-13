package com.tasty.recipesapp.data

import com.tasty.recipesapp.model.Component

data class ComponentDTO(
    val rawText: String,
    val extraComment: String,
    val ingredient: IngredientDTO,
    val measurement: MeasurementDTO,
    val position: Int
)

fun ComponentDTO.toComponent(): Component {
    return Component(
        rawText = this.rawText,
        extraComment = this.extraComment,
        ingredientName = this.ingredient.name,  // Assuming ingredient has a name property
        measurement = this.measurement.quantity + " " + this.measurement.unit.displaySingular, // Adjust as needed
        position = this.position
    )
}