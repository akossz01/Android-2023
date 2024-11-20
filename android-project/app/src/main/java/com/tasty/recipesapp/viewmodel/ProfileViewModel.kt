package com.tasty.recipesapp.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasty.recipesapp.data.RecipeDTO
import com.tasty.recipesapp.data.entity.RecipeEntity
import com.tasty.recipesapp.repository.RecipeRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: RecipeRepository) : ViewModel() {

    fun insertRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.insertRecipe(recipe)
        }
    }

    fun getAllRecipes(onResult: (List<RecipeDTO>) -> Unit) {
        viewModelScope.launch {
            onResult(repository.getAllRecipes())
        }
    }

    fun deleteRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.deleteRecipe(recipe)
        }
    }
}
