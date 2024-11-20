package com.tasty.recipesapp

import RecipeAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasty.recipesapp.R
import com.tasty.recipesapp.data.NutritionDTO
import com.tasty.recipesapp.data.RecipeDTO
import com.tasty.recipesapp.data.database.RecipeDatabase
import com.tasty.recipesapp.data.entity.RecipeEntity
import com.tasty.recipesapp.data.toRecipe
import com.tasty.recipesapp.viewmodel.ProfileViewModel
import com.tasty.recipesapp.model.Recipe
import com.tasty.recipesapp.repository.RecipeRepository
import com.tasty.recipesapp.viewmodel.ProfileViewModelFactory

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipeRepository: RecipeRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        recipesRecyclerView = view.findViewById(R.id.recipesRecyclerView)
        recipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the RecipeAdapter with an empty list
        recipeAdapter = RecipeAdapter(listOf()) { recipe ->
            // Handle item click, for example navigate to detail fragment
            // navigateToRecipeDetail(recipe)
        }
        recipesRecyclerView.adapter = recipeAdapter

        // Initialize ProfileViewModel
        val context = requireContext()
        val recipeDao = RecipeDatabase.getDatabase(context).recipeDao()

        // Pass context and recipeDao to the RecipeRepository constructor
        recipeRepository = RecipeRepository(context, recipeDao)
        val factory = ProfileViewModelFactory(recipeRepository)
        profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        // Observe recipes from ViewModel and update the RecyclerView
        profileViewModel.getAllRecipes { recipeList ->
            val recipeListMapped = recipeList.map { it.toRecipe() }  // Convert RecipeDTO to Recipe
            recipeAdapter.updateData(recipeListMapped)  // Update the adapter with the list of recipes
        }

        val nutrition = NutritionDTO(
            calories = 200,
            protein = 10,
            fat = 5,
            carbohydrates = 20,
            sugar = 10,
            fiber = 5
        )

        // Handle adding a new recipe (for simplicity, use a button here)
        view.findViewById<Button>(R.id.addRecipeButton).setOnClickListener {
            val newRecipe = RecipeEntity(
                name = "New Recipe",
                description = "Recipe description",
                thumbnailUrl = "http://example.com/thumbnail.jpg",
                keywords = listOf("keyword1", "keyword2"),
                isPublic = true,
                userEmail = "user@example.com",
                originalVideoUrl = "http://example.com/video.mp4",
                country = "USA",
                numServings = 4,
                components = listOf(),
                instructions = listOf(),
                nutrition = nutrition
            )

            profileViewModel.insertRecipe(newRecipe)
        }

        // Handle deleting a recipe
        view.findViewById<Button>(R.id.deleteRecipeButton).setOnClickListener {
            val recipeToDelete = RecipeEntity(
                name = "New Recipe",
                description = "Recipe description",
                thumbnailUrl = "http://example.com/thumbnail.jpg",
                keywords = listOf("keyword1", "keyword2"),
                isPublic = true,
                userEmail = "user@example.com",
                originalVideoUrl = "http://example.com/video.mp4",
                country = "USA",
                numServings = 4,
                components = listOf(),
                instructions = listOf(),
                nutrition = nutrition
            )
            profileViewModel.deleteRecipe(recipeToDelete)
        }
    }
}
