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
    private val onItemClick: (Recipe) -> Unit,
    private val onDeleteClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    companion object {
        const val TYPE_REGULAR = 0
        const val TYPE_PROFILE = 1
    }

//    override fun getItemViewType(position: Int): Int {
//        return if (recipes[position].isProfile) TYPE_PROFILE else TYPE_REGULAR
//    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeName: TextView = itemView.findViewById(R.id.recipeName)
        private val recipeThumbnail: ImageView = itemView.findViewById(R.id.recipeThumbnail)
        private val recipeDescription: TextView = itemView.findViewById(R.id.recipeDescription)
        private val viewRecipeButton: Button = itemView.findViewById(R.id.viewRecipeButton)
        private val addToWishlistButton: Button = itemView.findViewById(R.id.addToWishlistButton)
        // private val deleteRecipeButton: Button? = itemView.findViewById(R.id.deleteRecipeButton)

        fun bind(recipe: Recipe, onClick: (Recipe) -> Unit, onDelete: (Recipe) -> Unit) {
            recipeName.text = recipe.name
            recipeDescription.text = recipe.description ?: "No description available"

            // Check if the thumbnail URL is empty or null and use a valid fallback URL or empty placeholder
            val imageUrl = recipe.thumbnailUrl.takeIf { !it.isNullOrEmpty() }
                ?: R.drawable.placeholder_image.toString()

            // Picasso will load either a valid URL or use the placeholder image
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(recipeThumbnail)

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

            itemView.setOnLongClickListener {
                if (recipe.userEmail == "akossz12@gmail.com") {  // Assuming the user email is hardcoded for now
                    onDelete(recipe)
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        // val layout = if (viewType == TYPE_PROFILE) R.layout.item_recipe_profile else R.layout.item_recipe
        val layout = R.layout.item_recipe
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        // Bind the recipe data to the view holder
        holder.bind(recipes[position], onItemClick, onDeleteClick)
    }

    override fun getItemCount(): Int = recipes.size

    fun updateData(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
}