package com.tasty.recipesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tasty.recipesapp.data.toRecipe
import com.tasty.recipesapp.model.Recipe
import com.tasty.recipesapp.repository.RecipeRepository

class RecipeListViewModel(private val repository: RecipeRepository) : ViewModel() {

    // Backing property for LiveData to hold the list of recipes
    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>>
        get() = _recipes

    // Initialize loading of recipes
    init {
        loadRecipes()
    }

    // Function to load recipes from the repository
    private fun loadRecipes() {
        // Fetch data from the repository (this is returning List<RecipeDTO>)
        val recipeDTOs = repository.getAllRecipes()

        // Convert List<RecipeDTO> to List<Recipe>
        val recipes = recipeDTOs.map { it.toRecipe() }

        // Post the converted recipes to LiveData
        _recipes.value = recipes
    }

    fun getRecipeById(id: Int?): Recipe? {
        return recipes.value?.find { it.id == id }
    }
}

class RecipeListViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeListViewModel::class.java)) {
            return RecipeListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}