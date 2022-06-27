package com.pokemon.project.home.data.service.network

import com.pokemon.project.home.data.model.response.AbilitiesResponse
import com.pokemon.project.home.data.model.response.DetailPokemonResponse
import com.pokemon.project.home.data.model.response.PokemonResponse


interface ServiceCallback {

    interface GenericCallback {
        /**
         * Method attended when receive http exception
         * @param serviceId identifier of service called
         * @param httpCode standard http response code
         * @param message error message
         */
        fun onRetry(serviceId: Int, httpCode: Int, message: String)

        /**
         * Method attended when any of the service input parameters does not comply
         * with the required format.
         * @param serviceId identifier of service called
         * @param message error message
         */
        fun onFormatError(serviceId: Int, message: String)

        /**
         * Method attended when service response is not valid or unexpected error occurs.
         * @param serviceId identifier of service called
         * @param code error code defined in [ServicesConstants]
         * @param message error message
         */
        fun onServiceError(serviceId: Int, code: Int, message: String?)

    }

    interface ListPokemons: GenericCallback{
        fun onSuccessPokemonList(serviceId: Int, pokemonListResponse: PokemonResponse)
    }

    interface DetailsPokemon: GenericCallback{
        fun onSuccessPokemonDetail(serviceId: Int, detailPokemonResponse: DetailPokemonResponse)
    }

    interface AbilitiesPokemon: GenericCallback{
        fun onSuccessPokemonabilities(serviceId: Int, abilitiesPokemonResponse: AbilitiesResponse)
    }

}