package com.tasty.recipesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasty.recipesapp.model.Recipe
import com.tasty.recipesapp.repository.RecipeRepository
import com.tasty.recipesapp.ui.RecipeAdapter
import com.tasty.recipesapp.viewmodel.RecipeListViewModel
import com.tasty.recipesapp.viewmodel.RecipeListViewModelFactory

class RecipesFragment : Fragment() {

    private lateinit var viewModel: RecipeListViewModel
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipeRecyclerView: RecyclerView

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
        recipeAdapter = RecipeAdapter(listOf()) { recipe ->
            navigateToRecipeDetail(recipe)
        }
        recipeRecyclerView.adapter = recipeAdapter

        // Set up the ViewModel
        val repository = RecipeRepository(requireContext())
        viewModel = ViewModelProvider(this, RecipeListViewModelFactory(repository)).get(
            RecipeListViewModel::class.java)

        // Observe the recipes LiveData from the ViewModel and update the RecyclerView
        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            recipeAdapter.updateData(recipes)
        }
    }

    // Function to navigate to the RecipeDetailFragment
    private fun navigateToRecipeDetail(recipe: Recipe) {
        findNavController().navigate(R.id.action_recipesFragment_to_recipesDetailFragment,
            bundleOf("recipeId" to recipe.id))
    }

}
