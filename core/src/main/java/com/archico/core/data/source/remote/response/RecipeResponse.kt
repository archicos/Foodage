package com.archico.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("glutenFree")
    val glutenFree: Boolean,

    @field:SerializedName("dairyFree")
    val dairyFree: Boolean,

    @field:SerializedName("vegetarian")
    val vegetarian: Boolean,

    @field:SerializedName("veryHealthy")
    val veryHealthy: Boolean,

    @field:SerializedName("cheap")
    val cheap: Boolean,

    @field:SerializedName("vegan")
    val vegan: Boolean,

    @field:SerializedName("aggregateLikes")
    val aggregateLikes: Int,

    @field:SerializedName("healthScore")
    val healthScore: Int,

    @field:SerializedName("readyInMinutes")
    val readyInMinutes: Int,

    @field:SerializedName("summary")
    val summary: String
)
