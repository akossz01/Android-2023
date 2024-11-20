package com.tasty.recipesapp.data

import com.tasty.recipesapp.model.Instruction

data class InstructionDTO(
    val id: Int,
    val displayText: String,
    val position: Int
)

fun InstructionDTO.toInstruction(): Instruction {
    return Instruction(
        id = this.id,
        displayText = this.displayText,
        position = this.position
    )
}