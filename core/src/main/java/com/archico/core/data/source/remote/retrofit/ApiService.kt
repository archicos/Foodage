package com.archico.core.data.source.remote.retrofit

import com.archico.core.BuildConfig
import com.archico.core.data.source.remote.response.ListRecipeResponse
import com.archico.core.data.source.remote.response.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("{id}/information")
    suspend fun getDetailRecipe(
        @Path("id") id: Int,
        @Query("apiKey")apiKey: String = BuildConfig.API_KEY
    ): RecipeResponse

    @GET("complexSearch")
    suspend fun getRecipes(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("addRecipeInformation") addRecipeInformation: String = "true",
        @Query("fillIngredients") fillIngredients: String = "true"
    ): ListRecipeResponse
}