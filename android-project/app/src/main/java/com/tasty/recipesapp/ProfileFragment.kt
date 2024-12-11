package com.tasty.recipesapp

import RecipeAdapter
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasty.recipesapp.R
import com.tasty.recipesapp.data.InstructionDTO
import com.tasty.recipesapp.data.NutritionDTO
import com.tasty.recipesapp.data.entity.RecipeEntity
import com.tasty.recipesapp.data.database.RecipeDatabase
import com.tasty.recipesapp.data.toRecipe
import com.tasty.recipesapp.model.Recipe
import com.tasty.recipesapp.model.toRecipeEntity
import com.tasty.recipesapp.repository.RecipeRepository
import com.tasty.recipesapp.viewmodel.ProfileViewModel
import com.tasty.recipesapp.viewmodel.ProfileViewModelFactory
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipeRepository: RecipeRepository

    private var selectedImageUri: Uri? = null

    companion object {
        const val IMAGE_PICK_REQUEST_CODE = 1001
        const val USER_EMAIL = "akossz12@gmail.com"
    }

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
        recipeAdapter = RecipeAdapter(
            recipes = listOf(), // Initially empty
            onItemClick = { recipe ->
                navigateToRecipeDetail(recipe)
            },
            onDeleteClick = { recipe ->
                val recipeEntity = recipe.toRecipeEntity()
                showDeleteConfirmationDialog(recipeEntity)
            }
        )
        recipesRecyclerView.adapter = recipeAdapter

        // Initialize ViewModel
        val context = requireContext()
        val recipeDao = RecipeDatabase.getDatabase(context).recipeDao()
        recipeRepository = RecipeRepository(context, recipeDao)
        val factory = ProfileViewModelFactory(recipeRepository)
        profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        // Observe recipes
        profileViewModel.getUserRecipes { recipeList ->
            val recipeListMapped = recipeList.map { it.toRecipe() }
            recipeAdapter.updateData(recipeListMapped)
        }

        // Add recipe button
        view.findViewById<Button>(R.id.addRecipeButton).setOnClickListener {
            showAddRecipeDialog()
        }

        /* // Delete recipe button logic (Optional)
        view.findViewById<Button>(R.id.deleteRecipeButton).setOnClickListener {
            // Implement deletion logic as needed
        }*/
    }

    private fun showAddRecipeDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_recipe, null)
        val dialogBuilder = AlertDialog.Builder(requireContext()).setView(dialogView)
        val dialog = dialogBuilder.create()

        val titleEditText = dialogView.findViewById<EditText>(R.id.recipeTitleEditText)
        val descriptionEditText = dialogView.findViewById<EditText>(R.id.recipeDescriptionEditText)
        val imagePlaceholder = dialogView.findViewById<ImageView>(R.id.recipeImagePlaceholder)
        val instructionContainer = dialogView.findViewById<LinearLayout>(R.id.instructionContainer)
        val addInstructionButton = dialogView.findViewById<Button>(R.id.addInstructionButton)
        val submitButton = dialogView.findViewById<Button>(R.id.submitRecipeButton)

        // Add initial instruction field
        addInstructionField(instructionContainer)

        // Image selection logic
        imagePlaceholder.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }
            startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE)
        }

        // Add more instruction fields
        addInstructionButton.setOnClickListener {
            addInstructionField(instructionContainer)
        }

        // Handle submit
        submitButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val instructions = mutableListOf<InstructionDTO>()
            for (i in 0 until instructionContainer.childCount) {
                val instructionField =
                    instructionContainer.getChildAt(i).findViewById<EditText>(R.id.instructionEditText)
                instructions.add(
                    InstructionDTO(
                        id = i, // You can use 'i' as a simple unique id for each instruction
                        displayText = instructionField.text.toString(),
                        position = i
                    )
                )
            }

            // Create and save recipe
            val newRecipe = RecipeEntity(
                name = title,
                description = description,
                thumbnailUrl = selectedImageUri?.toString() ?: "",
                keywords = "",
                isPublic = true,
                userEmail = "akossz12@gmail.com",
                originalVideoUrl = "",
                country = "USA",
                numServings = 1,
                components = listOf(),
                instructions = instructions,
                nutrition = NutritionDTO(0, 0, 0, 0, 0, 0)
            )
            profileViewModel.insertRecipe(newRecipe)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun addInstructionField(container: LinearLayout) {
        val instructionField = LayoutInflater.from(requireContext())
            .inflate(R.layout.item_instruction_field, container, false)
        container.addView(instructionField)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            view?.findViewById<ImageView>(R.id.recipeImagePlaceholder)?.setImageURI(selectedImageUri)
        }
    }

    private fun showDeleteConfirmationDialog(recipe: RecipeEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Deletion")
            .setMessage("Are you sure you want to delete this recipe?")
            .setPositiveButton("Yes") { _, _ ->
                profileViewModel.deleteRecipe(recipe)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun navigateToRecipeDetail(recipe: Recipe) {
        findNavController().navigate(
            R.id.action_profileFragment_to_recipesDetailFragment,
            bundleOf("recipeId" to recipe.id)
        )
    }
}
