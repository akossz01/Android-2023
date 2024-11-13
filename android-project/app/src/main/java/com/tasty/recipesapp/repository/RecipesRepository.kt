package com.tasty.recipesapp.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.tasty.recipesapp.data.RecipeDTO
import com.tasty.recipesapp.model.Recipe
import com.tasty.recipesapp.model.toModel

class RecipeRepository(private val context: Context) {

    // Fetch and map Recipe data
    fun getAllRecipes(): List<RecipeDTO> {
        return readAllRecipesFromAssets(context)
    }

    // Read Recipe data from assets (simulating loading a JSON file)
    private fun readAllRecipesFromAssets(context: Context): List<RecipeDTO> {
        val gson = Gson()
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
}