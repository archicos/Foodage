package com.archico.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.archico.core.R
import com.archico.core.databinding.ItemListRecipeBinding
import com.archico.core.domain.model.Recipe
import com.archico.core.utils.htmlParser
import com.bumptech.glide.Glide

class RecipeAdapter(
    private var listRecipe: List<Recipe>
) : RecyclerView.Adapter<RecipeAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemListRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listRecipe.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val recipe = listRecipe[position]
        Glide.with(holder.itemView.context)
            .load(recipe.image)
            .into(holder.binding.imgRecipe)
        holder.apply {
            binding.txtTitle.text = recipe.title
            binding.txtDescription.text = htmlParser(recipe.summary)
            binding.icHealth.setImageResource(
                when (recipe.veryHealthy.toString()) {
                    "true" -> R.drawable.ic_health
                    else -> R.drawable.ic_no_health
                }
            )
            binding.icVegetarian.setImageResource(
                when (recipe.vegetarian.toString()) {
                    "true" -> R.drawable.ic_vegetarian
                    else -> R.drawable.ic_non_vegetarian
                }
            )
            binding.icCheap.setImageResource(
                when (recipe.cheap.toString()) {
                    "true" -> R.drawable.ic_cheap
                    else -> R.drawable.ic_expensive
                }
            )
            binding.txtTimeMinute.text = recipe.readyInMinutes.toString()
            binding.cardView.setOnClickListener {
                onItemClickCallback.onItemClicked(listRecipe[holder.adapterPosition])
            }
        }
    }

    class ListViewHolder(val binding: ItemListRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: Recipe)
    }
}