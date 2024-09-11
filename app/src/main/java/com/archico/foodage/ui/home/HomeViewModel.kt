package com.archico.foodage.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.archico.core.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(recipeUseCase: RecipeUseCase): ViewModel() {
    val recipe = recipeUseCase.getRecipes().asLiveData()
}