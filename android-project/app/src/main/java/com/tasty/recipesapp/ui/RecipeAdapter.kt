import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tasty.recipesapp.R
import com.tasty.recipesapp.model.Recipe

class RecipeAdapter(
    private var recipes: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeName: TextView = itemView.findViewById(R.id.recipeName)
        private val recipeThumbnail: ImageView = itemView.findViewById(R.id.recipeThumbnail)
        private val recipeDescription: TextView = itemView.findViewById(R.id.recipeDescription)
        private val viewRecipeButton: Button = itemView.findViewById(R.id.viewRecipeButton)
        private val addToWishlistButton: Button = itemView.findViewById(R.id.addToWishlistButton)

        fun bind(recipe: Recipe, onClick: (Recipe) -> Unit) {
            recipeName.text = recipe.name
            recipeDescription.text = recipe.description ?: "No description available"

            // Use Picasso to load the thumbnail image from the URL
            Picasso.get().load(recipe.thumbnailUrl).placeholder(R.drawable.placeholder_image).error(R.drawable.placeholder_image).into(recipeThumbnail)

            // Set click listener only on the View Recipe button
            viewRecipeButton.setOnClickListener {
                onClick(recipe)
            }

            addToWishlistButton.setOnClickListener {
                // Toggle heart icon between filled and unfilled
                val isFilled = addToWishlistButton.tag as? Boolean ?: false
                if (isFilled) {
                    addToWishlistButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_scaled_24, 0, 0, 0)
                } else {
                    addToWishlistButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_fill_scaled_24, 0, 0, 0)
                }
                // Toggle the state
                addToWishlistButton.tag = !isFilled
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        // Inflate the item layout for each recipe
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        // Bind the recipe data to the view holder and pass the click listener
        holder.bind(recipes[position], onItemClick)
    }

    override fun getItemCount(): Int = recipes.size

    fun updateData(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
}