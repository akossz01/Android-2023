package com.tasty.recipesapp.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasty.recipesapp.data.RecipeDTO
import com.tasty.recipesapp.data.entity.RecipeEntity
import com.tasty.recipesapp.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class ProfileViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val userEmail = "akossz12@gmail.com"

    // This function will fetch only the recipes belonging to the logged-in user
    fun getUserRecipes(onResult: (List<RecipeDTO>) -> Unit) {
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                repository.getRecipesByUserEmail()
            }

            onResult(res)
        }
    }

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
