package com.pokemon.project.home.data.service.network.api

import com.pokemon.project.home.data.model.response.AbilitiesResponse
import com.pokemon.project.home.data.model.response.DetailPokemonResponse
import com.pokemon.project.home.data.model.response.PokemonResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    fun listServicePokemon(
        @Query("limit") limit: String?
    ): Deferred<PokemonResponse>

    @GET("pokemon-species/{name}")
    fun listServicePokemonDetail(
        @Path("name") name: String?
    ): Deferred<DetailPokemonResponse>

    @GET("pokemon/{name}")
    fun abilitiesServicePokemon(
        @Path("name") name: String?
    ): Deferred<AbilitiesResponse>
}