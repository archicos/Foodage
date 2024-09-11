package com.archico.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val title: String,
    val image: String,
    val recipeId: Int,
    val summary: String,
    val aggregateLikes: Int,
    val isFavorite: Boolean,
    val readyInMinutes: Int,
    val healthScore: Int,
    val veryHealthy: Boolean,
    val vegetarian: Boolean,
    val vegan: Boolean,
    val cheap: Boolean,
    val dairyFree: Boolean,
    val glutenFree: Boolean
) : Parcelable
