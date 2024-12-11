package com.tasty.recipesapp.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.tasty.recipesapp.api.RecipeApiClient
import com.tasty.recipesapp.data.NutritionDTO
import com.tasty.recipesapp.data.RecipeDTO
import com.tasty.recipesapp.data.entity.RecipeEntity
import com.tasty.recipesapp.data.dao.RecipeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository(
    private val context: Context,
    private val recipeDao: RecipeDao
) {
    private val gson = Gson()
    private val recipeService = RecipeApiClient().recipeService

    // Fetch all recipes: user-created (from Room) + default (from assets)
    suspend fun getAllRecipes(): List<RecipeDTO> {
        val userRecipes = getUserRecipes()
        // val defaultRecipes = getDefaultRecipes()
        val apiRecipes = getApiRecipes()
        return apiRecipes + userRecipes
    }

    // Fetch recipes from the API
    private suspend fun getApiRecipes(): List<RecipeDTO> {
        return withContext(Dispatchers.IO) {
            try {
                val recipes = recipeService.getRecipes()
                recipes
            } catch (e: Exception) {
                Log.e("RecipeRepository", "Error fetching recipes from API: ${e.message}", e)
                listOf()
            }
        }
    }

    suspend fun getRecipeDetails(id: String): RecipeDTO {
        try {
            return recipeService.getRecipeDetails(id)
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error fetching recipe details: ${e.message}", e)
            throw e
        }
    }

    suspend fun getRecipesByUserEmail(): List<RecipeDTO> {
        return getUserRecipes()
    }

    // Insert a new recipe into the Room database
    suspend fun insertRecipe(recipe: RecipeEntity) {
        recipeDao.insertRecipe(recipe)
    }

//    fun getRecipesByUserEmail(email: String): List<RecipeDTO> {
//        return recipeDao.getRecipesByUserEmail(email).map { it.toRecipeDTO() }
//    }

    // Fetch all user-created recipes from Room
    private suspend fun getUserRecipes(): List<RecipeDTO> {
        return withContext(Dispatchers.IO) {
            recipeDao.getAllRecipes().map { recipeEntity ->
                val recipeDTO = RecipeDTO(
                    recipeID = recipeEntity.internalId,
                    name = recipeEntity.name ?: "",
                    description = recipeEntity.description ?: "",
                    thumbnailUrl = recipeEntity.thumbnailUrl ?: "",
                    keywords = recipeEntity.keywords ?: "",
                    isPublic = recipeEntity.isPublic,
                    userEmail = recipeEntity.userEmail ?: "",
                    originalVideoUrl = recipeEntity.originalVideoUrl ?: "",
                    country = recipeEntity.country ?: "",
                    numServings = recipeEntity.numServings ?: 0,
                    components = recipeEntity.components?.map { it } ?: listOf(),
                    instructions = recipeEntity.instructions?.map { it } ?: listOf(),
                    nutrition = recipeEntity.nutrition ?: NutritionDTO(0, 0, 0, 0, 0, 0)
                )
                recipeDTO
            }
        }
    }


    // Read default recipes from the assets folder
    private fun getDefaultRecipes(): List<RecipeDTO> {
        val recipeList = mutableListOf<RecipeDTO>()
        try {
            val inputStream = context.assets.open("recipes.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            // Convert byte buffer to a string
            val jsonString = String(buffer, Charsets.UTF_8)

            // Deserialize the JSON string into a list of RecipeDTO objects
            recipeList.addAll(gson.fromJson(jsonString, Array<RecipeDTO>::class.java).toList())
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error reading recipes: ${e.message}", e)
        }
        return recipeList
    }

    suspend fun deleteRecipe(recipe: RecipeEntity) {
        recipeDao.deleteRecipe(recipe)
    }
}
