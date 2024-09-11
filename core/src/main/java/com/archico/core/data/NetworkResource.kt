package com.archico.core.data

import com.archico.core.data.source.remote.retrofit.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkResource<ResultType, RequestType>{
    private val resourceFlow: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = initiateCall().first()) {
            is ApiResponse.Success -> {
                emitAll(loadNetworkResponse(apiResponse.data).map {
                    Resource.Success(it)
                })
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
            else -> Unit
        }
    }

    protected abstract fun loadNetworkResponse(data: RequestType): Flow<ResultType>

    protected abstract suspend fun initiateCall(): Flow<ApiResponse<RequestType>>

    fun getResourceFlow(): Flow<Resource<ResultType>> = resourceFlow
}