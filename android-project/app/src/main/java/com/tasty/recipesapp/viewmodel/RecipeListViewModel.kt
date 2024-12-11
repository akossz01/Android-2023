package com.tasty.recipesapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tasty.recipesapp.data.toRecipe
import com.tasty.recipesapp.model.Recipe
import com.tasty.recipesapp.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeListViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>()

    private val _selectedRecipe = MutableLiveData<Recipe?>()
    val selectedRecipe: LiveData<Recipe?> get() = _selectedRecipe
    val recipes: LiveData<List<Recipe>>
        get() = _recipes

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            val recipeDTOs = repository.getAllRecipes()
            val recipes = recipeDTOs.map { it.toRecipe() }
            _recipes.value = recipes
        }
    }

    fun getRecipeById(id: Int?) {
        if (id == null) return

        viewModelScope.launch {
            try {
                val recipeDTO = repository.getRecipeDetails(id.toString())
                val recipe = recipeDTO.toRecipe()
                _selectedRecipe.value = recipe
            } catch (e: Exception) {
                _selectedRecipe.value = null
                Log.e("RecipeListViewModel", "Error fetching recipe by ID: ${e.message}", e)
            }
        }
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
