package com.tasty.recipesapp

import RecipeAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasty.recipesapp.data.database.RecipeDatabase
import com.tasty.recipesapp.model.Recipe
import com.tasty.recipesapp.repository.RecipeRepository
import com.tasty.recipesapp.viewmodel.RecipeListViewModel
import com.tasty.recipesapp.viewmodel.RecipeListViewModelFactory

class RecipesFragment : Fragment() {

    private lateinit var viewModel: RecipeListViewModel
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var recipesSearchView: SearchView
    private var originalRecipesList = listOf<Recipe>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        recipeRecyclerView = view.findViewById(R.id.recipesRecyclerView)
        recipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the RecipeAdapter with an empty list and click handling
        recipeAdapter = RecipeAdapter(listOf(),
            onItemClick = { navigateToRecipeDetail(it) },
            onDeleteClick = {}
        )
        recipeRecyclerView.adapter = recipeAdapter

        // Set up the RecipeRepository with RecipeDao
        val recipeDao = RecipeDatabase.getDatabase(requireContext()).recipeDao()
        val repository = RecipeRepository(requireContext(), recipeDao)
        viewModel = ViewModelProvider(this, RecipeListViewModelFactory(repository)).get(RecipeListViewModel::class.java)

        // Observe the recipes LiveData from the ViewModel and update the RecyclerView
        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            recipeAdapter.updateData(recipes)
        }

        // Set up the SearchView
        val searchView = view.findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterRecipes(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterRecipes(newText)
                return true
            }
        })
    }


    // Function to navigate to the RecipeDetailFragment
    private fun navigateToRecipeDetail(recipe: Recipe) {
        findNavController().navigate(
            R.id.action_recipesFragment_to_recipesDetailFragment,
            bundleOf("recipeId" to recipe.id)
        )
    }

    private fun filterRecipes(query: String?) {
        // If the query is empty, show all recipes
        if (query.isNullOrEmpty()) {
            viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
                recipeAdapter.updateData(recipes)  // Update the RecyclerView with all recipes
            }
        } else {
            // Filter the recipes based on the query entered
            viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
                val filteredRecipes = recipes.filter {
                    it.name.contains(query, ignoreCase = true)  // Case-insensitive search for recipe names
                }
                recipeAdapter.updateData(filteredRecipes)  // Update RecyclerView with filtered recipes
            }
        }
    }

}
