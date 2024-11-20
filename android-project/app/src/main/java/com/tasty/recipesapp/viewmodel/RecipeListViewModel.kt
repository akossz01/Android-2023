package com.tasty.recipesapp.viewmodel

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
