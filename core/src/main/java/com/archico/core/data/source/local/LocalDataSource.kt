package com.archico.core.data.source.local

import com.archico.core.data.source.local.entity.RecipeEntity
import com.archico.core.data.source.local.room.RecipeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao
) {
    fun getRecipes(): Flow<List<RecipeEntity>> = recipeDao.getRecipes()

    fun getRecipeById(id: Int): Flow<RecipeEntity> = recipeDao.getRecipeById(id)

    suspend fun insertRecipe(recipeList: List<RecipeEntity>) = recipeDao.insertRecipe(recipeList)

    fun getFavoriteRecipe(): Flow<List<RecipeEntity>> = recipeDao.getFavoriteRecipe()

    fun setFavoriteRecipe(recipe: RecipeEntity, newState: Boolean) {
        recipe.isFavorite = newState
        recipeDao.updateFavoriteRecipe(recipe)
    }
}