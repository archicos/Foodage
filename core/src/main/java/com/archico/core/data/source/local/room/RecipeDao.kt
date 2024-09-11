package com.archico.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.archico.core.data.source.local.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipe WHERE recipeId =:id")
    fun getRecipeById(id: Int): Flow<RecipeEntity>

    @Query("SELECT * FROM recipe WHERE isFavorite=1")
    fun getFavoriteRecipe(): Flow<List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: List<RecipeEntity>)

    @Update
    fun updateFavoriteRecipe(recipe: RecipeEntity)

}