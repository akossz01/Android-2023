package com.tasty.recipesapp.data.dao

import androidx.room.*
import com.tasty.recipesapp.data.entity.RecipeEntity

@Dao
interface RecipeDao {
    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes WHERE internalId = :id")
    suspend fun getRecipeById(id: Int): RecipeEntity?

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE userEmail = :email")
    fun getRecipesByUserEmail(email: String): List<RecipeEntity>

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)
}
