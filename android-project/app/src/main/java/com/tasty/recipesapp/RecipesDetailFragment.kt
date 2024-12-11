package com.tasty.recipesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.tasty.recipesapp.data.database.RecipeDatabase
import com.tasty.recipesapp.repository.RecipeRepository
import com.tasty.recipesapp.viewmodel.RecipeListViewModel
import com.tasty.recipesapp.viewmodel.RecipeListViewModelFactory

class RecipesDetailFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipes_detail, container, false)

        // Initialize RecipeRepository with RecipeDao
        val recipeDao = RecipeDatabase.getDatabase(requireContext()).recipeDao()
        val repository = RecipeRepository(requireContext(), recipeDao)
        val factory = RecipeListViewModelFactory(repository)
        recipeViewModel = ViewModelProvider(requireActivity(), factory).get(RecipeListViewModel::class.java)

        // Retrieve recipe ID from arguments
        val recipeId = arguments?.getInt("recipeId")
        recipeViewModel.getRecipeById(recipeId)

        // Initialize view elements
        val recipeNameTextView = view.findViewById<TextView>(R.id.recipeName)
        val recipeDescriptionTextView = view.findViewById<TextView>(R.id.recipeDescription)
        val recipeImageView = view.findViewById<ImageView>(R.id.recipeImage)
        val recipeInstructionsTextView = view.findViewById<TextView>(R.id.recipeInstructions)

        // Observe the LiveData for recipe details
        recipeViewModel.selectedRecipe.observe(viewLifecycleOwner) { recipe ->
            recipe?.let {
                recipeNameTextView.text = it.name
                recipeDescriptionTextView.text = it.description
                Picasso.get().load(it.thumbnailUrl).into(recipeImageView)

                val instructionsText =
                    it.instructions.joinToString(separator = "\n") { instruction ->
                        "${it.instructions.indexOf(instruction) + 1}. $instruction"
                    }
                recipeInstructionsTextView.text = instructionsText
            }
        }

        return view
    }
}
