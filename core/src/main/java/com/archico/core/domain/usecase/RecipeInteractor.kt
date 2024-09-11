package com.archico.core.domain.usecase

import com.archico.core.domain.model.Recipe
import com.archico.core.domain.repository.IRecipeRepository
import javax.inject.Inject

class RecipeInteractor @Inject constructor(
    private val recipeRepository: IRecipeRepository
) : RecipeUseCase {
    override fun getRecipes() = recipeRepository.getRecipes()

    override fun getDetailRecipe(id: Int) = recipeRepository.getDetailRecipe(id)

    override fun getRecipeById(id: Int) = recipeRepository.getRecipeById(id)

    override fun getFavoriteRecipe() = recipeRepository.getFavoriteRecipe()

    override fun setFavoriteRecipe(recipe: Recipe, state: Boolean) = recipeRepository.setFavoriteRecipe(recipe, state)
}