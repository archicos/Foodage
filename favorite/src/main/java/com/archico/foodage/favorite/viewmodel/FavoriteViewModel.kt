package com.archico.foodage.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.archico.core.domain.usecase.RecipeUseCase

class FavoriteViewModel(private val recipeUseCase: RecipeUseCase) : ViewModel() {

    fun getFavoriteRecipe() = recipeUseCase.getFavoriteRecipe().asLiveData()
}