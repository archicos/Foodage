package com.archico.core.utils

import com.archico.core.data.source.local.entity.RecipeEntity
import com.archico.core.data.source.remote.response.RecipeResponse
import com.archico.core.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapResponsesToEntities(input: List<RecipeResponse>): List<RecipeEntity> {
        val recipeList = ArrayList<RecipeEntity>()
        input.map {
            val recipe = RecipeEntity(
                title = it.title,
                image = it.image,
                recipeId = it.id,
                summary = it.summary,
                aggregateLikes = it.aggregateLikes,
                isFavorite = false,
                readyInMinutes = it.readyInMinutes,
                healthScore = it.healthScore,
                veryHealthy = it.veryHealthy,
                vegetarian = it.vegetarian,
                vegan = it.vegan,
                cheap = it.cheap,
                dairyFree = it.dairyFree,
                glutenFree = it.glutenFree
            )
            recipeList.add(recipe)
        }
        return recipeList
    }

    fun mapResponseToDomain(input: RecipeResponse): Flow<Recipe> {
        return flowOf(
            Recipe(
                input.title,
                input.image,
                input.id,
                input.summary,
                input.aggregateLikes,
                false,
                input.readyInMinutes,
                input.healthScore,
                input.veryHealthy,
                input.vegetarian,
                input.vegan,
                input.cheap,
                input.dairyFree,
                input.glutenFree
            )
        )
    }

    fun mapEntitiesToDomain(input: List<RecipeEntity>): List<Recipe> =
        input.map {
            Recipe(
                title = it.title,
                image = it.image,
                recipeId = it.recipeId,
                summary = it.summary,
                aggregateLikes = it.aggregateLikes,
                isFavorite = it.isFavorite,
                readyInMinutes = it.readyInMinutes,
                healthScore = it.healthScore,
                veryHealthy = it.veryHealthy,
                vegetarian = it.vegetarian,
                vegan = it.vegan,
                cheap = it.cheap,
                dairyFree = it.dairyFree,
                glutenFree = it.glutenFree
            )
        }

    fun mapEntityToDomain(input: RecipeEntity): Recipe =
        Recipe(
            title = input.title,
            image = input.image,
            recipeId = input.recipeId,
            summary = input.summary,
            aggregateLikes = input.aggregateLikes,
            isFavorite = input.isFavorite,
            readyInMinutes = input.readyInMinutes,
            healthScore = input.healthScore,
            veryHealthy = input.veryHealthy,
            vegetarian = input.vegetarian,
            vegan = input.vegan,
            cheap = input.cheap,
            dairyFree = input.dairyFree,
            glutenFree = input.glutenFree
        )

    fun mapDomainToEntity(input: Recipe) = RecipeEntity(
        title = input.title,
        image = input.image,
        recipeId = input.recipeId,
        summary = input.summary,
        aggregateLikes = input.aggregateLikes,
        isFavorite = input.isFavorite,
        readyInMinutes = input.readyInMinutes,
        healthScore = input.healthScore,
        veryHealthy = input.veryHealthy,
        vegetarian = input.vegetarian,
        vegan = input.vegan,
        cheap = input.cheap,
        dairyFree = input.dairyFree,
        glutenFree = input.glutenFree
    )
}