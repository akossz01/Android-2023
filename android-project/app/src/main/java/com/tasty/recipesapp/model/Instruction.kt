package com.tasty.recipesapp.model

import com.tasty.recipesapp.data.InstructionDTO

data class Instruction(
    val id: Int,
    val displayText: String,
    val position: Int
)

fun Instruction.toEntity(): InstructionDTO {
    return InstructionDTO(
        id = this.id,
        displayText = this.displayText,
        position = this.position
    )
}
