package com.pokemon.project.home.data.service.repository

import android.content.Context
import com.pokemon.project.home.data.model.response.AbilitiesResponse
import com.pokemon.project.home.data.model.response.DetailPokemonResponse
import com.pokemon.project.home.data.model.response.PokemonResponse
import com.pokemon.project.home.data.service.network.NetworkDefinition
import com.pokemon.project.home.data.service.network.ServiceCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch

class ServiceRepositoryImpl (
    val context: Context,
    private val coroutineScope: CoroutineScope
    ): ServicesRepository {

    var netApi: NetworkDefinition

    init {
        netApi = NetworkDefinition(context)
    }

    override fun listServicePokemon(
        limit: String?,
        callback: ServiceCallback.ListPokemons
    ) {
        coroutineScope.launch {
            listServicePokemonService(limit, callback)
        }
    }

    override fun detailServicePokemon(
        name: String,
        callback: ServiceCallback.DetailsPokemon
    ) {
        coroutineScope.launch {
            detailServicePokemonervice(name, callback)
        }
    }

    override fun abilitiesServicePokemon(name: String, callback: ServiceCallback.AbilitiesPokemon) {
        coroutineScope.launch {
            abilitiesServicePokemonervice(name, callback)
        }
    }

    suspend fun abilitiesServicePokemonervice(
        name: String,
        callback: ServiceCallback.AbilitiesPokemon
    ) {
        NetworkDefinition.Connect<AbilitiesResponse>(
            context,
            GET_LIST_COMMERCE
        ).startService(
            object : ResponseObject<AbilitiesResponse>(callback){
                override suspend fun call(): Deferred<AbilitiesResponse> {
                    return netApi.service.abilitiesServicePokemon(
                        name
                    )
                }

                override fun onSuccessResponse(
                    serviceId: Int,
                    response: AbilitiesResponse
                ) {
                    callback.onSuccessPokemonabilities(serviceId, response)
                }
            }
        )
    }

    suspend fun detailServicePokemonervice(
        name: String?,
        callback: ServiceCallback.DetailsPokemon
    ) {
        NetworkDefinition.Connect<DetailPokemonResponse >(
            context,
            GET_LIST_COMMERCE
        ).startService(
            object : ResponseObject<DetailPokemonResponse>(callback){
                override suspend fun call(): Deferred<DetailPokemonResponse> {
                    return netApi.service.listServicePokemonDetail(
                        name
                    )
                }

                override fun onSuccessResponse(
                    serviceId: Int,
                    response: DetailPokemonResponse
                ) {
                    callback.onSuccessPokemonDetail(serviceId, response)
                }
            }
        )
    }


    suspend fun listServicePokemonService(
        search: String?,
        callback: ServiceCallback.ListPokemons
    ) {
        NetworkDefinition.Connect<PokemonResponse>(
            context,
            GET_LIST_COMMERCE
        ).startService(
            object : ResponseObject<PokemonResponse>(callback){
                override suspend fun call(): Deferred<PokemonResponse> {
                    return netApi.service.listServicePokemon(
                        search
                    )
                }

                override fun onSuccessResponse(
                    serviceId: Int,
                    response: PokemonResponse
                ) {
                    callback.onSuccessPokemonList(serviceId, response)
                }
            }
        )
    }

    abstract class ResponseObject<T> (val originCallback : ServiceCallback.GenericCallback?) : NetworkDefinition.Connect.CallService<T>{
        override fun onRetry(serviceId: Int, httpCode: Int, message: String) {
            originCallback?.onRetry(serviceId, httpCode, message)
        }
        override fun onServiceError(serviceId: Int, code: Int, message: String?) {
            originCallback?.onServiceError(serviceId, code, message)
        }
    }
}