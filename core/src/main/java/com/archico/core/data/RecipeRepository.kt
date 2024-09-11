package com.archico.core.data

import com.archico.core.data.source.local.LocalDataSource
import com.archico.core.data.source.remote.RemoteDataSource
import com.archico.core.data.source.remote.retrofit.ApiResponse
import com.archico.core.data.source.remote.response.RecipeResponse
import com.archico.core.domain.model.Recipe
import com.archico.core.domain.repository.IRecipeRepository
import com.archico.core.utils.AppExecutors
import com.archico.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IRecipeRepository {
    override fun getRecipes(): Flow<Resource<List<Recipe>>> =
        object : NetworkResourceHandler<List<Recipe>, List<RecipeResponse>>() {
            override fun fetchFromDB(): Flow<List<Recipe>> {
                return localDataSource.getRecipes().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun initiateNetworkCall(): Flow<ApiResponse<List<RecipeResponse>>> =
                remoteDataSource.getRecipes()

            override suspend fun saveNetworkResult(data: List<RecipeResponse>) {
                val recipeList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertRecipe(recipeList)
            }

            override fun shouldFetchFromNetwork(data: List<Recipe>?): Boolean =
                data == null || data.isEmpty()

        }.getResourceFlow()

    override fun getDetailRecipe(id: Int): Flow<Resource<Recipe>> {
        return object : NetworkResource<Recipe, RecipeResponse>() {
            override fun loadNetworkResponse(data: RecipeResponse): Flow<Recipe> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun initiateCall(): Flow<ApiResponse<RecipeResponse>> {
                return remoteDataSource.getDetailRecipe(id)
            }

        }.getResourceFlow()
    }

    override fun getRecipeById(id: Int): Flow<Recipe> {
        return localDataSource.getRecipeById(id).map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun getFavoriteRecipe(): Flow<List<Recipe>> {
        return localDataSource.getFavoriteRecipe().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteRecipe(recipe: Recipe, state: Boolean) {
        val recipeEntity = DataMapper.mapDomainToEntity(recipe)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteRecipe(recipeEntity, state)
        }
    }
}