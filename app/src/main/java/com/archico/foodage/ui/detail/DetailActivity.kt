package com.archico.foodage.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.archico.core.data.Resource
import com.archico.core.domain.model.Recipe
import com.archico.core.utils.htmlParser
import com.archico.core.utils.setImageViewTint
import com.archico.core.utils.setTextViewColor
import com.archico.foodage.R
import com.archico.foodage.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val args: DetailActivityArgs by navArgs()
    private val detailViewModel: DetailViewModel by viewModels()
    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()
        supportActionBar?.setTitle("Recipes")

        val recipeId: Int = args.id

        detailViewModel.getDetailRecipe(recipeId).observe(this) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbDetail.visibility = View.VISIBLE
                    binding.layFrame.visibility = View.GONE
                    binding.clBadge.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.msgError.root.visibility = View.GONE
                    binding.pbDetail.visibility = View.GONE
                    binding.layFrame.visibility = View.VISIBLE
                    binding.clBadge.visibility = View.VISIBLE
                    setData(it.data)
                }
                is Resource.Error -> {
                    binding.msgError.root.visibility = View.VISIBLE
                    binding.msgError.tvErrorText.text =
                        it.message ?: getString(R.string.something_wrong)
                    binding.pbDetail.visibility = View.GONE
                    binding.layDetail.visibility = View.GONE
                    binding.layFrame.visibility = View.GONE
                }
                else -> Unit
            }
        }

        binding.fabBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setData(data: Recipe?) {
        data?.let {
            Glide.with(this)
                .load(data.image)
                .into(binding.imgDetail)
            binding.apply {
                tvTitleDetail.text = data.title
                tvDesc.text = htmlParser(data.summary)
                setTextViewColor(applicationContext, tvBadgeCheap, data.cheap.toString())
                setImageViewTint(applicationContext, cbBadgeCheap, data.cheap.toString())
                setTextViewColor(applicationContext, tvBadgeVegan, data.vegan.toString())
                setImageViewTint(applicationContext, cbBadgeVegan, data.vegan.toString())
                setTextViewColor(applicationContext, tvBadgeVegetarian, data.vegetarian.toString())
                setImageViewTint(applicationContext, cbBadgeVegetarian, data.vegetarian.toString())
                setTextViewColor(applicationContext, tvBadgeHealthy, data.veryHealthy.toString())
                setImageViewTint(applicationContext, cbBadgeHealthy, data.veryHealthy.toString())
                setTextViewColor(applicationContext, tvBadgeGlutenFree, data.glutenFree.toString())
                setImageViewTint(applicationContext, cbBadgeGlutenFree, data.glutenFree.toString())
                setTextViewColor(applicationContext, tvBadgeDairyFree, data.dairyFree.toString())
                setImageViewTint(applicationContext, cbBadgeDairyFree, data.dairyFree.toString())

                detailViewModel.getRecipeById(data.recipeId).observe(this@DetailActivity) {
                    statusFavorite = it.isFavorite == true
                    setStatusFavorite(statusFavorite)
                }

                fabFavbutton.setOnClickListener {
                    statusFavorite = !statusFavorite
                    detailViewModel.setFavoriteRecipe(data, statusFavorite)
                    setStatusFavorite(statusFavorite)
                }
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {

        val favoriteIcon = if (statusFavorite) {
            R.drawable.ic_bookmark
        } else {
            R.drawable.ic_no_bookmark
        }

        binding.fabFavbutton.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                favoriteIcon
            )
        )
    }
}