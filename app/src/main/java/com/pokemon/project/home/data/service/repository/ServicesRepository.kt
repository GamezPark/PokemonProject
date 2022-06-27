package com.pokemon.project.home.data.service.repository


import com.pokemon.project.home.data.service.network.ServiceCallback

val GET_LIST_COMMERCE = 1001

interface ServicesRepository {

    fun listServicePokemon(
        limit: String?,
        callback: ServiceCallback.ListPokemons
    )

    fun detailServicePokemon(
        name: String,
        callback: ServiceCallback.DetailsPokemon
    )

    fun abilitiesServicePokemon(
        name: String,
        callback: ServiceCallback.AbilitiesPokemon
    )
}