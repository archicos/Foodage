package com.archico.core.data

import com.archico.core.data.source.remote.retrofit.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkResourceHandler<ResultType, RequestType> {

    private var resourceFlow: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = fetchFromDB().first()
        if (shouldFetchFromNetwork(dbSource)) {
            emit(Resource.Loading())
            when (val apiResponse = initiateNetworkCall().first()) {
                is ApiResponse.Success -> {
                    saveNetworkResult(apiResponse.data)
                    emitAll(fetchFromDB().map {
                        Resource.Success(it)
                    })
                }

                is ApiResponse.Empty -> {
                    emitAll(fetchFromDB().map {
                        Resource.Success(it)
                    })
                }

                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.Error<ResultType>(apiResponse.errorMessage))
                }
            }
        } else {
            emitAll(fetchFromDB().map {
                Resource.Success(it)
            })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun fetchFromDB(): Flow<ResultType>

    protected abstract fun shouldFetchFromNetwork(data: ResultType?): Boolean

    protected abstract suspend fun initiateNetworkCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveNetworkResult(data: RequestType)

    fun getResourceFlow(): Flow<Resource<ResultType>> = resourceFlow
}