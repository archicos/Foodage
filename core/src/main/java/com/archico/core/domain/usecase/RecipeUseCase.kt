package com.archico.core.domain.usecase

import com.archico.core.data.Resource
import com.archico.core.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeUseCase {

    fun getRecipes(): Flow<Resource<List<Recipe>>>
    fun getDetailRecipe(id: Int): Flow<Resource<Recipe>>
    fun getRecipeById(id: Int): Flow<Recipe>
    fun getFavoriteRecipe(): Flow<List<Recipe>>
    fun setFavoriteRecipe(recipe: Recipe, state: Boolean)
}