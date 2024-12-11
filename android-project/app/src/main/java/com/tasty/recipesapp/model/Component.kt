package com.tasty.recipesapp.model

import com.tasty.recipesapp.data.ComponentDTO
import com.tasty.recipesapp.data.IngredientDTO
import com.tasty.recipesapp.data.MeasurementDTO

data class Component(
    val rawText: String,
    val extraComment: String?,
    val ingredient: IngredientDTO,
    val measurement: MeasurementDTO,
    val position: Int
)

fun Component.toEntity(): ComponentDTO {
    return ComponentDTO(
        rawText = this.rawText,
        extraComment = this.extraComment ?: "",
        ingredient = this.ingredient,
        measurement = this.measurement,
        position = this.position
    )
}
