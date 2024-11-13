package com.tasty.recipesapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tasty.recipesapp.R
import com.tasty.recipesapp.data.RecipeDTO
import com.tasty.recipesapp.model.Recipe

class RecipeAdapter(private var recipes: List<Recipe>, private val onItemClick: (Recipe) -> Unit) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    // Create ViewHolder class to hold the views for each item in the RecyclerView
    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeName: TextView = itemView.findViewById(R.id.recipeName)
        private val recipeThumbnail: ImageView = itemView.findViewById(R.id.recipeThumbnail)
        private val recipeDescription: TextView = itemView.findViewById(R.id.recipeDescription)

        // Bind the RecipeDTO object to the views
        fun bind(recipe: Recipe) {  // Change the type here
            recipeName.text = recipe.name
            recipeDescription.text = recipe.description ?: "No description available"

            // Use Picasso to load the thumbnail image from the URL
            Picasso.get().load(recipe.thumbnailUrl).into(recipeThumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        // Inflate the item layout for each recipe
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        // Bind the recipe data to the view holder
        val recipe = recipes[position]
        holder.bind(recipe)
        holder.itemView.setOnClickListener { onItemClick(recipe) }
    }

    override fun getItemCount(): Int = recipes.size

    // Method to update the data in the adapter when there are changes in the list
    fun updateData(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }

}