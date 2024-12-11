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
        ingredient = this.ingredient,
        measurement = this.measurement,
        position = this.position
    )
}