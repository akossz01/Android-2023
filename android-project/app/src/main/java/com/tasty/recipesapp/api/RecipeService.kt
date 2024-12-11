package com.tasty.recipesapp.api
import com.tasty.recipesapp.data.RecipeDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {
    @GET("api/recipes")
    suspend fun getRecipes(): List<RecipeDTO>

    @GET("api/recipes/{id}")
    suspend fun getRecipeDetails(@Path("id") id: String): RecipeDTO
}